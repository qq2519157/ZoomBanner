package com.pppcar.zoombanner.network;

import androidx.annotation.NonNull;

import com.hjq.http.exception.HttpException;

/**
 * Author  ： logan
 * Time    ： 2022/5/19 6:29 下午
 * Desc    ：
 */
public class ResultException extends HttpException {

    private final HttpData<?> mData;

    public ResultException(String message, HttpData<?> data) {
        super(message);
        mData = data;
    }

    public ResultException(String message, Throwable cause, HttpData<?> data) {
        super(message, cause);
        mData = data;
    }

    @NonNull
    public HttpData<?> getHttpData() {
        return mData;
    }
}