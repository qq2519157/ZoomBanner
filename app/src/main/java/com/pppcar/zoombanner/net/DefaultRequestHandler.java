package com.pppcar.zoombanner.net;

import com.blankj.utilcode.util.LogUtils;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by TANG on 2018-03-14.
 */

public class DefaultRequestHandler implements RequestHandler {
    @Override
    public Request onBeforeRequest(Request request, Interceptor.Chain chain) {
        Headers headers = request.headers();
        StringBuffer buffer = new StringBuffer();
        buffer.append("url : ").append(request.url()).append("\n");
        buffer.append("method : ").append(request.method()).append("\n");
        buffer.append("Header : \n");
        for (int i = 0; i < headers.size(); i++) {
            String key = headers.name(i);
            buffer.append(key).append(" : ").append(headers.value(i)).append("\n");
        }


        RequestBody requestBody = request.body();
        if (requestBody instanceof FormBody) {
            buffer.append("---------------------------------\n");
            buffer.append("Body:\n");
            FormBody body = (FormBody) requestBody;
            for (int i = 0; i < body.size(); i++) {
                String key = body.name(i);
                String value = body.value(i);
                buffer.append(key).append(" : ").append(value).append("\n");
            }
        }
//        if (requestBody instanceof MultipartBody){
//            MultipartBody body= (MultipartBody) requestBody;
//            MultipartBody.Part part = body.part(0);
//            part.body()
//        }


        LogUtils.e("request\n" + buffer);


        return request;
    }



    @Override
    public void onAfterRequest(String result) {
        LogUtils.e("response=" + result);

    }
}
