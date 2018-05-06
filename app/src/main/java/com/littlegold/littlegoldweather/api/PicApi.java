package com.littlegold.littlegoldweather.api;

import com.littlegold.littlegoldweather.http.RetrofitClient;
import com.littlegold.littlegoldweather.tool.ServerConfig;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/5/5.
 */

public class PicApi {
    public static Observable<String> getImage(){
        return RetrofitClient.getRetrofitBuilder(ServerConfig.REQUEST_IMG).build()
                .create(PicService.class).getPic();
    }
}
