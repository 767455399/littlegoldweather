package com.littlegold.littlegoldweather.dialog;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by wangqing on 2018/4/11.
 */

public class CommonLoadingDialog {
    private static LoadingDialog dialog;

    public static void show(Fragment fragment){
        show(fragment.getActivity());
    }

    public static void show(Activity activity){
        hide();
        dialog=new LoadingDialog(activity);
        dialog.show();
    }

    public static void hide() {
        try{
            if(dialog!=null&&dialog.isShowing()){
                dialog.dismiss();
                dialog=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
