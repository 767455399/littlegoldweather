package com.littlegold.littlegoldweather.api;

import io.reactivex.Observable;

import retrofit2.http.GET;

/**
 * Created by Administrator on 2018/5/5.
 */

public interface PicService {
    /**
     * 获取最新的照片
     */
    @GET("bing_pic")
    Observable<String>getPic();


}
