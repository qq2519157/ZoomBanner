package com.pppcar.zoombanner.network;

import androidx.annotation.NonNull;

import com.hjq.http.config.IRequestServer;
import com.hjq.http.model.BodyType;
import com.pppcar.zoombanner.Constant;

/**
 * Author  ： logan
 * Time    ： 2022/5/19 6:17 下午
 * Desc    ：
 */
public class RequestServer implements IRequestServer {

    @NonNull
    @Override
    public String getHost() {
        return Constant.HOST;
    }

    @NonNull
    @Override
    public BodyType getBodyType() {
        // 参数以 Json 格式提交（默认是表单）
        return BodyType.JSON;
    }
}
