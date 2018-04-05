package com.littlegold.littlegoldweather.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
    }

    protected abstract void initView();

    protected abstract void loadData();

    /**
     * 绑定view
     */
    protected <T extends View> T f(int id) {
        return (T) findViewById(id);
    }
}
