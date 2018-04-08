package com.littlegold.littlegoldweather.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlegold.littlegoldweather.R;


/**
 * Created by wangqing on 2018/4/7.
 */

public class SureDialog extends Dialog {
    public SureDialog(@NonNull Context context, final Listener listener) {
        super(context, R.style.WqDialogStyle);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sure, null);
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        contentTextView.setText("您确定要添加该城市天气预报？");
        TextView cancleTextView = (TextView) view.findViewById(R.id.cancleTextView);
        TextView sureTextView = (TextView) view.findViewById(R.id.sureTextView);
        cancleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        sureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.sure();
                dismiss();
            }
        });
        //点击dialog外部是否消失
        setCanceledOnTouchOutside(true);
        setContentView(view);
        /**
         * 该方法必须在setContentView方法之后执行。
         */
        getWindow().setLayout((ViewGroup.LayoutParams.WRAP_CONTENT), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public interface Listener {
        void sure();
    }
}
