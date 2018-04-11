package com.littlegold.littlegoldweather.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * Created by wangqing on 2018/4/7.
 */

public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        UMConfigure.init(this,"5aca21a2f43e483abb000237","LittleGoldUmeng",UMConfigure.DEVICE_TYPE_PHONE,"1339801b735a6da98c46e20fed966ce4");
        registerUMPush();
    }

    private void registerUMPush() {
        PushAgent pushAgent=PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        pushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {
                //注册成功会返回device token
                Log.d(TAG, "onSuccess:******devicetoken******"+s);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
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
