package com.woleapp.network

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.woleapp.BuildConfig
import com.woleapp.util.SharedPrefManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object StormUtilitiesApiClient {

    //private const val BASE_URL_TEST = "http://storm-utilities.test.netpluspay.com/"
    //private const val BASE_URL_LIVE = "https://storm-utilities.netpluspay.com/
    private const val BASE_URL = BuildConfig.BASE_URL_STORM_UTILITIES

    @Volatile
    private var INSTANCE: StormUtilitiesApiService? = null
    fun getStormUtilitiesApiClientInstance(context: Context): StormUtilitiesApiService {
        return INSTANCE ?: synchronized(this) {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(StormUtilitiesApiService::class.java)
                .also {
                    INSTANCE = it
                }
        }
    }


}

fun getCustomGsonObject(): Gson = GsonBuilder()
    .run {
        excludeFieldsWithoutExposeAnnotation()
        create()
    }


fun getOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(TokenInterceptor()).apply {
        if (BuildConfig.DEBUG)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
    }
    .build()

object HealthCheckerClient {
    private const val BASE_URL = "http://healthcheck.test.netpluspay.com/api/"
    private const val BASE_URL_TEMP = "https://f0f80747f8d1.ngrok.io/api/"
    @Volatile
    private var INSTANCE: HealthCheckerServices? = null

    @JvmStatic
    fun getHealthCheckerInstance(): HealthCheckerServices = INSTANCE ?: synchronized(this){
        Retrofit.Builder()
            .client(getOkHttpClient())
            .baseUrl(BASE_URL_TEMP)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(HealthCheckerServices::class.java)
            .also {
                INSTANCE = it
            }

    }
}

object MerchantsApiClient {
    //private const val BASE_URL_LIVE = "https://storm-merchants.netpluspay.com"
    //private const val BASE_URL = "http://storm-merchants.test.netpluspay.com"
    private const val BASE_URL = BuildConfig.BASE_URL_STORM_MERCHANTS

    @Volatile
    private var INSTANCE: MerchantsApiService? = null

    @JvmStatic
    fun getMerchantApiService(context: Context): MerchantsApiService {
        return INSTANCE ?: synchronized(this) {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getCustomGsonObject()))
                .build()
                .create(MerchantsApiService::class.java)
                .also {
                    INSTANCE = it
                }
        }
    }
}

class TokenInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token: String? = SharedPrefManager.getUserToken()
        val request = chain.request()
        //Timber.e(request.url.toString())
//        val method = request.method
//        if (method == "POST" || method == "PUT") {
//            val reqBody = request.body!!
//            Timber.e(reqBody.toString())
//            val contentLength = reqBody.contentLength()
//            Timber.e("content_length $contentLength")
//        }
        //Log.e("interceptor", "token $token")
        //val headersInReq = request.headers()
        //Log.e("interceptor", "headers: ${headersInReq.size()}")
        //headersInReq.names().forEach { Log.e("Header", "$it : ${headersInReq.get(it)}") }
        val response = chain.proceed(request.newBuilder().run {
            token?.let {
                addHeader(
                    "Authorization",
                    "Bearer $it"
                )
            }
            build()
        })
//        val body = response.body
//        val bodyString = body?.string()
//        Timber.e(bodyString!!)
//        return response.newBuilder().body(ResponseBody.create(body.contentType(), bodyString))
//            .build()
        return response
    }
}