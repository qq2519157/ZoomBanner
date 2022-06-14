package com.pppcar.zoombanner.network;

import com.hjq.http.exception.HttpException;

/**
 * Author  ： logan
 * Time    ： 2022/5/19 6:29 下午
 * Desc    ：
 */
public class TokenException extends HttpException {
    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
