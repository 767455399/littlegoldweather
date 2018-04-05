package com.littlegold.littlegoldweather.base;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

/**
 * Created by wangqing on 2018/4/5.
 */

public class BaseFragment extends Fragment {

    /**
     * fragment跳转，需要进栈
     */
    public void replaceFragmentNeedToBack(Fragment fragment){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(Window.ID_ANDROID_CONTENT,fragment,fragment.getClass().getName());
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * fragment跳转，不需要进栈
     */
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(Window.ID_ANDROID_CONTENT,fragment,fragment.getClass().getName());
        fragmentTransaction.commitAllowingStateLoss();
    }
}
