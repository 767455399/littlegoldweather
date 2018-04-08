package com.littlegold.littlegoldweather.api;

import com.littlegold.littlegoldweather.http.RetrofitClient;
import com.littlegold.littlegoldweather.model.InstantWeatherModel;
import com.littlegold.littlegoldweather.model.NextThreeDaysWeatherModel;
import com.littlegold.littlegoldweather.tool.ServerConfig;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by wangqing on 2018/4/8.
 */

public class WeatherApi {
    public static Observable<NextThreeDaysWeatherModel>getNextThreeDayWeather(String cityCode){
        Retrofit retrofit= RetrofitClient.getRetrofitBuilder(ServerConfig.WEATHER_URL).build();
        return retrofit.create(WeatherService.class).getNextThreeDayWeather(cityCode);
    }

    public static Observable<InstantWeatherModel>getInstantWeather(String cityCode){
        return RetrofitClient.getRetrofitBuilder(ServerConfig.WEATHER_URL).build()
                .create(WeatherService.class).getInstantWeather(cityCode);
    }
}
