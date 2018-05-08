package com.pppcar.zoombanner.net;


public class NetError extends Exception {
    public static final int ERROR_PARSE  = 0;   //数据解析异常
    public static final int ERROR_NO_CONNECT = 1;   //无连接异常
    public static final int AuthError = 2;   //用户验证异常
    public static final int NoDataError = 3;   //无数据返回异常
    public static final int BusinessError = 4;   //业务异常
    public static final int OtherError = 5;   //其他异常

    //连接超时
    public static final int SOCKET_TIME_OUT = 10001;
    //JSON解析异常
    public static final int MALFORMED_JSON = 10002;
    //网络连接错误
    public static final int CONNECT_ERROR = 10003;
    //未知错误
    public static final int UNKNOW = 10004;
    public static final int EMPTY_DATA = 10005;
    //服务器错误
    public static final int HTTP_ERROE = 10006;



    private int code = 0;
    private String msg;


    public NetError(int code, String msg) {
        super(msg);
        this.code = code;
    }


    public int getCode() {
        return code;
    }


}
