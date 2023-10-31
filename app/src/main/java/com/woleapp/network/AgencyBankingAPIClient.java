package com.woleapp.network;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.woleapp.ui.fragments.CollectionsNewFragment;
import com.woleapp.util.SessionManager;
import com.woleapp.util.SharedPrefManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AgencyBankingAPIClient {
    public static final String BASE_URL = "http://abms.viobex.com//";
    public static final String BASE_URL_2 = "https://peoplespay.com.gh";
    public static final String BASE_URL_1 = "http://3.128.25.124";
    private static AgencyBankingService agencyAPI;
    private static AgencyBankingService agencyAPIDefault;
    private static AgencyBankingService agencyPPay;
    private static AgencyBankingService agencyBanking;

    static Context context;

    public static synchronized AgencyBankingService create(Context context) {

        AgencyBankingAPIClient.context = context;
        if(agencyAPI == null) {
            agencyAPI = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHttpClient(context, true))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(AgencyBankingService.class);
        }
        return agencyAPI;
    }

    public static synchronized AgencyBankingService createDefault(Context context) {

        AgencyBankingAPIClient.context = context;
        if(agencyAPIDefault == null) {
            agencyAPIDefault = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHttpClient(context, false))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(AgencyBankingService.class);
        }
        return agencyAPIDefault;
    }

    public static synchronized AgencyBankingService createPPay(Context context) {

        AgencyBankingAPIClient.context = context;
        if(agencyPPay == null) {
            agencyPPay = new Retrofit.Builder()
                    .baseUrl(BASE_URL_2)
                    .client(getHttpClient(context, true))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(AgencyBankingService.class);
        }
        return agencyPPay;
    }
    public static synchronized AgencyBankingService createSanlamUser(Context context){
        AgencyBankingAPIClient.context = context;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if(agencyBanking == null) {
            agencyBanking = new Retrofit.Builder()
                    .baseUrl(BASE_URL_1)
                    .client(getHttpClient(context, true))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(AgencyBankingService.class);
        }
        return agencyBanking;
    }

    public static OkHttpClient getHttpClient(Context context, boolean addTokenInterceptor) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(interceptor);
        if(addTokenInterceptor)
            builder.addInterceptor(new TokenInterceptor());
        builder.connectTimeout(2, TimeUnit.MINUTES);
        builder.readTimeout(2, TimeUnit.MINUTES);
        builder.writeTimeout(2, TimeUnit.MINUTES);
        final OkHttpClient okHttpClient = builder.build();

        return okHttpClient;
    }

public static Observable<Response<Object>> agencyLogin(JsonObject payload, Context context) {
    return AgencyBankingAPIClient.createPPay(context).agencyLogin(payload)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
}

    public static Observable<Response<Object>> changePassword(JsonObject payload, Context context) {
        return AgencyBankingAPIClient.createDefault(context).changePassword(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> allTime(JsonObject payload, Context context) {
        return AgencyBankingAPIClient.createDefault(context).allTimeReport(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> weeklyReports(JsonObject payload, Context context) {
        return AgencyBankingAPIClient.createDefault(context).weekReport(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> monthlyReport(JsonObject payload, Context context) {
        return AgencyBankingAPIClient.createDefault(context).monthlyReport(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> yearlyReport(JsonObject payload, Context context) {
        return AgencyBankingAPIClient.createDefault(context).yearlyReport(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> branchBalance(JsonObject payload, Context context) {
        return AgencyBankingAPIClient.createDefault(context).branchBalance(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> sendTransaction(JsonObject payload, Context context) {
        return AgencyBankingAPIClient.createDefault(context).sendTransaction(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static  Observable<Response<Object>> agencyDetails(JsonObject payload, Context context) {
        return AgencyBankingAPIClient.createDefault(context).agencyDetails(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> branchDetails(JsonObject payload, Context context) {
        return AgencyBankingAPIClient.createDefault(context).branchDetails(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Response<Object>> bankList(JsonObject payload, Context context) {
        return AgencyBankingAPIClient.createDefault(context).bankList(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<Response<Object>> pPayInit(JsonObject payload, Context context) {
        return AgencyBankingAPIClient.createPPay(context).pPayInit(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<Response<Object>> collectionsInit(JsonObject payload, Context context) {
        return AgencyBankingAPIClient.createPPay(context).collectionsInit(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<Response<Object>> getBankList(Context context) {
        return AgencyBankingAPIClient.createPPay(context).getBankList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<Response<Object>> sanlamSignUp(JsonObject payload, Context context) {
        return AgencyBankingAPIClient.createSanlamUser(context).sanlamSignUp(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<Response<Object>> sanlamClaims(JsonObject payload, Context context) {
        return AgencyBankingAPIClient.createSanlamUser(context).sanlamClaims(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
//    public static Observable<Response<Object>> cardInit(JsonObject payload, Context context) {
//        return AgencyBankingAPIClient.createPPay(context).cardInit(payload)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

}


