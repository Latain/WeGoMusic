package com.vigolin.wegomusic.http;

import android.graphics.Bitmap;

import com.squareup.okhttp.Request;
import com.vigolin.wegomusic.module.ArtistInfo;
import com.vigolin.wegomusic.module.DownloadInfo;
import com.vigolin.wegomusic.module.Lrc;
import com.vigolin.wegomusic.module.OnlineMusic;
import com.vigolin.wegomusic.module.SearchMusic;
import com.vigolin.wegomusic.module.Splash;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

/**
 * Created by Administrator on 2017/7/16.
 */

public class HttpClient {
    private static final String SPLASH_URL="http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
    private static final String BASE_URL="http://tingapi.ting.baidu.com/v1/restserver/ting";
    private static final String METHOD_GET_MUSIC_LIST="baidu.ting.billboard.billList";
    private static final String METHOD_DOWNLOAD_MUSIC="baidu.ting.song.play";
    private static final String METHOD_GET_LRC="baidu.ting.song.lry";
    private static final String METHOD_SEARCH_MUSIC="baidu.ting.search.catalogSug";
    private static final String METHOD_ARTIST_INFO="baidu.ting.artist.getInfo";

    private static final String PARAM_METHOD="method";
    private static final String PARAM_TYPE="type";
    private static final String PARAM_SIZE="size";
    private static final String PARAM_OFFSET="offset";
    private static final String PARAM_SONGID="songid";
    private static final String PARAM_QUERY="query";
    private static final String PARAM_TINGUID="tinguid";

    /*
     *request splash image and then resolve it
     * @param callback
     */
    public static void getSplash(final HttpCallback<Splash> callback){
        OkHttpUtils.get().url(SPLASH_URL).build().execute(new JsonCallback<Splash>(Splash.class){
            @Override
            public void onResponse(Splash response) {
                callback.onSuccess(response);
            }

            @Override
            public void onError(Request request, Exception e) {
                callback.onFail(e);
            }

            @Override
            public void onAfter() {
                callback.onFinish();
            }
        });
    }

    /*
     *download file according to the url
     * @param url
     * @param destFileDir
     * @param destFileName
     * @param callback
     * return
     */
    public static void downloadFile(String url,String destFileDir,String destFileName,final HttpCallback<File> callback){
        OkHttpUtils.get().url(url).build().execute(new FileCallBack(destFileDir,destFileName){
            @Override
            public void inProgress(float progress) {

            }

            @Override
            public void onResponse(File response) {
                callback.onSuccess(response);
            }

            @Override
            public void onError(Request request, Exception e) {
                callback.onFail(e);
            }

            @Override
            public void onAfter() {
                callback.onFinish();
            }
        });
    }

    public static void getSongListInfo(String type,int size,int offset,final HttpCallback<OnlineMusic> callback){
        OkHttpUtils.get().url(BASE_URL)
                .addParams(PARAM_METHOD,METHOD_GET_MUSIC_LIST)
                .addParams(PARAM_TYPE,type)
                .addParams(PARAM_SIZE,String.valueOf(size))
                .addParams(PARAM_OFFSET,String.valueOf(offset))
                .build()
                .execute(new JsonCallback<OnlineMusic>(OnlineMusic.class){
                    @Override
                    public void onResponse(OnlineMusic response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        callback.onFail(e);
                    }

                    @Override
                    public void onAfter() {
                        callback.onFinish();
                    }
                });
    }

    public static void getMusicDownloadInfo(String songId,final HttpCallback<DownloadInfo> callback){
        OkHttpUtils.get().url(BASE_URL)
                .addParams(PARAM_METHOD,METHOD_DOWNLOAD_MUSIC)
                .addParams(PARAM_SONGID,songId)
                .build()
                .execute(new JsonCallback<DownloadInfo>(DownloadInfo.class){
                    @Override
                    public void onResponse(DownloadInfo response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        callback.onFail(e);
                    }

                    @Override
                    public void onAfter() {
                        callback.onFinish();
                    }
                });
    }

    public static void getBitmap(String url,final HttpCallback<Bitmap> callback){
        OkHttpUtils.get().url(url).build()
                .execute(new BitmapCallback(){
                    @Override
                    public void onResponse(Bitmap response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        callback.onFail(e);
                    }

                    @Override
                    public void onAfter() {
                        callback.onFinish();
                    }
                });
    }

    public static void getLrc(String songId,final HttpCallback<Lrc> callback){
        OkHttpUtils.get().url(BASE_URL)
                .addParams(PARAM_METHOD,METHOD_GET_LRC)
                .addParams(PARAM_SONGID,songId)
                .build()
                .execute(new JsonCallback<Lrc>(Lrc.class){
                    @Override
                    public void onResponse(Lrc response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        callback.onFail(e);
                    }

                    @Override
                    public void onAfter() {
                        callback.onFinish();
                    }
                });
    }

    public static void searchMusic(String musicName,final HttpCallback<SearchMusic> callback){
        OkHttpUtils.get().url(BASE_URL)
                .addParams(PARAM_METHOD,METHOD_SEARCH_MUSIC)
                .addParams(PARAM_QUERY,musicName)
                .build()
                .execute(new JsonCallback<SearchMusic>(SearchMusic.class){
                    @Override
                    public void onResponse(SearchMusic response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        callback.onFail(e);
                    }

                    @Override
                    public void onAfter() {
                        callback.onFinish();
                    }
                });
    }

    public static void getArtistInfo(String tinguid,final HttpCallback<ArtistInfo> callback){
        OkHttpUtils.get().url(BASE_URL)
                .addParams(PARAM_METHOD,METHOD_ARTIST_INFO)
                .addParams(PARAM_TINGUID,tinguid)
                .build()
                .execute(new JsonCallback<ArtistInfo>(ArtistInfo.class){
                    @Override
                    public void onResponse(ArtistInfo response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        callback.onFail(e);
                    }

                    @Override
                    public void onAfter() {
                        callback.onFinish();
                    }
                });
    }
}
