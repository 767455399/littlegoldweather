package com.littlegold.littlegoldweather.base;

import android.app.Application;
import android.content.Context;

import com.umeng.commonsdk.UMConfigure;

/**
 * Created by wangqing on 2018/4/7.
 */

public class BaseApplication extends Application {
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        UMConfigure.init(this,"5aca21a2f43e483abb000237","LittleGoldUmeng",UMConfigure.DEVICE_TYPE_PHONE,null);
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
