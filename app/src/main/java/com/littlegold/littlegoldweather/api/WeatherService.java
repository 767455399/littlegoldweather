package com.littlegold.littlegoldweather.api;

import com.littlegold.littlegoldweather.model.InstantWeatherModel;
import com.littlegold.littlegoldweather.model.NextThreeDaysWeatherModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wangqing on 2018/4/8.
 */

public interface WeatherService {

    /**
     * 获取三天天气预报
     */
    @GET("forecast?key=6eb55f14b38c4ffb94a8806bb5b75328")
    Observable<NextThreeDaysWeatherModel> getNextThreeDayWeather(@Query("location")String cityCode);

    /**
     * 获取及时天气
     */
    @GET("now?key=6eb55f14b38c4ffb94a8806bb5b75328")
    Observable<InstantWeatherModel> getInstantWeather(@Query("location")String cityCode);
}
