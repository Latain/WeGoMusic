package com.vigolin.wegomusic.module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/16.
 */

public class ArtistInfo {
    //体重
    @SerializedName("weight")
    private String weight;
    //身高
    @SerializedName("stature")
    private String stature;
    //歌手链接
    @SerializedName("url")
    private String url;
    //星座
    @SerializedName("constellation")
    private String constellation;
    //简介
    @SerializedName("intro")
    private String intro;
    //国籍
    @SerializedName("country")
    private String country;
    //生日
    @SerializedName("birth")
    private String birth;
    //姓名
    @SerializedName("name")
    private String name;
    //头像链接
    @SerializedName("avatar_s1000")
    private String avatar_s1000;

    public void setWeight(String weight){
        this.weight=weight;
    }

    public String getWeight(){
        return weight;
    }

    public void setStature(String stature){
        this.stature=stature;
    }

    public String getStature(){
        return stature;
    }

    public void setUrl(String url){
        this.url=url;
    }

    public String getUrl(){
        return url;
    }

    public void setConstellation(String constellation){
        this.constellation=constellation;
    }

    public String getConstellation(){
        return constellation;
    }

    public void setIntro(String intro){
        this.intro=intro;
    }

    public String getIntro(){
        return intro;
    }

    public void setCountry(String country){
        this.country=country;
    }

    public String getCountry(){
        return country;
    }

    public void setBirth(String birth){
        this.birth=birth;
    }

    public String getBirth(){
        return birth;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setAvatar_s1000(String avatar_s1000){
        this.avatar_s1000=avatar_s1000;
    }

    public String getAvatar_s1000(){
        return avatar_s1000;
    }
}
