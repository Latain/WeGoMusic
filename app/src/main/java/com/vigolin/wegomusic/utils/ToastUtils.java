package com.vigolin.wegomusic.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/7/15.
 */

public class ToastUtils {
    private static Context mContext;
    private static Toast mToast;

    public static void init(Context context){
        mContext=context.getApplicationContext();
    }

    public static void show(int resId){
        show(mContext.getString(resId));
    }

    public static void show(String text){
        if(mToast==null){
            mToast=Toast.makeText(mContext,text,Toast.LENGTH_SHORT);
        }else{
            mToast.setText(text);
        }
        mToast.show();
    }
}
