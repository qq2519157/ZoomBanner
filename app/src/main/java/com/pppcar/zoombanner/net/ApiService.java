package com.pppcar.zoombanner.net;

import com.pppcar.zoombanner.Constant;
import com.pppcar.zoombanner.entity.BannerBean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface ApiService {
    /**
     *  Banner
     */
    @GET(Constant.BANNER)
    Observable<Response<List<BannerBean>>> getBanner();
}
