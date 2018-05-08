package com.pppcar.zoombanner.net;

/**
 * Created by TANG on 2018-03-15.
 */

public class ApiException extends ApiError {

    private int mCode;
    private  String mMessage;

    public ApiException(int code, String message) {
        super(code, message);
        this.mMessage=message;
    }


    @Override
    public String getMessage() {
        return mMessage;
    }

    public int getCode() {
        return mCode;
    }
}
