package com.vigolin.wegomusic.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.vigolin.wegomusic.module.Music;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/7/26.
 */

public class FileUtils {
    private static final String MP3=".mp3";
    private static final String LRC=".lrc";

    public static String getAppDir(){
        return Environment.getExternalStorageDirectory()+"/WeGoMusic";
    }

    public static String getMusicDir(){
        String musicDir=getAppDir()+"/Music/";
        return mkdirs(musicDir);
    }

    public static String getLrcDir(){
        String lrcDir=getAppDir()+"/Lyric/";
        return mkdirs(lrcDir);
    }

    public static String getAlbumDir(){
        String albumDir=getAppDir()+"/Album/";
        return mkdirs(albumDir);
    }

    public static String getLogDir(){
        String logDir=getAppDir()+"/Log/";
        return mkdirs(logDir);
    }

    public static String getSplashDir(Context context){
        String splashDir=context.getFilesDir()+"/splash/";
        return mkdirs(splashDir);
    }

    public static String getRelativeMusicDir(){
        String relativeMusicDir="WeGoMusic/music/";
        return mkdirs(relativeMusicDir);
    }

    /*
     *获取歌词路径
     * 先从下载文件夹找，若不存在，再从歌曲所在文件夹找
     * @return 若存在就返回路径，否则返回null
     */
    public static String getLrcFilePath(Music music){
        if(music==null)
            return null;

        String lrcFileName=getLrcDir()+getLrcFileName(music.getArtist(),music.getTitle());
        if(!exists(lrcFileName)){
            lrcFileName=music.getPath().replace(MP3,LRC);
            if(!exists(lrcFileName))
                lrcFileName=null;
        }

        return lrcFileName;
    }

    public static String getAlbumFilePath(Music music){
        if(music==null)
            return null;

        String albumFilePath=music.getCoverPath();
        if(TextUtils.isEmpty(albumFilePath) || !exists(albumFilePath)){
            albumFilePath=getAlbumDir()+getAlbumFileName(music.getArtist(),music.getTitle());
            if(!exists(albumFilePath))
                albumFilePath=null;
        }
        return albumFilePath;
    }

    //获取歌词文件名
    public static String getLrcFileName(String artist,String title){
        return getFileName(artist,title)+LRC;
    }

    public static String getAlbumFileName(String artist,String title){
        return getFileName(artist,title);
    }

    private static String getFileName(String artist,String title){
        artist=stringFilter(artist);
        title=stringFilter(title);
        return artist+"_"+title;
    }

    private static String mkdirs(String path){
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path;
    }

    private static String stringFilter(String str){
        if(str==null)
            return null;
        String regex="[\\/:*?\"<>|]";
        Matcher matcher= Pattern.compile(regex).matcher(str);
        return matcher.replaceAll("").trim();
    }

    private static boolean exists(String path){
        File file=new File(path);
        if(file.exists())
            return true;
        return false;
    }
}
