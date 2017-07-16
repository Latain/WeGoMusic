package com.vigolin.wegomusic.http;

import android.os.Handler;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.vigolin.wegomusic.module.Splash;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by Administrator on 2017/7/16.
 */

public abstract class JsonCallback<T> extends Callback<T> {
    private Class<T> mClass;
    private Gson mGson=new Gson();

    public JsonCallback(Class<T> mClass){
        this.mClass=mClass;
    }

    @Override
    public T parseNetworkResponse(Response response) throws IOException {
        try{
            String requestResult=response.body().string();
            return mGson.fromJson(requestResult,mClass);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
