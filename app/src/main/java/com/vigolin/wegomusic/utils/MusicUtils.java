package com.vigolin.wegomusic.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.vigolin.wegomusic.module.Music;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */

public class MusicUtils {
    public static void scanMusic(Context context, List<Music> musicList){
        musicList.clear();
        Cursor cursor=context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                ,null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if(cursor==null){
            return;
        }

        while(cursor.moveToNext()){
            int isMusic=cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
            if(isMusic==0)
                continue;
            long musicId=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String album=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            long albumId=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            String coverPath=getCoverPath(context,albumId);
            long duration=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            String path=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            String fileName=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            long fileSize=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));

            Music music=new Music.Builder()
                    .setId(musicId)
                    .setType(Music.Type.LOCAL)
                    .setTitle(title)
                    .setArtist(artist)
                    .setAlbum(album)
                    .setDuration(duration)
                    .setPath(path)
                    .setCoverPath(coverPath)
                    .setFileName(fileName)
                    .setFileSize(fileSize)
                    .build();
            CoverLoader.getInstance().loadThumbnail(music);
            musicList.add(music);
        }
    }

    private static String getCoverPath(Context context,long albumId){
        String path=null;
        Cursor cursor=context.getContentResolver().query(Uri.parse("content://media/external/audio/albums/"+albumId),
                new String[]{"album_art"},null,null,null);
        if(cursor!=null) {
            cursor.moveToNext();
            path = cursor.getString(0);
            cursor.close();
        }
        return path;
    }
}
