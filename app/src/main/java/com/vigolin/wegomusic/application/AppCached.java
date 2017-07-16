package com.vigolin.wegomusic.application;

import android.content.Context;
import android.support.v4.util.LongSparseArray;


import com.vigolin.wegomusic.activity.BaseActivity;
import com.vigolin.wegomusic.module.Music;
import com.vigolin.wegomusic.module.SongListInfo;
import com.vigolin.wegomusic.service.PlayService;
import com.vigolin.wegomusic.utils.PreferencesUtils;
import com.vigolin.wegomusic.utils.ScreenUtils;
import com.vigolin.wegomusic.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */

public class AppCached {
    private static final String TAG=AppCached.class.getSimpleName();
    private Context mContext;
    private PlayService mPlayService;
    private final List<Music> mMusicList=new ArrayList<>();
    private final List<SongListInfo> mSongListInfos=new ArrayList<>();
    private final List<BaseActivity> mActivityStack=new ArrayList<>();
    private final LongSparseArray<String> mDownloadList=new LongSparseArray<>();

    private static class AppCachedSingleHolder{
        public static AppCached singleTon=new AppCached();
    }

    public static AppCached getInstance(){
        return AppCachedSingleHolder.singleTon;
    }

    public static void init(Context context){
        getInstance().OnInit(context);
    }

    private void OnInit(Context context){
        this.mContext=context.getApplicationContext();
        ToastUtils.init(mContext);
        ScreenUtils.init(mContext);
        PreferencesUtils.init(mContext);
    }

    public static Context getContext(){
        return getInstance().mContext;
    }

    public static PlayService getPlayService(){
        return getInstance().mPlayService;
    }

    public void setPlayService(PlayService playService){
        this.mPlayService=playService;
    }

    public static List<Music> getMusicList(){
        return getInstance().mMusicList;
    }

    public static List<SongListInfo> getSongListInfos(){
        return getInstance().mSongListInfos;
    }

    public static void addToStack(BaseActivity baseActivity) {
        getInstance().mActivityStack.add(baseActivity);
    }

    public static void removeFromStack(BaseActivity baseActivity){
        getInstance().mActivityStack.remove(baseActivity);
    }

    public static void clearStack(){
        List<BaseActivity> list=getInstance().mActivityStack;
        for(int i=list.size()-1;i>0;i--){
            BaseActivity baseActivity=list.get(i);
            removeFromStack(baseActivity);
            if(!baseActivity.isFinishing()){
                baseActivity.finish();
            }
        }
    }

    public static LongSparseArray<String> getDownloadList(){
        return getInstance().mDownloadList;
    }
}
