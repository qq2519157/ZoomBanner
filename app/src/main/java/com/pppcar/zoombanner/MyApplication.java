package com.pppcar.zoombanner;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.hjq.http.EasyConfig;
import com.hjq.http.ssl.HttpSslConfig;
import com.hjq.http.ssl.HttpSslFactory;
import com.pppcar.zoombanner.network.RequestHandler;
import com.pppcar.zoombanner.network.RequestServer;
import com.tencent.mmkv.MMKV;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        init();
    }

    private void init() {
        String rootDir = MMKV.initialize(this);
        LogUtils.getConfig().setGlobalTag("LoganLog");
        LogUtils.e(rootDir);
        Utils.init(this);
        HttpSslConfig sslConfig = HttpSslFactory.generateSslConfig();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslConfig.getSslSocketFactory(), sslConfig.getTrustManager())
                .hostnameVerifier(HttpSslFactory.generateUnSafeHostnameVerifier())
                .build();

        EasyConfig.with(okHttpClient)
                // 是否打印日志
                .setLogEnabled(BuildConfig.DEBUG)
                // 设置服务器配置
                .setServer(new RequestServer())
                // 设置请求处理策略
                .setHandler(new RequestHandler(this))
                // 设置请求重试次数
                .setRetryCount(3)
                // 添加全局请求参数
//                .addParam("t", String.valueOf(System.currentTimeMillis()))
                // 添加全局请求头
                //.addHeader("time", "20191030")
                // 启用配置
                .into();
    }

    public static Context getInstance() {
        return mContext;
    }
}
