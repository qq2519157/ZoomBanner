package com.pppcar.zoombanner.net;


import com.pppcar.zoombanner.Constant;

import okhttp3.Interceptor;

/**
 * Created by TANG on 2018-03-14.
 */

public class MyNetProvider extends BaseNetProvider {
    @Override
    public String getBaseUrl() {
        return Constant.HOST;
    }

    @Override
    public boolean isLogEnable() {
        return true;
    }

    @Override
    public Interceptor[] getInterceptors() {
        return new Interceptor[]{new HeaderInterceptor()};
    }

    @Override
    public boolean isRetryOnConnectionFailure() {
        return true;
    }
}
