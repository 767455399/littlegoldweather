package com.littlegold.littlegoldweather.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by wangqing on 2018/4/10.
 */

public class NetWork {

    public static boolean isWifi(Context  context){
        ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(null==cm){
            return false;
        }
        NetworkInfo info=cm.getActiveNetworkInfo();
        if(null!=info){
            if(info.getType()==ConnectivityManager.TYPE_WIFI){
                return true;
            }
        }
        return false;
    }

}
