package com.vigolin.wegomusic.module;

/**
 * Created by Administrator on 2017/7/15.
 */

public class Music {
    public enum Type{
        LOCAL,ONLINE
    }

    //音乐类型
    private Type type;
    //(本地)歌曲id
    private long id;
    //音乐标题
    private String title;
    //专辑
    private String album;
    //时长
    private long duration;
    //音乐路径
    private String path;
    //音乐封面路径
    private String coverPath;
    //歌手
    private String artist;
    //文件
    private String fileName;
    //文件大小
    private long fileSize;

    public Music(Builder builder){
        this.type=builder.type;
        this.id=builder.id;
        this.title=builder.title;
        this.album=builder.album;
        this.duration=builder.duration;
        this.path=builder.path;
        this.coverPath=builder.coverPath;
        this.artist=builder.artist;
        this.fileName=builder.fileName;
        this.fileSize=builder.fileSize;
    }

    public static class Builder{
        private Type type;
        private long id;
        private String title;
        private String album;
        private long duration;
        private String path;
        private String coverPath;
        private String artist;
        private String fileName;
        private long fileSize;

        public Builder setType(Type type){
            this.type=type;
            return this;
        }

        public Builder setId(long id){
            this.id=id;
            return this;
        }

        public Builder setTitle(String title){
            this.title=title;
            return this;
        }

        public Builder setAlbum(String album){
            this.album=album;
            return this;
        }

        public Builder setDuration(long duration){
            this.duration=duration;
            return this;
        }

        public Builder setPath(String path){
            this.path=path;
            return this;
        }

        public Builder setCoverPath(String coverPath){
            this.coverPath=coverPath;
            return this;
        }

        public Builder setArtist(String artist){
            this.artist=artist;
            return this;
        }

        public Builder setFileName(String fileName){
            this.fileName=fileName;
            return this;
        }

        public Builder setFileSize(long fileSize){
            this.fileSize=fileSize;
            return this;
        }

        public Music build(){
            return new Music(this);
        }
    }

    public Type getType(){
        return type;
    }

    public long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getArtist(){
        return artist;
    }

    public String getAlbum(){
        return album;
    }

    public String getPath(){
        return path;
    }

    public String getCoverPath(){
        return coverPath;
    }

    public String getFileName(){
        return fileName;
    }

    public long getDuration(){
        return duration;
    }

    public long getFileSize(){
        return fileSize;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null || !(obj instanceof Music))
            return false;
        return ((Music)obj).getId()==getId();
    }
}
