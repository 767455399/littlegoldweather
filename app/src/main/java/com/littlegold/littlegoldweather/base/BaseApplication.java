package com.littlegold.littlegoldweather.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by wangqing on 2018/4/7.
 */

public class BaseApplication extends Application {
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }

    public static Context getContext() {
        return applicationContext;
    }

    public static void setContext(Context context) {
        if (applicationContext == null) {
            applicationContext = context;
        }
    }
}
