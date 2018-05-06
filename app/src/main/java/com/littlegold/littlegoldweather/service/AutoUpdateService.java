package com.littlegold.littlegoldweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.littlegold.littlegoldweather.api.PicApi;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AutoUpdateService extends Service {
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updatePic();
        AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int hour=10*60*1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+hour;
        Intent i=new Intent(this,AutoUpdateService.class);
        PendingIntent pendingIntent=PendingIntent.getService(this,0,i,0);
        manager.cancel(pendingIntent);
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新图片
     */
    private void updatePic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PicApi.getImage().observeOn(AndroidSchedulers.mainThread()).observeOn(Schedulers.io()).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        if(!TextUtils.isEmpty(s)){
                            SharedPreferences.Editor editor= getSharedPreferences("pic",MODE_PRIVATE).edit();
                            editor.putString("picUrl",s);
                            editor.apply();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });

    }

    private void updateWeather() {
        Toast.makeText(this, "天气已更新", Toast.LENGTH_SHORT).show();
    }
}
