package com.woleapp.network;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.woleapp.model.User;
import com.woleapp.util.SharedPrefManager;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Inverse, LLC on 10/18/16.
 */

public class HeaderInterceptor implements Interceptor {

    private String token;
    Context context;

    boolean isMultiPart = false;
    public HeaderInterceptor(String token) {
        this.token = token;
    }

    public HeaderInterceptor(Context context) {
        this.context = context;
    }

    public HeaderInterceptor(Context context,boolean isMultiPart) {
        this.context = context;
        this.isMultiPart = isMultiPart;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        if(isMultiPart)
        {
            String twoHyphens = "--";
            String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
            String lineEnd = "\r\n";

            //"multipart/form-data; boundary=--------------------------493874304739053227085907"
            builder.addHeader("Content-Type", "multipart/form-data;boundary=" + boundary);//"multipart/form-data; charset=utf-8; boundary=\"myboundary\""
            builder.addHeader("Accept", "*/*");
            /*builder.addHeader("Content-Type", "multipart/form-data; charset=utf-8; boundary=\"myboundary\"");
            builder.addHeader("Content-type", "application/json");
            builder.addHeader("Accept", "application/json");*/
        }
        else
        {
            builder.addHeader("Content-type", "application/json");
            builder.addHeader("Accept", "application/json");
        }
        Log.e("isMultiPart",isMultiPart+" ##");

//        if (!TextUtils.isEmpty(token)) {
//            builder.addHeader("Authorization", "Bearer " + token);
//            //builder.addHeader("Authorization", "Basic " + getBase64ClientSecret());
//        }

        return chain.proceed(builder.build());
    }

    public String getBase64ClientSecret()
    {
        String credentials = "";
        boolean isLogin = SharedPrefManager.isLogin();
        if(isLogin)
        {
            User user = SharedPrefManager.getUser();
            credentials = user.getEmail()+":"+user.getPassword();
        }
        else
        {
            credentials = "kishore@geeksperhour.com"+":"+"qqqqqq";
            //credentials = "vinod@futuretechnologies.co.in"+":"+"sneh001";
        }

        String base64 = "";
        byte[] data;
        try
        {
            data = credentials.getBytes("UTF-8");
            base64 = Base64.encodeToString(data, Base64.NO_WRAP);//DEFAULT
            Log.e("Encoded_Str",base64);
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return base64;
    }
}
