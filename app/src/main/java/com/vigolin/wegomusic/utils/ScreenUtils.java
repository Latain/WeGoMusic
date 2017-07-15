package com.vigolin.wegomusic.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/7/15.
 */

public class ScreenUtils {
    private static Context mContext;

    public static void init(Context context){
        mContext=context.getApplicationContext();
    }

    public static int getSystemWidth(){
        WindowManager mWindowManager=(WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        return mWindowManager.getDefaultDisplay().getWidth();
    }

    //获取状态栏的高度
    public static int getSystemBarHeight(){
        int result=0;
        int resId=mContext.getResources().getIdentifier("status_bar_height","dimen","android");
        if(resId>0)
            result=mContext.getResources().getDimensionPixelSize(resId);
        return result;
    }

    public static int px2dp(int pxValue){
        float scale=mContext.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }

    public static int dp2px(int dpValue){
        float scale=mContext.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    public static int sp2px(int spValue){
        float fontScale=mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue*fontScale+0.5f);
    }
}
