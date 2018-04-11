package com.littlegold.littlegoldweather.tool;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by wangqing on 2018/4/10.
 */

public class AppInfo {
    /**
     * 获取版本号：
     */
    public static int getAppVersionCode(Context context){
        int versionCode=1;
        PackageManager packageManager=context.getPackageManager();
        try {
            PackageInfo packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
