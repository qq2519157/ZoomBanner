package com.pppcar.zoombanner.net;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by TANG on 2018-03-13.
 */

public interface NetProvider {
    String getBaseUrl();

    Interceptor[] getInterceptors();

    void configHttps(OkHttpClient.Builder builder);

//    CookieJar configCookie();

    RequestHandler getHandler();

    long getConnectTimeoutMills();

    long getReadTimeoutMills();

    boolean isLogEnable();

    boolean isRetryOnConnectionFailure();

    boolean handleError(NetError error);
}
