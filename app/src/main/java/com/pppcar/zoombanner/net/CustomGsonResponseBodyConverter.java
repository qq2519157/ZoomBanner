package com.pppcar.zoombanner.net;

import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by TANG on 2018-03-13.
 */

public class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final TypeAdapter<T> adapter;
    private final NetProvider mNetProvider;

    CustomGsonResponseBodyConverter(TypeAdapter<T> adapter, NetProvider provider) {
        this.adapter = adapter;
        this.mNetProvider=provider;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String respose=value.string();
            if (mNetProvider.getHandler()!=null){
                mNetProvider.getHandler().onAfterRequest(respose);
            }
            return adapter.fromJson(respose);
//            return adapter.fromJson(value.charStream());
        } finally {
            value.close();
        }
    }
}
