package com.pppcar.zoombanner.network;

import androidx.annotation.NonNull;

import com.hjq.http.config.IHttpPostBodyStrategy;
import com.hjq.http.config.IRequestServer;
import com.hjq.http.model.RequestBodyType;
import com.pppcar.zoombanner.Constant;

public class RequestServer implements IRequestServer {

    @NonNull
    @Override
    public String getHost() {
        return Constant.HOST;
    }

    @NonNull
    @Override
    public IHttpPostBodyStrategy getBodyType() {
        return RequestBodyType.JSON;
    }
}
