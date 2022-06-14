package com.pppcar.zoombanner.network.api;

import androidx.annotation.NonNull;

import com.hjq.http.config.IRequestApi;
import com.pppcar.zoombanner.Constant;

/**
 * Author  ： logan
 * Time    ： 2022/5/19 6:33 下午
 * Desc    ：
 */
public class MainApi implements IRequestApi {
    @NonNull
    @Override
    public String getApi() {
        return Constant.MAIN;
    }

}
