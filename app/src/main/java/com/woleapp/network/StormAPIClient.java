package com.woleapp.network;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.woleapp.BuildConfig;
import com.woleapp.model.AgentTransactions;
import com.woleapp.model.Inventory;
import com.woleapp.util.Constants;
import com.woleapp.util.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.woleapp.util.Constants.USER_TYPE_AGENT;

/**
 * REST Client that makes API Calls
 *
 * @author Kaushik N Sanji
 */
public class StormAPIClient {

    private static final String LOG_TAG = StormAPIClient.class.getSimpleName();

    //public static final String BASE_URL = "http://storm.test.netpluspay.com/";
    //public static final String BASE_URL = "https://storm.netpluspay.com/";
    public static final String BASE_URL = BuildConfig.BASE_URL_STORM;

    private static StormAPIService apiService;
    private static StormAPIService apiServiceDefault;


    static Context context;

    /**
     * Creates the Retrofit Service for Storm API
     *
     * @return The {@link APIService} instance
     */
    public static synchronized StormAPIService create(Context context) {

        StormAPIClient.context = context;
        if (apiService == null) {
            apiService = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHttpClient(context, true))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(StormAPIService.class);
        }

        return apiService;
    }

    public static synchronized StormAPIService createDefault(Context context) {

        StormAPIClient.context = context;
        if (apiServiceDefault == null) {
            apiServiceDefault = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHttpClient(context, false))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(StormAPIService.class);
        }

        return apiServiceDefault;
    }


    public static OkHttpClient getHttpClient(Context context, boolean addTokenInterceptor) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(interceptor);
        if (addTokenInterceptor)
            builder.addInterceptor(new TokenInterceptor());
        builder.connectTimeout(2, TimeUnit.MINUTES);
        final OkHttpClient okHttpClient = builder.build();

        return okHttpClient;

    }

    /**
     * Method that invokes the {@link APIService#//searchOrListInventories(String, int, int)} API for
     * retrieving the Repositories using the Search query executed.
     *
     * @param //service      The {@link APIService} instance for executing the API
     * @param //query        The Search Query to transferFunds
     * @param //page         The Page index to show
     * @param //itemsPerPage The Number of items to be shown per page
     * @param //apiCallback  The {@link ApiCallback} interface for receiving the events of this
     *                       API Call
     */
    public static void searchOrListInventories(APIService service, String query, int page, int itemsPerPage, ApiCallback apiCallback) {
        Log.e(LOG_TAG, String.format("searchOrListInventories: query: %s, page: %s, itemsPerPage: %s", query, page, itemsPerPage));

        //Framing the query to be searched only in the Name and description fields of the
        //Github repositories
        String apiQuery = query + "in:name,description";

        Long user_id = SharedPrefManager.getUser().getUser_id();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("merchant_id", user_id.toString());
        jsonObject.addProperty("offset", page);
        jsonObject.addProperty("limit", itemsPerPage);

        service.getInventories(jsonObject).enqueue(new Callback<Object>() {
            //Called when the fundsTransferResponse is received
            @Override
            public void onResponse(@Nullable Call<Object> call, @NonNull Response<Object> response) {
                Log.d(LOG_TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    //Retrieving the Word Items when the fundsTransferResponse is successful
                    List<Inventory> items = null;

                    if (response.body() != null) {
                        Gson gson = new Gson();
                        //items = fundsTransferResponse.body().items;
                        try {
                            JSONObject jsonObject1 = new JSONObject(new Gson().toJson(response.body()));
                            //JSONObject jsonObject = new JSONObject(new Gson().toJson(fundsTransferResponse.body()));
                            JSONArray jsonArray = jsonObject1.getJSONArray(Constants.PN_DATA);
                            if (jsonArray != null && jsonArray.length() > 0) {
                                items = gson.fromJson(jsonArray.toString(), new TypeToken<List<Inventory>>() {
                                }.getType());
                            } else {
                                items = new ArrayList<>();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        items = new ArrayList<>();
                    }

                    //Pass the result to the callback
                    apiCallback.onSuccess(items);
                } else {
                    //When the fundsTransferResponse is unsuccessful
                    try {
                        //Pass the error to the callback
                        apiCallback.onError(response.errorBody() != null ? response.errorBody().string() : "Unknown error");
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "onResponse: Failed while reading errorBody: ", e);
                    }
                }
            }

            //Called on Failure of the request
            @Override
            public void onFailure(@Nullable Call<Object> call, @NonNull Throwable t) {
                Log.d(LOG_TAG, "onFailure: Failed to get data");
                //Pass the error to the callback
                apiCallback.onError(t.getMessage() != null ?
                        t.getMessage() : "Unknown error");
            }
        });
    }

    public static void getAppToken(JsonObject jsonObject, final ApiCallback2 apiCallback, Context context) {

        StormAPIClient.createDefault(context).appToken(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(er -> {
                    apiCallback.onFail(er.getMessage());
                })
                .subscribe(new DefaultObserver(apiCallback));

    }

    public static Observable<Response<Object>> getUserTokenObservable(String appToken, JsonObject credentials, Context context) {
        return StormAPIClient.createDefault(context).userToken("Bearer " + appToken, credentials)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> getWalletBalance(String stormId) {
        return apiService.getWalletBalance(stormId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> registerPushTokenObservable(JsonObject payload, Context context) {
        return StormAPIClient.create(context).registerPushToken(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> registerUser(JsonObject payload, Context context) {
        String appToken = SharedPrefManager.getAppToken();
        return StormAPIClient.createDefault(context).registerUser("Bearer " + appToken, payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> verifyUser(JsonObject payload, Context context) {
        String appToken = SharedPrefManager.getAppToken();
        return StormAPIClient.createDefault(context).verifyUser("Bearer " + appToken, payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<AgentTransactions> getTransactions(String stormId, Context context) {
        return StormAPIClient.create(context).getTransactions(stormId, 1, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public static Observable<Response<Object>> getAccountName(String accountNumber, String bankCode, Context context) {
        String appToken = SharedPrefManager.getAppToken();
        return StormAPIClient.createDefault(context).getAccountName("Bearer " + appToken, accountNumber, bankCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void decrementStockLevel(JsonObject jsonObject, final ApiCallback2 apiCallback, Context context) {

    }

    public static void getInventories(JsonObject jsonObject, final ApiCallback2 apiCallback, Context context) {

    }

    public static void getCategories(final ApiCallback2 apiCallback, Context context) {

    }

    //MultipartBody.Part multipartBody
    public static void addInventory(String fileName, MultipartBody.Part multipartBody, Map<String, String> map, ApiCallback apiCallback) {

    }

    public static void addInventoryMultipart(String fileName, MultipartBody.Part multipartBody, Map<String, String> map, ApiCallback apiCallback) {

    }


    public static void addInventory11(APIService service, String fileName, MultipartBody.Part multipartBody, Map<String, RequestBody> map, ApiCallback apiCallback) {

    }

    public static void addInventory1(APIService service, RequestBody requestBody, ApiCallback apiCallback) {

    }

    public static void addInventory1Multipart(RequestBody requestBody, ApiCallback apiCallback) {

    }

    public interface ApiCallback {
        /**
         * Callback invoked when the Search Word API Call
         * completed successfully
         *
         * @param items The List of Repos retrieved for the Search done
         */
        void onSuccess(Object items);// List<Object> items //ProjectDTO

        /**
         * Callback invoked when the Search Word API Call failed
         *
         * @param errorMessage The Error message captured for the API Call failed
         */
        void onError(String errorMessage);
    }

    public static Observable<Response<Object>> passwordReset(JsonObject payload, Context context) {
        String appToken = SharedPrefManager.getAppToken();
        return StormAPIClient.createDefault(context).passwordReset("Bearer " + appToken, payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public static Observable<Response<Object>> getAgentDetails(String stormId, Context context) {
        return StormAPIClient.create(context).getAgentDetails(stormId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> getMerchantDetails(String stormId, Context context) {
        return StormAPIClient.create(context).getMerchantDetails(stormId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> getFee(Context context, String name) {
        String appToken = SharedPrefManager.getAppToken();
        return StormAPIClient.createDefault(context).getFee("Bearer " + appToken, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> transfer(JsonObject payload, Context context) {
        return StormAPIClient.create(context).transfer(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> getProducts(String userToken, Context context) {

        return StormAPIClient.create(context).getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void onRequestFailed(Response<Object> response, final ApiCallback2 callBack, int user_type) {
        //Response<Object> response,final ApiCallback2 callBack
        if (response.errorBody() != null) {
            Gson gson = new Gson();
            try {
                //JSONObject jsonObject1 = new JSONObject(gson.toJson(response.errorBody().string()));

                JSONParser parser_obj = new JSONParser();
                String errorBodyString = response.errorBody().string();
                Log.e("error", errorBodyString);

                org.json.simple.JSONObject jsonObject1 = (org.json.simple.JSONObject) parser_obj.parse(errorBodyString);
                Log.e("error", jsonObject1.toJSONString());
                getOTPIfUnverified(new JSONObject(jsonObject1.toJSONString()), callBack, user_type);

                /*String message = (String) jsonObject1.get("message");
                //String message = jsonObject1.optString("message",response.message());
                callBack.onFail(message);*/
            } catch (ParseException e) {
                e.printStackTrace();
                callBack.onFail("Unknown error occured. Please try again later");
            } catch (JSONException e) {
                e.printStackTrace();
                callBack.onFail("Unknown error occured. Please try again later");
            } catch (IOException e) {
                e.printStackTrace();
                callBack.onFail("Unknown error occured. Please try again later");
            }

        } else {
            String message = response.message();
            callBack.onFail(message);
        }

    }


    public static void getOTPIfUnverified(JSONObject jsonObject1, ApiCallback2 callBack, int user_type) {
        try {
            if (jsonObject1.has("Your OTP")) {
                String otp = jsonObject1.getString("Your OTP");
                String message = jsonObject1.getString("message");
                String user_id = "";
                if (user_type == USER_TYPE_AGENT) {
                    user_id = jsonObject1.getString("agent_id");
                } else {
                    user_id = jsonObject1.getString("Merchant_id");
                }

                HashMap<String, String> map = new HashMap<>();
                map.put("otp", otp);
                map.put("id", user_id);
                map.put("message", message);
                map.put("user_type", user_type + "");
                callBack.onResponse(map);

                                            /*JsonObject jsonObject = new JsonObject();
                                            jsonObject.addProperty("otp",otp);
                                            jsonObject.addProperty("message",message);
                                            jsonObject.addProperty("id",agent_id);
                                            callBack.onResponse(jsonObject);*/
            } else if (jsonObject1.has("message")) {
                String message = jsonObject1.getString("message");
                callBack.onFail(message);
            } else if (jsonObject1.has("error") && jsonObject1.getJSONArray("error") != null && jsonObject1.getJSONArray("error").length() > 0) {
                String message = jsonObject1.getJSONArray("error").getString(0);
                callBack.onFail(message);
            } else {
                callBack.onFail("Unknown error occured. Please try again later");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    static class DefaultObserver implements Observer<Response<Object>> {

        private ApiCallback2 apiCallback;

        public DefaultObserver(ApiCallback2 apiCallback) {
            this.apiCallback = apiCallback;
        }


        @Override
        public void onSubscribe(Disposable d) {
            apiCallback.onStart();
        }

        @Override
        public void onNext(Response<Object> response) {
            if (response.isSuccessful() && response.body() != null) {
                Gson gson = new Gson();
                try {

                    JSONObject object = new JSONObject(gson.toJson(response.body()));
                    apiCallback.onResponse(object);

                } catch (JSONException e) {
                    Log.e(StormAPIClient.class.getName(), "an unexpected error occured", e);
                    apiCallback.onFail(e.getMessage());
                }

            } else {
                apiCallback.onFail("Unable to connect to server, please try again later.");
            }
        }

        @Override
        public void onError(Throwable e) {
            Log.e("sdsdssdssds", "on error called", e);
            apiCallback.onFail(e.getMessage());
        }

        @Override
        public void onComplete() {
            apiCallback.onEnd();
        }

    }

    public interface ApiCallback2 {
        /**
         * Callback invoked when the Search Word API Call
         * completed successfully
         * <p>
         * //@param items The List of Repos retrieved for the Search done
         */

        void onStart();

        void onEnd();

        //void onResponse(ResponseEnvelope response);
        void onResponse(Object response);

        void onFail(String msg);

        //void onSuccess(Object items);// List<Object> items //ProjectDTO

        /**
         * Callback invoked when the Search Word API Call failed
         *
         * @param errorMessage The Error message captured for the API Call failed
         */
        //void onError(String errorMessage);
    }
}
