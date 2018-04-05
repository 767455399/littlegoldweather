package com.littlegold.littlegoldweather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.littlegold.littlegoldweather.base.BaseActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {
    /**
     * 获取图片
     */
    private static final String requestImg = "http://guolin.tech/api/bing_pic";
    private String imagePath;
    private ImageView bgImageView;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(!TextUtils.isEmpty(String.valueOf(msg.obj))){
                Glide.with(MainActivity.this).load(msg.obj).placeholder(R.mipmap.ic_launcher).into(bgImageView);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        bgImageView = f(R.id.bgImageView);
    }

    @Override
    protected void loadData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(requestImg).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                imagePath = response.body().string();
                Message message = Message.obtain();
                message.obj = imagePath;
                handler.sendMessage(message);
            }
        });
    }


}
