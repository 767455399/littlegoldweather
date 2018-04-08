package com.littlegold.littlegoldweather.model;

import java.util.List;

/**
 * Created by wangqing on 2018/4/7.
 */

public class InstantWeatherModel {

    public List<HeWeather6Bean> HeWeather6;

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101190401","location":"苏州","parent_city":"苏州","admin_area":"江苏","cnty":"中国","lat":"31.29937935","lon":"120.61958313","tz":"+8.00"}
         * update : {"loc":"2018-04-07 10:47","utc":"2018-04-07 02:47"}
         * status : ok
         * now : {"cloud":"2","cond_code":"100","cond_txt":"晴","fl":"-15","hum":"19","pcpn":"0.0","pres":"1028","tmp":"11","vis":"20","wind_deg":"319","wind_dir":"西北风","wind_sc":"5","wind_spd":"38"}
         */

        public BasicBean basic;
        public UpdateBean update;
        public String status;
        public NowBean now;

        public static class BasicBean {
            /**
             * cid : CN101190401
             * location : 苏州
             * parent_city : 苏州
             * admin_area : 江苏
             * cnty : 中国
             * lat : 31.29937935
             * lon : 120.61958313
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
             * loc : 2018-04-07 10:47
             * utc : 2018-04-07 02:47
             */

            public String loc;
            public String utc;
        }

        public static class NowBean {
            /**
             * cloud : 2
             * cond_code : 100
             * cond_txt : 晴
             * fl : -15
             * hum : 19
             * pcpn : 0.0
             * pres : 1028
             * tmp : 11
             * vis : 20
             * wind_deg : 319
             * wind_dir : 西北风
             * wind_sc : 5
             * wind_spd : 38
             */

            public String cloud;
            public String cond_code;
            public String cond_txt;
            public String fl;
            public String hum;
            public String pcpn;
            public String pres;
            public String tmp;
            public String vis;
            public String wind_deg;
            public String wind_dir;
            public String wind_sc;
            public String wind_spd;
        }
    }
}
