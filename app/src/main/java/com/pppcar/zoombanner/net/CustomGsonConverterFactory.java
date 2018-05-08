package com.pppcar.zoombanner.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by TANG on 2018-03-13.
 */

public class CustomGsonConverterFactory extends Converter.Factory{

    private final Gson gson;
    private NetProvider mNetProvider;

    private CustomGsonConverterFactory(Gson gson, NetProvider provider) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
        mNetProvider = provider;
    }


    public static CustomGsonConverterFactory create(NetProvider provider) {

        return new CustomGsonConverterFactory(new Gson(), provider);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonResponseBodyConverter<>(adapter, mNetProvider);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonRequestBodyConverter<>(gson, adapter);
    }
}
