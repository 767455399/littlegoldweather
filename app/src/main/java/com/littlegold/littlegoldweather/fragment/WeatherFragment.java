package com.littlegold.littlegoldweather.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.littlegold.littlegoldweather.R;
import com.littlegold.littlegoldweather.api.WeatherApi;
import com.littlegold.littlegoldweather.base.BaseFragment;
import com.littlegold.littlegoldweather.model.InstantWeatherModel;
import com.littlegold.littlegoldweather.model.NextThreeDaysWeatherModel;
import com.littlegold.littlegoldweather.tool.ServerConfig;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by wangqing on 2018/4/5.
 */

public class WeatherFragment extends BaseFragment {
    //当天及时天气预报
    private String path = ServerConfig.WEATHER_URL + "now?key=6eb55f14b38c4ffb94a8806bb5b75328&location=";
    //    未来三天天气预报
    private String nextThreeDaysPath = ServerConfig.WEATHER_URL + "forecast?key=6eb55f14b38c4ffb94a8806bb5b75328&location=";
    private static String CITY_CODE;
    private InstantWeatherModel instantWeatherModel;
    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    public static final int ITEM_HEAD = 0;
    public static final int ITEM_RECORD = 1;
    public static final int ITEM_NO_RECORD = 2;
    public static final int ITEM_FOOTER = 3;
    private Retrofit retrofit;
    private OkHttpClient.Builder builder;
    public List<NextThreeDaysWeatherModel.HeWeather6Bean.DailyForecastBean> threeDayWeatherList = new ArrayList<>();
    public List<InstantWeatherModel.HeWeather6Bean> instantList = new ArrayList<>();
    private int weatherListLength;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    public static WeatherFragment newInstance(String cityCode) {
        Bundle args = new Bundle();
        args.putString(CITY_CODE, cityCode);
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        gridLayoutManager.setSpanSizeLookup(new AutoSpanSizeLookUp());
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new WeatherAdapter();
        recyclerView.setAdapter(adapter);

//
    }

    class WeatherAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case ITEM_HEAD:
                    return new HeadViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.fragment_weather_header, parent, false));
                case ITEM_RECORD:
                    return new RecordViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.fragment_weather_record, parent, false));
                case ITEM_NO_RECORD:
                    return new NoRecordViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.fragment_weather_no_record, parent, false));
                case ITEM_FOOTER:
                    return new FooterViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.fragment_weather_footer, parent, false));
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof RecordViewHolder) {
                final RecordViewHolder recordViewHolder = (RecordViewHolder) holder;
                int positions=position-1;
                if (2 == positions) {
                    recordViewHolder.dayTextView.setText("后天");
                } else if (1 == positions) {
                    recordViewHolder.dayTextView.setText("明天");
                } else {
                    recordViewHolder.dayTextView.setText("今天");
                }
                recordViewHolder.airQualityTextView.setText(threeDayWeatherList.get(positions).cond_txt_d);
                recordViewHolder.temperatureTextView.setText(threeDayWeatherList.get(positions).tmp_min + "/" + threeDayWeatherList.get(positions).tmp_max + "℃");
//                recordViewHolder.airQualityTextView.setCompoundDrawablePadding(2);
//                recordViewHolder.airQualityTextView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_launcher), null, null, null);
            } else if (holder instanceof HeadViewHolder) {
                final HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                if (instantWeatherModel.HeWeather6.size() > 0) {
                    InstantWeatherModel.HeWeather6Bean.NowBean nowBean = instantWeatherModel.HeWeather6.get(0).now;
                    headViewHolder.addressTextView.setText(instantWeatherModel.HeWeather6.get(0).basic.parent_city + "~" + instantWeatherModel.HeWeather6.get(0).basic.location);
                    headViewHolder.temperatureTextView.setText(nowBean.tmp);
                    headViewHolder.companyTextView.setText("℃");
                    headViewHolder.weatherTextView.setText(nowBean.cond_txt);
                    headViewHolder.windDirectionTextView.setText(nowBean.wind_dir);
                    headViewHolder.windPowerTextView.setText(nowBean.wind_sc);
                    headViewHolder.relativeHumidityTextView.setText(nowBean.hum);
                    headViewHolder.SomatosensoryTemperatureTextView.setText(nowBean.fl);
                }
            } else if (holder instanceof NoRecordViewHolder) {

            } else if (holder instanceof FooterViewHolder) {

            }

        }

        @Override
        public int getItemViewType(int position) {
            if (0 == position && weatherListLength > 0) {
                return ITEM_HEAD;
            } else if (0 == position && weatherListLength == 0) {
                return ITEM_NO_RECORD;
            } else if (position == weatherListLength + 1) {
                return ITEM_FOOTER;
            } else {
                return ITEM_RECORD;
            }
        }

        @Override
        public int getItemCount() {
            return weatherListLength == 0 ? 1 : weatherListLength + 1;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        WeatherApi.getInstantWeather(getArguments().getString(CITY_CODE)).flatMap(new Function<InstantWeatherModel, Observable<NextThreeDaysWeatherModel>>() {
            @Override
            public Observable<NextThreeDaysWeatherModel> apply(InstantWeatherModel instantWeatherModels) throws Exception {
                instantWeatherModel = instantWeatherModels;
                return WeatherApi.getNextThreeDayWeather(getArguments().getString(CITY_CODE));
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<NextThreeDaysWeatherModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(NextThreeDaysWeatherModel nextThreeDaysWeatherModel) {
                weatherListLength = threeDayWeatherList.size() + instantList.size();
                weatherListLength=3;
                Toast.makeText(getActivity(), weatherListLength+"******"+threeDayWeatherList.size()+"****"+instantList.size(), Toast.LENGTH_SHORT).show();
                threeDayWeatherList = nextThreeDaysWeatherModel.HeWeather6.get(0).daily_forecast;
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        private TextView addressTextView, temperatureTextView, weatherTextView, companyTextView,
                windDirectionTextView, windPowerTextView, relativeHumidityTextView, SomatosensoryTemperatureTextView;

        public HeadViewHolder(View itemView) {
            super(itemView);
            addressTextView = (TextView) itemView.findViewById(R.id.addressTextView);
            temperatureTextView = (TextView) itemView.findViewById(R.id.temperatureTextView);
            weatherTextView = (TextView) itemView.findViewById(R.id.weatherTextView);
            windDirectionTextView = (TextView) itemView.findViewById(R.id.windDirectionTextView);
            windPowerTextView = (TextView) itemView.findViewById(R.id.windPowerTextView);
            relativeHumidityTextView = (TextView) itemView.findViewById(R.id.relativeHumidityTextView);
            SomatosensoryTemperatureTextView = (TextView) itemView.findViewById(R.id.SomatosensoryTemperatureTextView);
            addressTextView = (TextView) itemView.findViewById(R.id.addressTextView);
            companyTextView = (TextView) itemView.findViewById(R.id.companyTextView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        private TextView footerTextView;

        public FooterViewHolder(View itemView) {
            super(itemView);
            footerTextView = (TextView) itemView.findViewById(R.id.footerTextView);
        }
    }

    class NoRecordViewHolder extends RecyclerView.ViewHolder {
        private TextView noDataTextView;

        public NoRecordViewHolder(View itemView) {
            super(itemView);
            noDataTextView = (TextView) itemView.findViewById(R.id.noDataTextView);
        }
    }

    class RecordViewHolder extends RecyclerView.ViewHolder {
        private TextView dayTextView, airQualityTextView, temperatureTextView;

        public RecordViewHolder(View itemView) {
            super(itemView);
            dayTextView = (TextView) itemView.findViewById(R.id.dayTextView);
            airQualityTextView = (TextView) itemView.findViewById(R.id.airQualityTextView);
            temperatureTextView = (TextView) itemView.findViewById(R.id.temperatureTextView);
        }
    }

    public class AutoSpanSizeLookUp extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            switch (adapter.getItemViewType(position)) {
                case ITEM_HEAD:
                    return 1;
                case ITEM_RECORD:
                    return 1;
                case ITEM_FOOTER:
                    return 1;
                case ITEM_NO_RECORD:
                    return 1;
                default:
                    return 1;
            }
        }
    }

//    private void loadData() {
//        String cityCode = getArguments().getString("cityCode");
//        if (TextUtils.isEmpty(cityCode)) {
//            return;
//        }
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(path + cityCode)
//                .build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Toast.makeText(getContext(), "获取天气失败", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String weatherData = response.body().string();
//                instantWeatherModel = new Gson().fromJson(weatherData, InstantWeatherModel.class);
//                if (!TextUtils.isEmpty(weatherData)) {
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            refreshUI();
//                        }
//                    });
//                }
//            }
//        });
//
//    }


    private void refreshUI() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                InstantWeatherModel.HeWeather6Bean.NowBean nowBean = instantWeatherModel.HeWeather6.get(0).now;
//                addressTextView.setText(instantWeatherModel.HeWeather6.get(0).basic.parent_city + "~" + instantWeatherModel.HeWeather6.get(0).basic.location);
//                temperatureTextView.setText(nowBean.tmp);
//                companyTextView.setText("℃");
//                weatherTextView.setText(nowBean.cond_txt);
//                windDirectionTextView.setText(nowBean.wind_dir);
//                windPowerTextView.setText(nowBean.wind_sc);
//                relativeHumidityTextView.setText(nowBean.hum);
//                SomatosensoryTemperatureTextView.setText(nowBean.fl);
            }
        });
    }
}
