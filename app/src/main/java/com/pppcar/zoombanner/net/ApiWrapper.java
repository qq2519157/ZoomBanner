package com.pppcar.zoombanner.net;



/**
 * Created by TANG on 2018-03-15.
 */

public class ApiWrapper {
    private static ApiWrapper mInstance;
    private static MyNetProvider netProvider;


    private ApiWrapper() {
        if (!XApi.getInstance().isRegisterProvider()) {
            if (netProvider == null) {
                netProvider = new MyNetProvider();
            }
            XApi.getInstance().registerProvider(netProvider);
        }
    }

    public static ApiWrapper getInstance() {
        if (mInstance == null) {
            synchronized (ApiWrapper.class) {
                if (mInstance == null) {
                    mInstance = new ApiWrapper();
                }
            }
        }
        return mInstance;
    }

    public static ApiService getApiService() {
        if (!XApi.getInstance().isRegisterProvider()) {
            if (netProvider == null) {
                netProvider = new MyNetProvider();
            }
            XApi.getInstance().registerProvider(netProvider);
        }
        return XApi.getService(ApiService.class);
    }

}
