package com.pppcar.zoombanner.net;

/**
 * Created by TANG on 2018-03-15.
 */

public class ApiError extends Exception {
    private int mCode;

    public ApiError(int code, String message) {
        super(message);
        this.mCode = code;
    }

    public int getCode() {
        return mCode;
    }
}
