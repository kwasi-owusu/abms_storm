package com.woleapp.network;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.woleapp.BuildConfig;
import com.woleapp.model.Categories;
import com.woleapp.model.Inventory;
import com.woleapp.model.User;
import com.woleapp.util.ConnectivityReceiver;
import com.woleapp.util.Constants;
import com.woleapp.util.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static com.woleapp.util.Constants.USER_TYPE_AGENT;

/**
 * REST Client that makes API Calls
 *
 * @author Kaushik N Sanji
 */
public class APIServiceClient {

    private static final String LOG_TAG = APIServiceClient.class.getSimpleName();

    //public static final String BASE_URL = "https://storm.netpluspay.com/api/";

    //TODO: Change back to live url
    //public static final String BASE_URL = "https://netpluspay.com/pay/test/";
    //public static final String BASE_URL = "http://storm.test.netpluspay.com/";
    public static final String BASE_URL = BuildConfig.BASE_URL_STORM;
    private static APIService apiService;

    private static APIService multipartAPI;

//    public static final String BASE_URL_NETPLUS_PAY = "https://netpluspay.com/pay/test/";
//    public final static String BASE_URL_ETRANSACT_SANDBOX = "http://www.etranzact.net/";


    static Context context;

    /**
     * Creates the Retrofit Service for Github API
     *
     * @return The {@link APIService} instance
     */
    public static synchronized APIService create(Context context) {

        APIServiceClient.context = context;
        if(apiService == null) {
            apiService = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHttpClient(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(APIService.class);
        }

        return apiService;
    }

    public static synchronized APIService createMultipart(Context context) {

        APIServiceClient.context = context;
        if(apiService == null) {
            apiService = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHttpClient(context, true))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(APIService.class);
        }

        return apiService;
    }

    public static OkHttpClient getHttpClient(Context context) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

//        builder.addInterceptor(new RetryInterceptor());
        builder.addInterceptor(interceptor);
        builder.addInterceptor(new TokenInterceptor());
        builder.connectTimeout(2, TimeUnit.MINUTES);
        builder.authenticator(new Authenticator() {
            @javax.annotation.Nullable
            @Override
            public Request authenticate(@javax.annotation.Nullable Route route, okhttp3.Response response) throws IOException {
                String credential = Credentials.basic("android", "st0rm!");
                return response.request().newBuilder().header("Authorization", credential).build();            }

        });

        final OkHttpClient okHttpClient = builder.build();
        return okHttpClient;
    }

    public static OkHttpClient getHttpClient(Context context, boolean isMultiPart) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(new TokenInterceptor());
        boolean isLogin = SharedPrefManager.isLogin();
        Log.e("isLogin", isLogin + " ##");
        if (isLogin) {
            builder.addInterceptor(new HeaderInterceptor(context, isMultiPart));
        }
        builder.addInterceptor(new RetryInterceptor());
        builder.addInterceptor(interceptor);
        builder.authenticator(new Authenticator() {
            @javax.annotation.Nullable
            @Override
            public Request authenticate(@javax.annotation.Nullable Route route, okhttp3.Response response) throws IOException {
                String credential = Credentials.basic("android", "st0rm!");
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        });

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
        //Executing the API asynchronously
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
                        apiCallback.onError(response.errorBody() != null ?
                                response.errorBody().string() : "Unknown error");
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

    public static void decrementStockLevel(JsonObject jsonObject, final ApiCallback2 apiCallback, Context context) {
        APIServiceClient.create(context).inventoryDecrement(jsonObject).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if(response.isSuccessful() && response.body() != null)  {
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject1 = new JSONObject(new Gson().toJson(response.body()));
                        if(jsonObject1.has("Status") && jsonObject1.optBoolean("Status", false)) {
                            apiCallback.onResponse(jsonObject1);
                        } else {
                            apiCallback.onResponse(jsonObject1.optString("message","unknown error"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        apiCallback.onFail("unknown error");
                    }


                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                apiCallback.onFail("unknown error");
            }
        });
    }

    public static void getInventories(JsonObject jsonObject, final ApiCallback2 apiCallback, Context context) {
        //Framing the query to be searched only in the Name and description fields of the
        //Github repositories
        //Long user_id = SharedPrefManager.getInstance(context).getUser().getUser_id();
        //Executing the API asynchronously
        APIServiceClient.create(context).getInventories(jsonObject).enqueue(new Callback<Object>() {
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

                            JSONArray jsonArray = null;
                            if (jsonObject1.has("data")) {
                                jsonArray = jsonObject1.getJSONArray("data");
                            } else if (jsonObject1.has(Constants.PN_DATA)) {
                                jsonArray = jsonObject1.getJSONArray(Constants.PN_DATA);
                            }
                            if (jsonArray != null) {
                                //JSONArray jsonArray = object.getJSONArray("records");
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
                    apiCallback.onResponse(items);
                } else {
                    //When the fundsTransferResponse is unsuccessful
                    try {
                        //Pass the error to the callback
                        apiCallback.onFail(response.errorBody() != null ?
                                response.errorBody().string() : "Unknown error");
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
                apiCallback.onFail(t.getMessage() != null ?
                        t.getMessage() : "Unknown error");
            }
        });
    }

    public static void getCategories(final ApiCallback2 apiCallback, Context context) {

        APIServiceClient.create(context).getCategories().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@Nullable Call<Object> call, @NonNull Response<Object> response) {
                Log.d(LOG_TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject1 = new JSONObject(gson.toJson(response.body()));
                        if (jsonObject1.optBoolean("Status")) {
                            List<Categories> categories = gson.fromJson(String.valueOf(jsonObject1.getJSONObject("Data").getJSONArray("records")), new TypeToken<List<Categories>>() {
                            }.getType());
                            apiCallback.onResponse(categories);
                        } else {
                            apiCallback.onResponse("Category list is empty");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    //When the fundsTransferResponse is unsuccessful
                    try {
                        //Pass the error to the callback
                        apiCallback.onFail(response.errorBody() != null ?
                                response.errorBody().string() : "Unknown error");
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
                apiCallback.onFail(t.getMessage() != null ?
                        t.getMessage() : "Unknown error");
            }
        });
    }

    //MultipartBody.Part multipartBody
    public static void addInventory(String fileName, MultipartBody.Part multipartBody, Map<String, String> map, ApiCallback apiCallback) {

        APIServiceClient.createMultipart(context).addInventory(multipartBody,
                map.get("merchant_id")
                , map.get("product_name")
                , map.get("category_name")
                , map.get("price")
                , map.get("quantity")
                , map.get("size")
                , map.get("color_name")
                , map.get("color_code"), fileName
        ).enqueue(new Callback<Object>() {
            //Called when the response is received
            @Override
            public void onResponse(@Nullable Call<Object> call, @NonNull Response<Object> response) {
                Log.e(LOG_TAG, "onResponse: " + response);
                if (response.isSuccessful()) {

                } else {
                    try {
                        //Pass the error to the callback
                        apiCallback.onError(response.errorBody() != null ?
                                response.errorBody().string() : "Unknown error");
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

    public static void addInventoryMultipart(String fileName, MultipartBody.Part multipartBody, Map<String, String> map, ApiCallback apiCallback) {

        APIServiceClient.createMultipart(context).addInventory(multipartBody,
                map.get("merchant_id")
                , map.get("product_name")
                , map.get("category_name")
                , map.get("price")
                , map.get("quantity")
                , map.get("size")
                , map.get("color_name")
                , map.get("color_code"), fileName
        ).enqueue(new Callback<Object>() {
            //Called when the response is received
            @Override
            public void onResponse(@Nullable Call<Object> call, @NonNull Response<Object> response) {
                Log.e(LOG_TAG, "onResponse: " + response);
                if (response.isSuccessful()) {

                } else {
                    try {
                        //Pass the error to the callback
                        apiCallback.onError(response.errorBody() != null ?
                                response.errorBody().string() : "Unknown error");
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


    public static void addInventory11(APIService service, String fileName, MultipartBody.Part multipartBody, Map<String, RequestBody> map, ApiCallback apiCallback) {

        service.addInventory11(multipartBody,
                map
        ).enqueue(new Callback<Object>() {
            //Called when the response is received
            @Override
            public void onResponse(@Nullable Call<Object> call, @NonNull Response<Object> response) {
                Log.e(LOG_TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    //Retrieving the Word Items when the response is successful
                    //List<MessageDTO> items=null;

                    if (response.body() != null) {
                        Gson gson = new Gson();
                        //items = response.body().items;
                        try {
                            JSONObject jsonObject1 = new JSONObject(gson.toJson(response.body()));
                            //JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            JSONArray jsonArray = jsonObject1.getJSONArray(Constants.PN_DATA);
                            if (jsonArray != null && jsonArray.length() > 0) {
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                Inventory inventory = gson.fromJson(jsonObject.toString(), new TypeToken<Inventory>() {
                                }.getType());
                                apiCallback.onSuccess(inventory);
                            } else {
                                apiCallback.onError("Failed to save your inventory.Please try again later");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            apiCallback.onError("Unknown error occured. Please try again later");
                        }

                    } else {
                        apiCallback.onError(response.message());
                    }

                    //Pass the result to the callback
                    //apiCallback.onSuccess(items);
                } else {
                    //When the response is unsuccessful
                    try {
                        //Pass the error to the callback
                        apiCallback.onError(response.errorBody() != null ?
                                response.errorBody().string() : "Unknown error");
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

    public static void addInventory1(APIService service, RequestBody requestBody, ApiCallback apiCallback) {

        service.addInventory1(requestBody).enqueue(new Callback<Object>() {
            //Called when the response is received
            @Override
            public void onResponse(@Nullable Call<Object> call, @NonNull Response<Object> response) {
                Log.e(LOG_TAG, "onResponse: " + response);
                if (response.isSuccessful()) {

                } else {
                    try {
                        apiCallback.onError(response.errorBody() != null ?
                                response.errorBody().string() : "Unknown error");
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

    public static void addInventory1Multipart(RequestBody requestBody, ApiCallback apiCallback) {

        APIServiceClient.createMultipart(context).addInventory1(requestBody).enqueue(new Callback<Object>() {
            //Called when the response is received
            @Override
            public void onResponse(@Nullable Call<Object> call, @NonNull Response<Object> response) {
                Log.e(LOG_TAG, "onResponse: " + response);
                if (response.isSuccessful()) {

                } else {
                    try {
                        apiCallback.onError(response.errorBody() != null ?
                                response.errorBody().string() : "Unknown error");
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


    /**
     * Default interceptor GET
     * The default interceptor cache is only for GET requests and does not cache POST
     */
    private static Interceptor defcacheInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!ConnectivityReceiver.isConnected()) {//Mandatory read from cache without network
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            okhttp3.Response response = chain.proceed(request);
            okhttp3.Response responseLatest;
            if (ConnectivityReceiver.isConnected()) {
                int maxAge = 0; //Setting up a cache timeout time of 0 hours when there is a network
                responseLatest = response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")// Clear header information, because if the server does not support it, it will return some interference information. Clear header information will not take effect below.
                        .build();
            } else {
                int maxStale = 60 * 60 * 72; // Failure of power grid for 72 hours
                responseLatest = response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return responseLatest;
        }
    };


    /**
     * cache
     *
     * @return
     */
    private static Cache defcache() {
        int cacheSize = 10 * 1024 * 1024;
        if (context == null) {
            throw new NullPointerException("Not in Application Middle initial");
        }
        //return new Cache(PathUtils.getDiskCacheDir(context, BASE_CACHE_PATH), cacheSize);
        //context.getCacheDir()
        return new Cache(context.getCacheDir(), cacheSize);
    }


    /**
     * Default OKHttp configuration
     *
     * @return
     */
    private static OkHttpClient.Builder getdefOkhttp() {
        //Log correlation
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.readTimeout(10, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(10, TimeUnit.SECONDS);
        okHttpClient.addInterceptor(new HeaderInterceptor(context));

        okHttpClient.addInterceptor(logging);

        //Failure reconnect
        okHttpClient.retryOnConnectionFailure(true);
        return okHttpClient;
    }

    private static <T> T createService(Class<T> serviceClass) {
        //--------------------------------------------------------------------------------------------------------------------------------------------------------------------
        OkHttpClient.Builder okHttpClient = getdefOkhttp();
        //Setting Cache Directory
        okHttpClient.cache(defcache());
        //Set cache
        okHttpClient.addInterceptor(defcacheInterceptor);
        okHttpClient.addNetworkInterceptor(defcacheInterceptor);
        //--------------------------------------------------------------
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_STORM)
                //Setting up OKHttpClient
                .client(okHttpClient.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                //gson converter
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create(new Persister(new AnnotationStrategy())))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//
                .build();

        return retrofit.create(serviceClass);
    }

//    public static void transferFundsRest(JsonObject jsonObject, final ApiCallback2 callback, Context context) {
//        APIServiceClient.create(context).fundTransfer(jsonObject)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Response<Object>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        Log.e("msg", "onSubscribe");
//                        callback.onStart();
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Response<Object> response) {
//                        if (response.isSuccessful()) {
//
//                            if (response.body() != null) {
//                                Gson gson = new Gson();
//
//                                try {
//
//                                    JSONObject jsonObject1 = new JSONObject(gson.toJson(response.body()));
//
//                                    if (jsonObject1.getInt("success") == 1) {
//
//                                        callback.onResponse(jsonObject1);
//
//                                    } else {
//
//                                        if(jsonObject1.has("msg")) {
//                                            callback.onFail(jsonObject1.getString("message"));
//
//                                        } else if(jsonObject1.has("message")) {
//                                            callback.onFail(jsonObject1.getString("message"));
//                                        } else if(jsonObject1.has("error") && jsonObject1.getJSONArray("error") != null && jsonObject1.getJSONArray("error").length() > 0) {
//                                            callback.onFail(jsonObject1.getJSONArray("error").getString(0));
//                                        } else {
//                                            callback.onFail("An unexpected error occurred. Please try again later");
//
//                                        }
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                    Log.e("APIServiceClient", e.getMessage());
//                                    callback.onFail("An unexpected error occurred. Please try again later");
//                                }
//
//                            } else {
//
//                                String message = response.message();
//                                callback.onFail(message);
//                            }
//
//                        } else {
//                            onRequestFailed(response, callback, USER_TYPE_AGENT);
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.e("onError", e.getMessage());
//                        callback.onFail(e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("onComplete", "---------------------------------------------");
//                        callback.onEnd();
//                    }
//                });
//    }


//    public static void addTransaction(JsonObject data, final ApiCallback2 callBack, Context context) {
//        APIServiceClient.create(context).addTransaction(data)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Response<Object>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        Log.e("msg", "onSubscribe");
//                        callBack.onStart();
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Response<Object> response) {
//                        if (response.isSuccessful()) {
//                            if (response.body() != null) {
//                                Gson gson = new Gson();
//                                //items = fundsTransferResponse.body().items;
//                                try {
//                                    JSONObject jsonObject1 = new JSONObject(gson.toJson(response.body()));
//                                    boolean status = false;
//                                    String message = jsonObject1.optString("message", "No response obtained");
//                                    if (jsonObject1.has("Status")) {
//                                        status = jsonObject1.getBoolean("Status");
//                                    } else if (jsonObject1.has("status")) {
//                                        status = jsonObject1.getBoolean("status");
//                                    }
//                                    if (status) {
//                                        callBack.onResponse(new Boolean(true));
//                                    } else {
//                                        callBack.onFail(message);
//                                    }
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                    callBack.onFail("Unknown error occured. Please try again later");
//                                }
//
//                            } else {
//
//                                String message = response.message();
//                                callBack.onFail(message);
//                            }
//
//                        } else {
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.e("onError", e.getMessage());
//                        callBack.onFail(e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("onComplete", "---------------------------------------------");
//                        callBack.onEnd();
//                    }
//                });
//
//    }


    public static void notifyWalletCredits(JsonObject data, final ApiCallback2 callBack, Context context) {
        APIServiceClient.create(context).notifyWalletCredits(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Object>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("msg", "onSubscribe");
                        callBack.onStart();
                    }

                    @Override
                    public void onNext(@NonNull Response<Object> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Gson gson = new Gson();
                                //items = fundsTransferResponse.body().items;
                                try {
                                    JSONObject jsonObject1 = new JSONObject(gson.toJson(response.body()));
                                    callBack.onResponse(jsonObject1);

                                    /*boolean status = false;
                                    String message = jsonObject1.optString("message","No response obtained");
                                    if(jsonObject1.has("Status"))
                                    {
                                        status = jsonObject1.getBoolean("Status");
                                    }
                                    else if(jsonObject1.has("status"))
                                    {
                                        status = jsonObject1.getBoolean("status");
                                    }
                                    if(status)
                                    {
                                        callBack.onResponse(new Boolean(true));
                                    }
                                    else
                                    {
                                        callBack.onFail(message);
                                    }*/


                                    /*if(status)
                                    {
                                        JSONObject data = jsonObject1.getJSONObject(Constants.PN_DATA);
                                        if (data != null && data.length() > 0) {
                                            User user = gson.fromJson(data.toString(), new TypeToken<User>() {
                                            }.getType());
                                            user.setUser_type(USER_TYPE_AGENT);
                                            SharedPrefManager.getInstance(context).setUser(user);
                                            //Pass the result to the callback
                                            callBack.onResponse(new Boolean(true));
                                        }
                                    }
                                    else
                                    {
                                        getOTPIfUnverified(jsonObject1, callBack, USER_TYPE_AGENT);
                                    }*/

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    callBack.onFail("Unknown error occured. Please try again later");
                                }

                            } else {

                                String message = response.message();
                                callBack.onFail(message);
                            }

                        } else {
                            //onRequestFailed(response, callBack, USER_TYPE_AGENT);
                            /*JSONObject jsonObject1 = null;
                            try {
                                jsonObject1 = new JSONObject(new Gson().toJson(response.errorBody()));
                                getOTPIfUnverified(jsonObject1, callBack, USER_TYPE_AGENT);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("onError", e.getMessage());
                        callBack.onFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete", "---------------------------------------------");
                        callBack.onEnd();
                    }
                });

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
            } else if(jsonObject1.has("error") && jsonObject1.getJSONArray("error") != null && jsonObject1.getJSONArray("error").length() > 0) {
                String message = jsonObject1.getJSONArray("error").getString(0);
                callBack.onFail(message);
            }else {
                callBack.onFail("Unknown error occured. Please try again later");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public static void updateUser(JsonObject data, final ApiCallback2 callBack, Context context) {
        APIServiceClient.create(context).update(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Object>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("msg", "onSubscribe");
                        callBack.onStart();
                    }

                    @Override
                    public void onNext(@NonNull Response<Object> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                try {
                                    JSONObject jsonObject1 = new JSONObject(new Gson().toJson(response.body()));
                                    if (jsonObject1.optBoolean("Status", false)) {
                                        JSONArray array;
                                        if (jsonObject1.has(Constants.PN_DATA)) {
                                            array = jsonObject1.getJSONArray(Constants.PN_DATA);
                                        } else {
                                            array = jsonObject1.getJSONArray(Constants.PN_DATA_2);
                                        }
                                        //JSONArray array = jsonObject1.getJSONArray(Constants.PN_DATA);
                                        //JSONObject data = jsonObject1.getJSONObject(Constants.PN_DATA);
                                        JSONObject data = array.getJSONObject(0);

                                        if (data != null && data.length() > 0) {
                                            User user = new Gson().fromJson(data.toString(), new TypeToken<User>() {
                                            }.getType());
                                            user.setUser_type(USER_TYPE_AGENT);
                                            SharedPrefManager.setUser(user);
                                            //Pass the result to the callback
                                            callBack.onResponse(new Boolean(true));
                                        }
                                    } else {
                                        String str = jsonObject1.optString("message", "Unknown error occured. Please try again later");
                                        callBack.onFail(str);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    callBack.onFail("Unknown error occured. Please try again later");
                                }

                            } else {
                                String message = response.message();
                                callBack.onFail(message);//"Unknown error occured. Please try again later"
                            }

                        } else {
                            onRequestFailed(response, callBack, USER_TYPE_AGENT);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("msg", e.getMessage());
                        callBack.onFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("msg", "---------------------------------------------");
                        callBack.onEnd();
                    }
                });
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
            /*catch (JSONException e) {
                e.printStackTrace();
                callBack.onFail("Unknown error occured. Please try again later");
            }*/

        } else {
            String message = response.message();
            callBack.onFail(message);
        }

        //When the fundsTransferResponse is unsuccessful
                            /*try {
                                //Pass the error to the callback
                                apiCallback.onError(response.errorBody() != null ?
                                        response.errorBody().string() : "Unknown error");
                            } catch (IOException e) {
                                Log.e(LOG_TAG, "onResponse: Failed while reading errorBody: ", e);
                            }*/
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
