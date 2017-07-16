package com.vigolin.wegomusic.module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/16.
 */

public class OnlineMusic {
    @SerializedName("pic_big")
    private String pic_big;
    @SerializedName("pic_small")
    private String pic_small;
    @SerializedName("lrclink")
    private String lrclink;
    @SerializedName("song_id")
    private String song_id;
    @SerializedName("title")
    private String title;
    @SerializedName("ting_uid")
    private String ting_uid;
    @SerializedName("album_id")
    private String album_id;
    @SerializedName("album_title")
    private String album_title;
    @SerializedName("artist_name")
    private String artist_name;

    public void setPic_big(String pic_big){
        this.pic_big=pic_big;
    }

    public String getPic_big(){
        return pic_big;
    }

    public void setPic_small(String pic_small){
        this.pic_big=pic_small;
    }

    public String getPic_small(){
        return pic_small;
    }

    public void setLrclink(String lrclink){
        this.lrclink=lrclink;
    }

    public String getLrclink(){
        return lrclink;
    }

    public void setSong_id(String song_id){
        this.song_id=song_id;
    }

    public String getSong_id(){
        return song_id;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getTitle(){
        return title;
    }

    public void setTing_uid(String ting_uid){
        this.ting_uid=ting_uid;
    }

    public String getTing_uid(){
        return ting_uid;
    }

    public void setAlbum_id(String album_id){
        this.album_id=album_id;
    }

    public String getAlbum_id(){
        return album_id;
    }

    public void setAlbum_title(String album_title){
        this.album_title=album_title;
    }

    public String getAlbum_title(){
        return album_title;
    }

    public void setArtist_name(String artist_name){
        this.artist_name=artist_name;
    }

    public String getArtist_name(){
        return artist_name;
    }
}
