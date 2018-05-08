package com.pppcar.zoombanner.net;

import android.os.Build;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by TANG on 2018-03-14.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request.Builder build = originalRequest.newBuilder();

        if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
//            build.addHeader("Connection", "close");
        }

        Request request = build.build();

        return chain.proceed(request);
    }
}
