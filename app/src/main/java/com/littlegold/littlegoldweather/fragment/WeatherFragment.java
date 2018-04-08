package com.littlegold.littlegoldweather.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.littlegold.littlegoldweather.R;
import com.littlegold.littlegoldweather.base.BaseFragment;
import com.littlegold.littlegoldweather.model.InstantWeatherModel;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangqing on 2018/4/5.
 */

public class WeatherFragment extends BaseFragment {
    private String path = "https://free-api.heweather.com/s6/weather/now?key=6eb55f14b38c4ffb94a8806bb5b75328&location=";
    private TextView addressTextView, temperatureTextView, weatherTextView,
            windDirectionTextView, windPowerTextView, relativeHumidityTextView, SomatosensoryTemperatureTextView;
    private InstantWeatherModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    public static WeatherFragment newInstance(String cityCode) {
        Bundle args = new Bundle();
        args.putString("cityCode", cityCode);
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addressTextView = (TextView) view.findViewById(R.id.addressTextView);
        temperatureTextView = (TextView) view.findViewById(R.id.temperatureTextView);
        weatherTextView = (TextView) view.findViewById(R.id.weatherTextView);
        windDirectionTextView = (TextView) view.findViewById(R.id.windDirectionTextView);
        windPowerTextView = (TextView) view.findViewById(R.id.windPowerTextView);
        relativeHumidityTextView = (TextView) view.findViewById(R.id.relativeHumidityTextView);
        SomatosensoryTemperatureTextView = (TextView) view.findViewById(R.id.SomatosensoryTemperatureTextView);
        addressTextView = (TextView) view.findViewById(R.id.addressTextView);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        String cityCode = getArguments().getString("cityCode");
        if (TextUtils.isEmpty(cityCode)) {
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(path + cityCode)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getContext(), "获取天气失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String weatherData = response.body().string();
                model = new Gson().fromJson(weatherData, InstantWeatherModel.class);
                if (!TextUtils.isEmpty(weatherData)) {
                    refreshUI();
                }

            }
        });

    }

    private void refreshUI() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                InstantWeatherModel.HeWeather6Bean.NowBean nowBean = model.HeWeather6.get(0).now;
                addressTextView.setText(model.HeWeather6.get(0).basic.parent_city + "~" + model.HeWeather6.get(0).basic.location);
                temperatureTextView.setText(nowBean.tmp+"℃");
                weatherTextView.setText(nowBean.cond_txt);
                windDirectionTextView.setText(nowBean.wind_dir);
                windPowerTextView.setText(nowBean.wind_sc);
                relativeHumidityTextView.setText(nowBean.hum);
                SomatosensoryTemperatureTextView.setText(nowBean.fl);
            }
        });
    }
}
