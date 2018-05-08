package com.pppcar.zoombanner.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by TANG on 2018-03-13.
 */

public class XApi {
    public static final long CONNECT_TIME_OUT_MILLS = 10 * 1000l;
    public static final long READ_TIME_OUT_MILLS = 10 * 1000l;

    private Retrofit retrofit;

    private static XApi instance;
    private static NetProvider mNetProvider;


    private XApi() {

    }

    public static XApi getInstance() {
        if (instance == null) {
            synchronized (XApi.class) {
                if (instance == null) {
                    instance = new XApi();
                }
            }
        }
        return instance;
    }

    public static <S> S getService(Class<S> service) {
        return getInstance().getRetrofit(true).create(service);
    }

    public void registerProvider(NetProvider provider) {
        mNetProvider = provider;
    }

    public boolean isRegisterProvider() {
        return !ObjectUtils.isEmpty(mNetProvider);
    }


    private Retrofit getRetrofit(boolean useRx) {
        checkProvider();
        if (retrofit == null) {

            Retrofit.Builder builder = new Retrofit.Builder()
                    .client(getHttpClient())
                    .baseUrl(mNetProvider.getBaseUrl())
//                    .addConverterFactory(GsonConverterFactory.create());
                    .addConverterFactory(CustomGsonConverterFactory.create(mNetProvider));

            if (useRx) {
                builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            }
            retrofit = builder.build();
        }
        return retrofit;
    }



    public static class ParamBuilder {
        Map<String, String> paramContainer;

        public ParamBuilder() {
            paramContainer = new HashMap<>();
        }

        public ParamBuilder putParam(String key, String value) {
            if (!StringUtils.isEmpty(value)) {
                paramContainer.put(key, value);
            }
            return this;
        }

        public Map<String, String> getMap() {
            return paramContainer;
        }
    }

    public static ParamBuilder getParamBuilder() {
        return new ParamBuilder();
    }








    private void checkProvider() {
        if (mNetProvider == null || TextUtils.isEmpty(mNetProvider.getBaseUrl())) {
            throw new IllegalStateException("must register provider first");
        }
    }

    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    private OkHttpClient getHttpClient() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                Logger.d("request=" + message);
//            }
//        });

//        HeaderInterceptor headerInterceptor = new HeaderInterceptor();


        //网络缓存路径文件
        // File httpCacheDirectory = new File(BaseApplication.getInstance().getExternalCacheDir(), "responses");
        //通过拦截器设置缓存，暂未实现
        //CacheInterceptor cacheInterceptor = new CacheInterceptor();


//     OkHttpClient.Builder builder   = new OkHttpClient.Builder()
//                //设置缓存
//                //      .cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024))
//                //log请求参数
//                .addInterceptor(interceptor)
//                .addInterceptor(headerInterceptor)
//                //网络请求缓存，未实现
////                        .addInterceptor(cacheInterceptor)
//                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        mNetProvider.configHttps(builder);
        builder.retryOnConnectionFailure(false);
        builder.connectTimeout(getConnectTimeout(), TimeUnit.MILLISECONDS);
        builder.readTimeout(getReadTimeout(), TimeUnit.MILLISECONDS);

        Interceptor[] interceptors = mNetProvider.getInterceptors();
        if (!ObjectUtils.isEmpty(interceptors)) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (mNetProvider.isLogEnable()) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        builder.retryOnConnectionFailure(mNetProvider.isRetryOnConnectionFailure());

        RequestHandler handler = mNetProvider.getHandler();
        if (handler != null) {
            builder.addInterceptor(new XInterceptor(handler));
        }




        return builder.build();
    }

    private long getReadTimeout() {
        long time = mNetProvider.getReadTimeoutMills();
        return time != 0 ? time : READ_TIME_OUT_MILLS;
    }

    private long getConnectTimeout() {
        long time = mNetProvider.getConnectTimeoutMills();
        return time != 0 ? time : CONNECT_TIME_OUT_MILLS;
    }
}
