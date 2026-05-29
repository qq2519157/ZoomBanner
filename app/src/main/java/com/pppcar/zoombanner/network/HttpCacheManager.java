package com.pppcar.zoombanner.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hjq.gson.factory.GsonFactory;
import com.hjq.http.EasyLog;
import com.hjq.http.config.IHttpCacheStrategy;
import com.hjq.http.config.IRequestApi;
import com.hjq.http.request.HttpRequest;
import com.tencent.mmkv.MMKV;

import java.lang.reflect.Type;

import okhttp3.Response;

public class HttpCacheManager implements IHttpCacheStrategy {

    private volatile static MMKV sMmkv;
    private volatile static HttpCacheManager sInstance;

    public static HttpCacheManager getInstance() {
        if (sInstance == null) {
            synchronized (HttpCacheManager.class) {
                if (sInstance == null) {
                    sInstance = new HttpCacheManager();
                }
            }
        }
        return sInstance;
    }

    private HttpCacheManager() {}

    /**
     * 获取单例的 MMKV 实例
     */
    private static MMKV getMmkv() {
        if(sMmkv == null) {
            synchronized (HttpCacheManager.class) {
                if (sMmkv == null) {
                    sMmkv = MMKV.mmkvWithID("http_cache_id");
                }
            }
        }
        return sMmkv;
    }

    /**
     * 生成缓存的 key
     */
    private static String generateCacheKey(@NonNull HttpRequest<?> httpRequest) {
        IRequestApi requestApi = httpRequest.getRequestApi();
        return "用户 id" + "\n" + requestApi.getApi() + "\n" + GsonFactory.getSingletonGson().toJson(requestApi);
    }

    @Nullable
    @Override
    public Object readCache(@NonNull HttpRequest<?> httpRequest, @NonNull Type type, long cacheTime) throws Throwable {
        String cacheKey = generateCacheKey(httpRequest);
        String cacheValue = getMmkv().getString(cacheKey, null);
        if (cacheValue == null || "".equals(cacheValue) || "{}".equals(cacheValue)) {
            return null;
        }
        EasyLog.printLog(httpRequest, "----- readCache cacheKey -----");
        EasyLog.printJson(httpRequest, cacheKey);
        EasyLog.printLog(httpRequest, "----- readCache cacheValue -----");
        EasyLog.printJson(httpRequest, cacheValue);
        return GsonFactory.getSingletonGson().fromJson(cacheValue, type);
    }

    @Override
    public boolean writeCache(@NonNull HttpRequest<?> httpRequest, @NonNull Response response, @NonNull Object result) throws Throwable {
        String cacheKey = generateCacheKey(httpRequest);
        String cacheValue = GsonFactory.getSingletonGson().toJson(result);
        if (cacheValue == null || "".equals(cacheValue) || "{}".equals(cacheValue)) {
            return false;
        }
        EasyLog.printLog(httpRequest, "----- writeCache cacheKey -----");
        EasyLog.printJson(httpRequest, cacheKey);
        EasyLog.printLog(httpRequest, "----- writeCache cacheValue -----");
        EasyLog.printJson(httpRequest, cacheValue);
        return getMmkv().putString(cacheKey, cacheValue).commit();
    }

    @Override
    public boolean deleteCache(@NonNull HttpRequest<?> httpRequest) {
        String cacheKey = generateCacheKey(httpRequest);
        getMmkv().removeValueForKey(cacheKey);
        return true;
    }

    @Override
    public void clearCache() {
        getMmkv().clearMemoryCache();
        getMmkv().clearAll();
    }
}
