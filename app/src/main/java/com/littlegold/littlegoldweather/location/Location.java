package com.littlegold.littlegoldweather.location;

import android.content.Context;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by Administrator on 2018/5/19.
 */

public class Location {
    private Context mContext;
    private AMapLocation aMapLocations;
    public AMapLocationClient aMapLocationClient = null;
    public AMapLocationListener aMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (null != aMapLocation) {
                if (0 == aMapLocation.getErrorCode()) {
                    aMapLocations=aMapLocation;
                    Toast.makeText(mContext, "位置信息"+aMapLocation.getLatitude()+"，"+aMapLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public Location(Context context) {
        mContext = context;
        getLocation();
    }

    public void getLocation() {
        aMapLocationClient = new AMapLocationClient(mContext);
        aMapLocationClient.setLocationListener(aMapLocationListener);
        aMapLocationClient.startLocation();
    }

    public AMapLocation getLocationData(){
        return aMapLocations;
    }
}
