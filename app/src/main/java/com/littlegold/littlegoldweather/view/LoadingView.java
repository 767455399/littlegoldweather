package com.littlegold.littlegoldweather.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.littlegold.littlegoldweather.R;


/**
 * Created by wangqing on 2018/4/11.
 */

public class LoadingView extends View {
    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.animation_loading);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((AnimationDrawable) getBackground()).start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((AnimationDrawable) getBackground()).stop();
    }
}
