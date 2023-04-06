package com.woleapp.network

import com.woleapp.model.*

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface StormUtilitiesApiService {
    @POST("api/utility/pay_bill")
    fun payBills(@Body payload: UtilitiesPayload): Single<SuccessNetworkResponse>

    /*@GET("api/utility/single_provider_fees/{provider_name}")
    fun getServiceFee(@Path("provider_name") providerName: String): Single<ServiceFeeResponse>*/

    @POST("api/utility/validate_bill")
    fun validateBill(@Body payload: UtilitiesPayload): Single<ValidateBillResponse>
}

interface MerchantsApiService {
    @Multipart
    @POST("api/merchant/products/{merchant_id}")
    fun postProduct(
        @Path("merchant_id") merchantId: String,
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part image: MultipartBody.Part
    ): Single<BaseResponse<Any>>

    @Multipart
    @PUT("api/merchant/products/{merchant_id}/{product_id}")
    fun updateProduct(
        @Path("merchant_id") merchantId: String,
        @Path("product_id") productId: String,
        @PartMap inventoryMap: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part image: MultipartBody.Part?
    ): Single<BaseResponse<Any>>

    /*@GET("api/merchant/products/{merchant_id}/{product_id}")
    fun getProductDetails(
        @Path("merchant_id") merchantId: String,
        @Path("product_id") productId: String
    ): Single<BaseResponse<Inventory>>*/

    @GET("api/merchant/products/{merchant_id}")
    fun getAllProducts(@Path("merchant_id") merchantId: String): Single<BaseResponse<List<Inventory>>>

    @DELETE("api/merchant/products/{merchant_id}/{product_id} ")
    fun deleteProduct(
        @Path("merchant_id") merchantId: String,
        @Path("product_id") productId: String
    ): Single<BaseResponse<Any>>

    @FormUrlEncoded
    @POST("api/merchant/saleorder/{merchant_id}")
    fun checkout(
        @Path("merchant_id") merchantId: String,
        @FieldMap checkoutMap: Map<String, String>
    ): Single<BaseResponse<Any>>

    @FormUrlEncoded
    @POST("api/merchant/transaction/{merchant_id}")
    fun logTransaction(
        @Path("merchant_id") merchantId: String,
        @FieldMap payload: Map<String, String>
    ): Single<BaseResponse<Any>>

    @GET("api/merchant/transaction/{merchant_id}")
    fun getTransactions(@Path("merchant_id") merchantId: String): Single<BaseResponse<List<MerchantTransaction>>>

    @Multipart
    @POST("api/merchant/stores/{merchant_id}")
    fun createStore(
        @Path("merchant_id") merchantId: String,
        @PartMap storeMap: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part logo: MultipartBody.Part
    ): Single<BaseResponse<Any>>

    @GET("api/merchant/stores")
    fun getStores(): Single<BaseResponse<List<Marketplace>>>

    @GET("api/merchant/storeexists/{merchant_id}")
    fun checkStoreExists(@Path("merchant_id") merchantId: String): Single<BaseResponse<Marketplace>>

    @GET("api/merchant/merchantorder/{merchant_id}")
    fun getSellersOrders(@Path("merchant_id") merchantId: String): Single<BaseResponse<List<SalesOrder>>>

    @GET("api/merchant/referenceorder/{merchant_id}")
    fun getBuyerOrder(@Path("merchant_id") merchantId: String): Single<BaseResponse<List<SalesOrder>>>

    @FormUrlEncoded
    @PUT("api/merchant/saleorder/{merchant_id}/{seller_id}")
    fun updateSaleOrder(
        @Path("merchant_id") buyerId: String,
        @Path("seller_id") sellerId: String,
        @FieldMap checkoutMap: Map<String, String>
    ): Single<BaseResponse<Any>>

    @Multipart
    @POST("api/merchant/stores/{merchant_id}")
    fun updateStore(
        @Path("merchant_id") merchantId: String,
        @PartMap storeMap: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part logo: MultipartBody.Part
    ): Single<BaseResponse<Any>>
}

interface HealthCheckerServices {
    @POST("activate-agent")
    fun activateAgent(): Single<HealthCheckerResponse>

    @POST("request")
    fun request(@Body healthCheckerModel: HealthCheckerModel): Single<HealthCheckerResponse>
}