package com.littlegold.littlegoldweather;

/**
 * Created by wangqing on 2018/4/6.
 */

public interface LittleGoldCallBack<T> {
    void onSuccess(T t);

    void onFail(int errorCode, String errorMsg);
}
