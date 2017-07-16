package com.vigolin.wegomusic.module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/16.
 */

public class DownloadInfo {
    @SerializedName("bitrate")
    private Bitrate bitrate;

    public void setBitrate(Bitrate bitrate){
        this.bitrate=bitrate;
    }

    public Bitrate getBitrate(){
        return bitrate;
    }

    private static class Bitrate{
        @SerializedName("file_duration")
        private String file_duration;
        @SerializedName("file_link")
        private String file_link;

        public void setFile_duration(String file_duration){
            this.file_duration=file_duration;
        }

        public String getFile_duration(){
            return file_duration;
        }

        public void setFile_link(String file_link){
            this.file_link=file_link;
        }

        public String getFile_link(){
            return file_link;
        }
    }
}
