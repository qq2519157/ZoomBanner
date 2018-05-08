package com.pppcar.zoombanner.net;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by TANG on 2018-03-13.
 */

public interface RequestHandler {
    Request onBeforeRequest(Request request, Interceptor.Chain chain);


    void onAfterRequest(String result);
}
