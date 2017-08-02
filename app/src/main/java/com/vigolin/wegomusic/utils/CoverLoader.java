package com.vigolin.wegomusic.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.vigolin.wegomusic.R;
import com.vigolin.wegomusic.application.AppCached;
import com.vigolin.wegomusic.module.Music;

/**
 * Created by VigoLin on 2017/7/27.
 * 专辑封面加载器
 */

public class CoverLoader {
    private static final String KEY_NULL="null";
    /*
     *缩略图缓存，用于音乐列表
     */
    private LruCache<String,Bitmap> mThumbnailCached;
    /*
     *高清图缓存，用于播放页背景
     */
    private LruCache<String,Bitmap> mBlurCached;
    /*
     *圆形图缓存，用于播放页CD
     */
    private LruCache<String,Bitmap> mCircleCached;

    private CoverLoader(){
        int maxMemory=(int)Runtime.getRuntime().maxMemory();//获取当前进程最大运行内存
        int cachedMemory=maxMemory/8;//缓存设置为最大内存的1/8

        mThumbnailCached=new LruCache<String,Bitmap>(cachedMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };

        mBlurCached=new LruCache<String,Bitmap>(cachedMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };

        mCircleCached=new LruCache<String,Bitmap>(cachedMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };
    }

    private static class SingletonInstance{
        public static final CoverLoader INSTANCE=new CoverLoader();
    }

    public static CoverLoader getInstance(){
        return SingletonInstance.INSTANCE;
    }

    public Bitmap loadThumbnail(Music music){
        Bitmap bitmap;
        String path=FileUtils.getAlbumFilePath(music);
        if(TextUtils.isEmpty(path)){
            bitmap=mThumbnailCached.get(KEY_NULL);
            if(bitmap==null){
                bitmap=BitmapFactory.decodeResource(AppCached.getContext().getResources(), R.drawable.default_cover);
                mThumbnailCached.put(KEY_NULL,bitmap);
            }
        }else{
            bitmap=mThumbnailCached.get(path);
            if(bitmap==null){
                bitmap=loadBitmap(path,ScreenUtils.getScreenWidth()/10);
                if(bitmap==null){
                    bitmap=loadThumbnail(null);
                }
                mThumbnailCached.put(path,bitmap);
            }
        }
        return bitmap;
    }

    public Bitmap loadBlur(Music music){
        Bitmap bitmap;
        String path=FileUtils.getAlbumFilePath(music);
        if(TextUtils.isEmpty(path)){
            bitmap=mBlurCached.get(KEY_NULL);
            if(bitmap==null){
                bitmap=BitmapFactory.decodeResource(AppCached.getContext().getResources(),R.drawable.play_page_default_bg);
                mBlurCached.put(KEY_NULL,bitmap);
            }
        }else{
            bitmap=mBlurCached.get(path);
            if(bitmap==null){
                bitmap=loadBitmap(path,ScreenUtils.getScreenWidth()/2);
                if(bitmap==null){
                    bitmap=loadBlur(null);
                }else{
                    ImageUtils.blur(bitmap);
                }
                mBlurCached.put(path,bitmap);
            }
        }
        return bitmap;
    }

    public Bitmap loadCircle(Music music){
        Bitmap bitmap;
        String path=FileUtils.getAlbumFilePath(music);
        if(TextUtils.isEmpty(path)){
            bitmap=mCircleCached.get(KEY_NULL);
            if(bitmap==null){
                bitmap=BitmapFactory.decodeResource(AppCached.getContext().getResources(),R.drawable.play_page_default_cover);
                bitmap=ImageUtils.resizeImage(bitmap,ScreenUtils.getScreenWidth()/2,ScreenUtils.getScreenWidth()/2);
                mCircleCached.put(KEY_NULL,bitmap);
            }
        }else{
            bitmap=mCircleCached.get(path);
            if(bitmap==null){
                bitmap=loadBitmap(path,ScreenUtils.getScreenWidth()/2);
                if(bitmap==null){
                    bitmap=loadCircle(null);
                }else{
                    bitmap=ImageUtils.resizeImage(bitmap,ScreenUtils.getScreenWidth()/2,ScreenUtils.getScreenWidth()/2);
                    bitmap=ImageUtils.createCircleImage(bitmap);
                }
                mCircleCached.put(path,bitmap);
            }
        }
        return bitmap;
    }

    private Bitmap loadBitmap(String path,int length){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(path,options);
        int maxLength=options.outWidth>options.outHeight?options.outWidth:options.outHeight;
        int sampleSize=maxLength/length;
        if(sampleSize<1)
            sampleSize=1;
        options.inSampleSize=sampleSize;
        return BitmapFactory.decodeFile(path,options);
    }
}
