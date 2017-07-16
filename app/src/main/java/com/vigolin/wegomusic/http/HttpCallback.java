package com.vigolin.wegomusic.http;

/**
 * Created by Administrator on 2017/7/16.
 */

public abstract class HttpCallback<T> {
    public abstract void onSuccess(T t);
    public abstract void onFail(Exception e);
    public void onFinish(){}
}
