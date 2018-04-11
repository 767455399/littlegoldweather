package com.littlegold.littlegoldweather.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.umeng.message.PushAgent;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
        PushAgent.getInstance(getApplicationContext()).onAppStart();
    }

    protected abstract void initView();

    protected abstract void loadData();

    /**
     * 绑定view
     */
    protected <T extends View> T f(int id) {
        return (T) findViewById(id);
    }

    /**
     * fragment跳转不需要进栈
     */
    public void replaceFragment(Fragment fragment) {
        try {
//            getSupportFragmentManager().beginTransaction().replace(Window.ID_ANDROID_CONTENT,fragment).commit();
            getSupportFragmentManager().beginTransaction().replace(Window.ID_ANDROID_CONTENT, fragment, fragment.getClass().getName()).commit();
        } catch (Throwable e) {
        }
    }
}
