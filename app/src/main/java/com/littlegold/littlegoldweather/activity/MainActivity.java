package com.littlegold.littlegoldweather.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.littlegold.littlegoldweather.R;
import com.littlegold.littlegoldweather.base.BaseActivity;
import com.littlegold.littlegoldweather.fragment.MainFragment;
import com.umeng.analytics.MobclickAgent;


public class MainActivity extends BaseActivity {
    /**
     * 获取图片
     * 和风天气数据
     * 用户ID: HE1804051912321516
     * 认证key：6eb55f14b38c4ffb94a8806bb5b75328
     * https://free-api.heweather.com/s6/weather/now?location=CN101190401&key=6eb55f14b38c4ffb94a8806bb5b75328
     */

    private static final String requestImg = "http://guolin.tech/api/bing_pic";
    private String imagePath;
    private TextView load;

//    @SuppressLint("HandlerLeak")
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (!TextUtils.isEmpty(String.valueOf(msg.obj))) {
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(MainFragment.newInstance());

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        load = f(R.id.load);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                replaceFragment(MainFragment.newInstance());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void loadData() {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        final Request request = new Request.Builder().url(requestImg).build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                imagePath = response.body().string();
//                Message message = Message.obtain();
//                message.obj = imagePath;
//                handler.sendMessage(message);
//            }
//        });
    }


}
