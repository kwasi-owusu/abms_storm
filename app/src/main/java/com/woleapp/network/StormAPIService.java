package com.woleapp.network;

import com.google.gson.JsonObject;
import com.woleapp.model.AgentTransactions;
import com.woleapp.model.SessionCode;
import com.woleapp.model.Transact;


import io.reactivex.Observable;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Inverse, LLC on 10/18/16.
 */
public interface StormAPIService {

    @POST("api/token")
    Observable<Response<Object>> appToken(@Body JsonObject credentials);

    @POST("api/auth")
    Observable<Response<Object>> userToken(@Header("Authorization") String appToken, @Body JsonObject credentials);

    @PUT("api/pushToken")
    Observable<Response<Object>> registerPushToken(@Body JsonObject payload);

    @GET("api/agents/{stormId}")
    Observable<Response<Object>> getAgentDetails(@Path("stormId") String stormId);

    @GET("api/merchants/{stormId}")
    Observable<Response<Object>> getMerchantDetails(@Path("stormId") String stormId);

    @GET("api/agent-balance/{stormId}")
    Observable<Response<Object>> getWalletBalance(@Path("stormId") String stormId);

    @GET("api/app-settings/storm_app")
    Observable<Response<Object>> getFee(@Header("Authorization") String appToken, @Query("name") String name);

    @POST("api/register")
    Observable<Response<Object>> registerUser(@Header("Authorization") String appToken, @Body JsonObject payload);

    @POST("api/verify")
    Observable<Response<Object>> verifyUser(@Header("Authorization") String appToken, @Body JsonObject payload);

    @POST("api/passwordReset")
    Observable<Response<Object>> passwordReset(@Header("Authorization") String appToken, @Body JsonObject payload);

    @POST("api/transfer")
    Observable<Response<Object>> transfer(@Body JsonObject payload);

    @GET("api/transactions")
    Single<AgentTransactions> getTransactions(@Query("stormId") String stormId, @Query("page") int page, @Query("count") int count);

    @GET("api/account-name")
    Observable<Response<Object>> getAccountName(@Header("Authorization") String token, @Query("bankAccountNumber") String accountNumber, @Query("bankCode") String bankCode);

    @GET("api/products")
    Observable<Response<Object>> getProducts();

    @POST("api/transactions")
    Single<Response<Object>> logNewTransaction(@Body Transact payload);

    @GET("api/nip-notifications")
    Single<Response<Object>> getNotificationByReference(
            @Header("X-CLIENT-ID") String xClientId,
            @Header("X-ACCESSCODE") String xAccessCode,
            @Query("referenceNo") String referenceNo);

    @GET("api/sessionCode")
    Single<SessionCode> getSessionCode();
}
