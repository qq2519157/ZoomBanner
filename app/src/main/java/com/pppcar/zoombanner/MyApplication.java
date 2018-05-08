package com.pppcar.zoombanner;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        init();
    }

    private void init() {
        // init it in the function of onCreate in ur Application
        Utils.init(this);
    }

    public static Context getInstance() {
        return mContext;
    }
}
