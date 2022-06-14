package com.pppcar.zoombanner.network;

/**
 * Author  ： logan
 * Time    ： 2022/5/19 6:27 下午
 * Desc    ：
 */
public class HttpData <T> {

    /** 返回码 */
    private int returnCode;

    /** 数据 */
    private T data;

    private String returnMsg;


    public String getMessage(){
        return returnMsg;
    }

    public T getData() {
        return data;
    }

    /**
     * 是否请求成功
     */
    public boolean isRequestSucceed() {
        return returnCode == 000000;
    }

    /**
     * 是否 Token 失效
     */
    public boolean isTokenFailure() {
        return returnCode == 401;
    }
}