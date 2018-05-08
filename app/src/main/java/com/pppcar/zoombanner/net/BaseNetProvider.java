package com.pppcar.zoombanner.net;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import static com.pppcar.zoombanner.net.XApi.CONNECT_TIME_OUT_MILLS;
import static com.pppcar.zoombanner.net.XApi.READ_TIME_OUT_MILLS;

/**
 * Created by TANG on 2018-03-14.
 */

public abstract class BaseNetProvider implements NetProvider {

    protected OkHttpClient.Builder mBuilder;

    @Override
    public Interceptor[] getInterceptors() {
        return new Interceptor[0];
    }

    @Override
    public void configHttps(OkHttpClient.Builder builder) {
        this.mBuilder = builder;
    }

    @Override
    public RequestHandler getHandler() {
        return new DefaultRequestHandler();
    }

    @Override
    public long getConnectTimeoutMills() {
        return CONNECT_TIME_OUT_MILLS;
    }

    @Override
    public long getReadTimeoutMills() {
        return READ_TIME_OUT_MILLS;
    }

    @Override
    public boolean isLogEnable() {
        return false;
    }


    @Override
    public boolean isRetryOnConnectionFailure() {
        return false;
    }

    @Override
    public boolean handleError(NetError error) {
        return false;
    }
}
