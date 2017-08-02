package com.vigolin.wegomusic.service;

import com.vigolin.wegomusic.module.Music;

/**
 * Created by Administrator on 2017/7/28.
 */

public interface OnPlayerEventListener {
    //更新播放进度
    void onPublish(int progress);
    //暂停播放
    void onPlayerPause();
    //切换音乐
    void onChange(Music music);
    //继续播放
    void onPlayerResume();
    //更新定时停止播放时间
    void onTimer(long remain);
}
