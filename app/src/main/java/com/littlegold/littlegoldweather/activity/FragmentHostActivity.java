package com.littlegold.littlegoldweather.activity;

import android.os.Bundle;

import com.littlegold.littlegoldweather.base.BaseActivity;
import com.littlegold.littlegoldweather.fragment.ProvinceFragment;

public class FragmentHostActivity extends BaseActivity {

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
}
