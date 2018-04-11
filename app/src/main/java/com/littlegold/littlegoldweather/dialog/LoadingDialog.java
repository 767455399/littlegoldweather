package com.littlegold.littlegoldweather.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.littlegold.littlegoldweather.R;


/**
 * Created by wangqing on 2018/4/11.
 */

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.Common_Dialog_Base_Center);
        setContentView(R.layout.dialog_loading);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
}
