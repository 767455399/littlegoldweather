package com.littlegold.littlegoldweather.http;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by wangqing on 2018/4/8.
 */

public enum  HttpClient {

    INSTANCE;
    private OkHttpClient client;
//    private PersistentCookieStore persistentCookieStore;

    HttpClient() {
        Log.e("HttpClient", "enumInit");
//        persistentCookieStore = new PersistentCookieStore(BaseApplication.getContext());
//        String userAgentString = UserCache.getUserAgent();
//        String appVersion = AppInfo.getVersionName(BaseApplication.getContext());
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);
//                .cookieJar(new CustomCookieJar(new CookieManager(persistentCookieStore, CookiePolicy.ACCEPT_ALL)));
//                .addInterceptor(new UserAgentInterceptor(userAgentString))
//                .addInterceptor(new AppVersionInterceptor(appVersion));
//        if (BaseBuildConfig.isEnableLog()) {
//            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
//        }
        client = builder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return client;
    }

//    public void clearCookie() {
//        persistentCookieStore.removeAll();
//    }

}
