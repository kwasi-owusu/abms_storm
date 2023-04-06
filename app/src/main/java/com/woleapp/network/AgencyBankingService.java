package com.woleapp.network;


import android.content.Context;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Observable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AgencyBankingService {

    @POST("api/auth/login")
    Observable<Response<Object>> agencyLogin(@Body JsonObject payload);

    @POST("api/auth/change_password")
    Observable<Response<Object>> changePassword(@Body JsonObject payload);

    @POST("/api/report/all_time")
    Observable<Response<Object>> allTimeReport(@Body JsonObject payload);

    @POST("/api/report/week")
    Observable<Response<Object>> weekReport(@Body JsonObject payload);

    @POST("/api/report/month")
    Observable<Response<Object>> monthlyReport(@Body JsonObject payload);

    @POST("/api/report/year")
    Observable<Response<Object>> yearlyReport(@Body JsonObject payload);

    @POST("/api/report/single")
    Observable<Response<Object>> singleReport(@Body JsonObject payload);

    @POST("/api/branch/balance")
    Observable<Response<Object>> branchBalance(@Body JsonObject payload);

    @POST("/api/transaction/doo")
    Observable<Response<Object>> sendTransaction(@Body JsonObject payload);

    @POST("/api/agency/details")
    Observable<Response<Object>> agencyDetails(@Body JsonObject payload);

    @POST("/api/agency/branch_details")
    Observable<Response<Object>> branchDetails(@Body JsonObject payload);

    @POST("/api/partners/list")
    Observable<Response<Object>> bankList(@Body JsonObject payload);

    @POST("/api/checkout/initiate")
    Observable<Response<Object>> pPayInit(@Body JsonObject payload);

    @POST("/peoplepay/hub/token/get")
    Observable<Response<Object>> collectionsInit(@Body JsonObject payload);

    @POST("/peoplepay/hub/collectmoney")
    Observable<Response<Object>> momoInit(@Body JsonObject payload);

    //@GET("/peoplepay/hub/transactions/get/{KEY}")
    // Call<Response<Object>> transactionStatus(@Path("KEY") String key);

    @GET("/peoplepay/hub/transactions/get/{KEY}")
    Observable<Response<Object>> transactionStatus(@Path("KEY") String key);

    @POST("/peoplepay/hub/collectmoney/card")
    Observable<Response<Object>> cardInit(@Body JsonObject payload);

    @GET("/peoplepay/issuers/get")
    Observable<Response<Object>> getBankList();

}
