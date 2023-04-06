package com.woleapp.network;

import android.util.Log;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by Inverse, LLC on 10/20/16.
 */

public class RetryInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // try the request
        Response response = chain.proceed(request);

        int tryCount = 0;
        int maxLimit = 1; //Set your max limit here

        while (!response.isSuccessful() && tryCount < maxLimit) {

            Log.e("Request failed - Retry" , ": "+tryCount + 1);

            tryCount++;

            // retry the request
            response = chain.proceed(request);
        }

        // otherwise just pass the original fundsTransferResponse on
        return response;
    }
}
