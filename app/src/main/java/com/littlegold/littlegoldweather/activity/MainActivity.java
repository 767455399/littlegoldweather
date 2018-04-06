package com.littlegold.littlegoldweather.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.littlegold.littlegoldweather.R;
import com.littlegold.littlegoldweather.base.BaseActivity;
import com.littlegold.littlegoldweather.fragment.ProvinceFragment;

public class MainActivity extends BaseActivity {
    /**
     * 获取图片
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

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        load = f(R.id.load);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(ProvinceFragment.newInstance());
            }
        });
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
