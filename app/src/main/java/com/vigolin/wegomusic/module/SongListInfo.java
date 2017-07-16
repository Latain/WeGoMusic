package com.vigolin.wegomusic.module;

/**
 * Created by Administrator on 2017/7/16.
 * song list information
 * 主打榜单
 * 分类榜单
 * 媒体榜单
 */

public class SongListInfo {
    private String title;
    private String type;
    private String coverUrl;
    private String musicOrder1;
    private String musicOrder2;
    private String musicOrder3;

    public void setTitle(String title){
        this.title=title;
    }

    public String getTitle(){
        return title;
    }

    public void setType(String type){
        this.type=type;
    }

    public String getType(){
        return type;
    }

    public void setCoverUrl(String coverUrl){
        this.coverUrl=coverUrl;
    }

    public String getCoverUrl(){
        return coverUrl;
    }

    public void setMusicOrder1(String musicOrder1){
        this.musicOrder1=musicOrder1;
    }

    public String getMusicOrder1(){
        return musicOrder1;
    }

    public void setMusicOrder2(String musicOrder2){
        this.musicOrder2=musicOrder2;
    }

    public String getMusicOrder2(){
        return musicOrder2;
    }

    public void setMusicOrder3(String musicOrder3){
        this.musicOrder3=musicOrder3;
    }

    public String getMusicOrder3(){
        return musicOrder3;
    }
}
