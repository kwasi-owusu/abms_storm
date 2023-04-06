package com.woleapp.network;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.SharedPreferences;

import com.woleapp.ui.fragments.CollectionsNewFragment;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CollectTokenInterceptor extends CollectionsNewFragment implements Interceptor {
    public static final String BASE_URL_2 = "https:/peoplespay.com.gh";
    SharedPreferences preferences = getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
    String retrivedToken  = preferences.getString("TOKEN",null);
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request newRequest=chain.request().newBuilder()
                .header("Authorization","Bearer "+ retrivedToken)
                .build();

        return chain.proceed(newRequest);
    }

    CollectTokenInterceptor interceptor= new CollectTokenInterceptor();

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL_2)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
