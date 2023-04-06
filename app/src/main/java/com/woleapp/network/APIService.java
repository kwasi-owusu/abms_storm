package com.woleapp.network;



import com.google.gson.JsonObject;
import com.woleapp.network.soap.request.RequestEnvelope;
import com.woleapp.network.soap.response.ResponseEnvelope;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.Map;

/**
 * Created by Inverse, LLC on 10/18/16.
 */

public interface APIService {
    // String API_END_POINT = "http://mywebsitepreview.co/GPH-Demo/";
    //String API_END_POINT = "https://geeksperhour.com/GPH-Mobile-API/";

    String API_END_POINT = "https://geeksperhour.com/portal/index.php/api/";

    @FormUrlEncoded
    @POST("user-login.php")
    Call<Object> login(@Field("user_name") String user_name,
                       @Field("user_password") String user_password);


    @POST("agent/logout.php")
    Observable<Response<Object>> agentLogout(@Body JsonObject data);

    @FormUrlEncoded
    @POST("auth/login")
    Call<Object> doLogin(@Field("email") String user_name,
                         @Field("password") String user_password,
                         @Field("deviceId") String deviceId);

    @FormUrlEncoded
    @Streaming
    @POST("jobs-management.php")
    Call<Object> projects(@Field("geek_id") String geek_id);

    //@FormUrlEncoded
    //@Streaming
    @GET("jobs/list")
    Call<Object> getProjects(@Query("page") Integer page, @Query("limit") Integer limit);

    @GET("jobs/message")
    Call<Object> getMessages(@Query("page") Integer page, @Query("limit") Integer limit, @Query("jobId") Integer jobId);


    @FormUrlEncoded
    @Streaming
    @POST("message.php")
    Call<Object> message(@Field("project_id") String project_id);

    @FormUrlEncoded
    @Streaming
    @POST("send-message.php")
    Call<Object> send_message(@Field("project_id") String project_id,
                              @Field("geek_id") String geek_id,
                              @Field("client_id") String client_id,
                              @Field("message") String message,
                              @Field("attached_file") String attached_file,
                              @Field("from_id") String from_id,
                              @Field("to_id") String to_id,
                              @Field("message_status") String message_status,
                              @Field("reminder_message") String reminder_message);


    /*@Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/Move_GetDate"
    })
    @POST("DBHelperOra.asmx")
    Observable<ResponseEnvelope_Ref> getAssetInfo(@Body RequestEnvelope_Ref requestEnvelope);*/


    @Headers({"Content-Type: text/xml;charset=UTF-8"})
    // , "SOAPAction: http://WebXml.com.cn/getWeatherbyCityName"
    @POST("FGate/ws?wsdl")
//https://www.etranzact.net/FGate/ws?wsdl  //WeatherWebService.asmx
    Observable<ResponseEnvelope> getTransactionResponse(@Body RequestEnvelope requestEnvelope);
//"FGate/ws?wsdl" //TestFGate/ws?wsdl

    @Headers({"Content-Type: text/xml;charset=UTF-8"})
    // , "SOAPAction: http://WebXml.com.cn/getWeatherbyCityName"
    @POST("FGate/ws?wsdl")
//https://www.etranzact.net/FGate/ws?wsdl  //WeatherWebService.asmx
    Call<ResponseEnvelope> getTransactionResponse1(@Body RequestEnvelope requestEnvelope);

    //@FormUrlEncoded
    @POST("merchant/login.php")
    Observable<Response<Object>> doMerchantLogin(@Body JsonObject data);

    @POST("agent/login.php")
    Observable<Response<Object>> doAgentLogin(@Body JsonObject data); //ResponseBody

    @POST("agent/signup.php")
    Observable<Response<Object>> agentSignUp(@Body JsonObject data);

    @POST("merchant/update.php")
    Observable<Response<Object>> update(@Body JsonObject data);

    @POST("merchant/signup.php")
    Observable<Response<Object>> merchantSignUp(@Body JsonObject data); //ResponseBody

    @POST("merchant/isVerification.php")
    Observable<Response<Object>> verifyMerchant(@Body JsonObject data);

    @POST("agent/isVerification.php")
    Observable<Response<Object>> verifyAgent(@Body JsonObject data); //ResponseBody

    @Multipart
    //@FormUrlEncoded
    @POST("inventories/inventoryAdd.php")
        //  @Part("uploadfile") RequestBody
    Call<Object> addInventory(@Part MultipartBody.Part file,
                              //@FieldMap Map<String, String> params

                              @Part("merchant_id") String id,
                              @Part("product_name") String product_name,
                              @Part("category_name") String category_name,
                              @Part("price") String price,
                              @Part("quantity") String quantity,
                              @Part("size") String size,
                              @Part("color_name") String color_name,
                              @Part("color_code") String color_code,
                              @Part("image_url") String image_url
    );//uploadfile //@Query("name") String name,

    //@FormUrlEncoded
    //@FieldMap Map<String, String> params


    @Multipart
    //@FormUrlEncoded
    @POST("inventories/inventoryAdd.php")
        //  @Part("uploadfile") RequestBody
    Call<Object> addInventory1(@Part("image_ur") RequestBody requestBody);
    //uploadfile //@Query("name") String name,

    @Multipart
    @POST("inventories/inventoryAdd.php")
        //  @Part("uploadfile") RequestBody
    Call<Object> addInventory11(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> partMap);

    @POST("inventories/inventoryDecrement.php")
    Call<Object> inventoryDecrement(@Body JsonObject data);

    @POST("inventories/inventoryIncrement.php")
    Call<Object> inventoryIncrement(@Body JsonObject data);


    @POST("inventories/inventoriesList.php")
    Call<Object> getInventories(@Body JsonObject data);//@Query("page") Integer page, @Query("limit") Integer limit);

    @POST("category/getCategories.php")
    Call<Object> getCategories();

    @POST("transaction/getTransaction.php")
    Call<Object> getTransactions(@Body JsonObject data);

    @POST("transaction/addTransaction.php")
    Observable<Response<Object>> addTransaction(@Body JsonObject data);

    @POST("fund/pushNotify.php")
    Observable<Response<Object>> notifyWalletCredits(@Body JsonObject data);

    @POST("agent/resetPassword.php")
    Observable<Response<Object>> resetPassword(@Body JsonObject data);

    @GET("settings/getSettings.php")
    Observable<Response<Object>> getSettings(@Query("page") Long user_id);

    @POST("fundtransfer/createtransfer.php")
    Observable<Response<Object>> fundTransfer(@Body JsonObject jsonObject);

    @POST("agent/agentDatails.php")
    Observable<Response<Object>> getAgentDetails(@Body JsonObject jsonObject);


}
