package com.littlegold.littlegoldweather.model;

import java.util.List;

/**
 * Created by wangqing on 2018/4/8.
 */

public class NextThreeDaysWeatherModel {

    public List<HeWeather6Bean> HeWeather6;

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101010100","location":"北京","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.90498734","lon":"116.4052887","tz":"+8.00"}
         * update : {"loc":"2018-04-08 10:47","utc":"2018-04-08 02:47"}
         * status : ok
         * daily_forecast : [{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2018-04-08","hum":"32","mr":"01:13","ms":"11:07","pcpn":"0.0","pop":"0","pres":"1012","sr":"05:48","ss":"18:45","tmp_max":"18","tmp_min":"5","uv_index":"5","vis":"20","wind_deg":"183","wind_dir":"南风","wind_sc":"1-2","wind_spd":"3"},{"cond_code_d":"100","cond_code_n":"101","cond_txt_d":"晴","cond_txt_n":"多云","date":"2018-04-09","hum":"29","mr":"01:59","ms":"11:59","pcpn":"0.0","pop":"0","pres":"1011","sr":"05:46","ss":"18:46","tmp_max":"20","tmp_min":"7","uv_index":"6","vis":"20","wind_deg":"145","wind_dir":"东南风","wind_sc":"1-2","wind_spd":"5"},{"cond_code_d":"101","cond_code_n":"100","cond_txt_d":"多云","cond_txt_n":"晴","date":"2018-04-10","hum":"30","mr":"02:40","ms":"12:53","pcpn":"0.0","pop":"0","pres":"1002","sr":"05:45","ss":"18:47","tmp_max":"24","tmp_min":"9","uv_index":"5","vis":"20","wind_deg":"309","wind_dir":"西北风","wind_sc":"4-5","wind_spd":"33"}]
         */

        public BasicBean basic;
        public UpdateBean update;
        public String status;
        public List<DailyForecastBean> daily_forecast;

        public static class BasicBean {
            /**
             * cid : CN101010100
             * location : 北京
             * parent_city : 北京
             * admin_area : 北京
             * cnty : 中国
             * lat : 39.90498734
             * lon : 116.4052887
             * tz : +8.00
             */

            public String cid;
            public String location;
            public String parent_city;
            public String admin_area;
            public String cnty;
            public String lat;
            public String lon;
            public String tz;
        }

        public static class UpdateBean {
            /**
             * loc : 2018-04-08 10:47
             * utc : 2018-04-08 02:47
             */

            public String loc;
            public String utc;
        }

        public static class DailyForecastBean {
            /**
             * cond_code_d : 100
             * cond_code_n : 100
             * cond_txt_d : 晴
             * cond_txt_n : 晴
             * date : 2018-04-08
             * hum : 32
             * mr : 01:13
             * ms : 11:07
             * pcpn : 0.0
             * pop : 0
             * pres : 1012
             * sr : 05:48
             * ss : 18:45
             * tmp_max : 18
             * tmp_min : 5
             * uv_index : 5
             * vis : 20
             * wind_deg : 183
             * wind_dir : 南风
             * wind_sc : 1-2
             * wind_spd : 3
             */

            public String cond_code_d;
            public String cond_code_n;
            public String cond_txt_d;
            public String cond_txt_n;
            public String date;
            public String hum;
            public String mr;
            public String ms;
            public String pcpn;
            public String pop;
            public String pres;
            public String sr;
            public String ss;
            public String tmp_max;
            public String tmp_min;
            public String uv_index;
            public String vis;
            public String wind_deg;
            public String wind_dir;
            public String wind_sc;
            public String wind_spd;
        }
    }
}
