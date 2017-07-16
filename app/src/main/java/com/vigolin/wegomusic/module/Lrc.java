package com.vigolin.wegomusic.module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/16.
 */

public class Lrc {
    @SerializedName("lrcContent")
    private String lrcContent;

    public void setLrcContent(String lrcContent){
        this.lrcContent=lrcContent;
    }

    public String getLrcContent(){
        return lrcContent;
    }
}
