package com.pppcar.zoombanner.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by TANG on 2018-03-13.
 */

public class XInterceptor implements Interceptor {
    private final RequestHandler mHandler;

    public XInterceptor(RequestHandler requestHandler) {
        this.mHandler = requestHandler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (mHandler != null) {
            mHandler.onBeforeRequest(request, chain);
        }

        Response response = chain.proceed(request);
//        try {
//            if (mHandler != null) {
//
//                Response.Builder builder = response.newBuilder();
//                Response clone = builder.build();
//                ResponseBody body = clone.body();
//
//                mHandler.onAfterRequest(response,body., chain);
//            }
//        } catch (Exception e) {
//            Logger.d("e=" + e.toString());
//        }
        return response;
    }
}
