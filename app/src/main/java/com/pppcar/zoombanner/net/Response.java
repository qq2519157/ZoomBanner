package com.pppcar.zoombanner.net;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TANG on 2018-03-14.
 */

public class Response<T> implements IResult {
    @SerializedName("returnCode")
    private String mCode;
    @SerializedName("returnMsg")
    private String mMsg;
    @SerializedName("data")
    private T mData;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "mCode=" + mCode +
                ", mMsg='" + mMsg + '\'' +
                ", mData=" + mData +
                '}';
    }

    @Override
    public boolean isNull() {
        return mData == null;
    }

    @Override
    public boolean isSuccess() {
        return mCode.equals("000000");
    }

}
