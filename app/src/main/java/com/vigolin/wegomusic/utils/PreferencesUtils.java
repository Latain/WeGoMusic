package com.vigolin.wegomusic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2017/7/15.
 * Using sharedPreferences to save the current music data
 */

public class PreferencesUtils {
    private static final String MUSIC_ID="music_id";
    private static final String PLAY_MODE="play_mode";
    private static final String SPLASH_URL="splash_url";

    private static Context mContext;

    public static void init(Context context){
        mContext=context.getApplicationContext();
    }

    public static void saveCurrentSongId(long musicId){
        saveLong(MUSIC_ID,musicId);
    }

    public static long getCurrentSongId(){
        return getLong(MUSIC_ID,-1);
    }

    public static void savePlayMode(int mode){
        saveInt(PLAY_MODE,mode);
    }

    public static int getPlayMode(){
        return getInt(PLAY_MODE,0);
    }

    public static void saveSplashUrl(String splashUrl){
        saveString(SPLASH_URL,splashUrl);
    }

    public static String getSplashUrl(){
        return getString(SPLASH_URL,"");
    }

    private static SharedPreferences getPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    private static void saveString(String key,String value){
        getPreferences().edit().putString(key,value).apply();
    }

    private static String getString(String key,String defValue){
        return getPreferences().getString(key,defValue);
    }

    private static void saveLong(String key,long value){
        getPreferences().edit().putLong(key,value).apply();
    }

    private static long getLong(String key,long defValue){
        return getPreferences().getLong(key,defValue);
    }

    private static void saveInt(String key,int value){
        getPreferences().edit().putInt(key,value).apply();
    }

    private static int getInt(String key,int defValue){
        return getPreferences().getInt(key,defValue);
    }

    private static void saveBoolean(String key,boolean value){
        getPreferences().edit().putBoolean(key,value).apply();
    }

    private static boolean getBoolean(String key,boolean defValue){
        return getPreferences().getBoolean(key,defValue);
    }
}
