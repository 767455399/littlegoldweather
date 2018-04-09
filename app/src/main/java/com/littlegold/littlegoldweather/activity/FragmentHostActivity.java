package com.littlegold.littlegoldweather.activity;

import android.os.Bundle;

import com.littlegold.littlegoldweather.base.BaseActivity;
import com.littlegold.littlegoldweather.fragment.ProvinceFragment;
import com.umeng.analytics.MobclickAgent;

public class FragmentHostActivity extends BaseActivity {
    private static final String TAG = "FragmentHostActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        replaceFragment(ProvinceFragment.newInstance());
    }

    @Override
    protected void loadData() {

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
}
