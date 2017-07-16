package com.vigolin.wegomusic.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */

public class SearchMusic {
    @SerializedName("song")
    private List<Song> song;

    public void setSong(List<Song> song){
        this.song=song;
    }

    public List<Song> getSong(){
        return song;
    }

    private static class Song{
        @SerializedName("songname")
        private String songname;
        @SerializedName("songid")
        private String songid;
        @SerializedName("artistname")
        private String artistname;

        public void setSongname(String songname){
            this.songname=songname;
        }

        public String getSongname(){
            return songname;
        }

        public void setSongid(String songid){
            this.songid=songid;
        }

        public String getSongid(){
            return songid;
        }

        public void setArtistname(String artistname){
            this.artistname=artistname;
        }

        public String getArtistname(){
            return artistname;
        }
    }
}
