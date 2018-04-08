package com.littlegold.littlegoldweather.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangqing on 2018/4/8.
 */

public class RetrofitClient {
    public static Retrofit.Builder getRetrofitBuilder(String baseUrl) {
        return new Retrofit.Builder()
                .client(HttpClient.INSTANCE.getOkHttpClient())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
    }
}
