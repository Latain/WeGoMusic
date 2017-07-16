package com.vigolin.wegomusic.module;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */

public class Splash {
    private static final String URL="http://cn.bing.com";
    @SerializedName("images")
    private List<ImageBean> images;

    public String getUrl(){
        if(images!=null && images.size()>0){
            String imageUrl=images.get(0).url;
            if(!TextUtils.isEmpty(imageUrl)){
                return URL+imageUrl;
            }
        }
        return null;
    }

    private static class ImageBean{
        @SerializedName("startdate")
        private String startdate;
        @SerializedName("fullstartdate")
        private String fullstartdate;
        @SerializedName("enddate")
        private String enddate;
        @SerializedName("url")
        private String url;
        @SerializedName("urlbase")
        private String urlbase;
        @SerializedName("copyright")
        private String copyright;
        @SerializedName("copyrightlink")
        private String copyrightlink;
        @SerializedName("quiz")
        private String quiz;
        @SerializedName("wp")
        private String wp;
        @SerializedName("hsh")
        private String hsh;
        @SerializedName("drk")
        private String drk;
        @SerializedName("top")
        private String top;
        @SerializedName("boot")
        private String bot;
        @SerializedName("hs")
        private List<?> hs;
    }
}
