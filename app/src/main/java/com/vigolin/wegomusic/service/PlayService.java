package com.vigolin.wegomusic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.text.format.DateUtils;
import android.util.Log;

import com.vigolin.wegomusic.application.AppCached;
import com.vigolin.wegomusic.enums.PlayModeEnum;
import com.vigolin.wegomusic.module.Music;
import com.vigolin.wegomusic.utils.MusicUtils;
import com.vigolin.wegomusic.utils.PreferencesUtils;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class PlayService extends Service implements MediaPlayer.OnCompletionListener,AudioManager.OnAudioFocusChangeListener{
    private static final String TAG="service";
    private static final long TIME_UPDATE=100L;
    private List<Music> musicList;
    private MediaPlayer mPlayer=new MediaPlayer();
    private OnPlayerEventListener mListener;
    private Handler mHandler=new Handler();
    private AudioManager audioManager;
    private Music mPlayingMusic;
    private int mPlayingPosition;
    private boolean isPausing;
    private boolean isPreparing;
    private long quitTimerRemain;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate:"+getClass().getSimpleName());
        musicList= AppCached.getMusicList();
        audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mPlayer.setOnCompletionListener(this);
    }


    public PlayService() {
    }

    @Override
    public IBinder onBind(Intent intent){
        return new PlayServiceBinder();
    }

    public static void startCommand(Context context,String action){
        Intent intent=new Intent(context,PlayService.class);
        intent.setAction(action);
        context.startService(intent);
    }

   @Override
   public int onStartCommand(Intent intent,int flags,int startId){
       return super.onStartCommand(intent,flags,startId);
   }

    public void updateMusicList(){
        MusicUtils.scanMusic(this,musicList);
        if(musicList.isEmpty()){
            return;
        }
        updatePlayingPosition();
        mPlayingMusic=mPlayingMusic==null?musicList.get(mPlayingPosition):mPlayingMusic;
    }

    /*
     *更新正在播放的音乐的位置
     */
    public void updatePlayingPosition(){
        int position=0;
        long id=PreferencesUtils.getCurrentSongId();
        for(int i=0;i<musicList.size();i++){
            if(id==musicList.get(i).getId()){
                position=i;
                break;
            }
        }
        mPlayingPosition=position;
        PreferencesUtils.saveCurrentSongId(musicList.get(mPlayingPosition).getId());
    }

    private MediaPlayer.OnPreparedListener mPreparedListener=new MediaPlayer.OnPreparedListener(){
        public void onPrepared(MediaPlayer mPlayer){
            isPreparing=false;
            start();
        }
    };

    public void playPause(){
        if(isPreparing)
            return;

        if(isPlaying())
            pause();
        else if(isPausing)
            resume();
        else
            play(getPlayingPosition());
    }

    public void play(int position){
        if(musicList.isEmpty())
            return;
        if(position<0)
            position=musicList.size()-1;
        else if(position>=musicList.size())
            position=0;
        mPlayingPosition=position;
        Music music=musicList.get(mPlayingPosition);
        PreferencesUtils.saveCurrentSongId(music.getId());
        play(music);
    }

    public void play(Music music){
        mPlayingMusic=music;
        try{
            mPlayer.reset();
            mPlayer.setDataSource(music.getPath());
            mPlayer.prepareAsync();
            isPreparing=true;
            mPlayer.setOnPreparedListener(mPreparedListener);

            if(mListener!=null)
                mListener.onChange(music);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /*
     *开始播放
     */
    private void start(){
        mPlayer.start();
        isPausing=false;
        mHandler.post(mBackgroundRunnable);
        audioManager.requestAudioFocus(this,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
    }

    /*
     *暂停
     */
    private void pause(){
        if(!isPlaying())
            return;

        mPlayer.pause();
        isPausing=true;
        mHandler.removeCallbacks(mBackgroundRunnable);
        audioManager.abandonAudioFocus(this);

        if(mListener!=null)
            mListener.onPlayerPause();
    }

    //继续播放
    private void resume(){
        if(!isPausing)
            return;

        start();
        if(mListener!=null){
            mListener.onPlayerResume();
        }
    }

    /*
     *跳转到指定的时间
     * @param msec 时间
     */
    public void seekTo(int msec){
        if(isPlaying() || isPausing()){
            mPlayer.seekTo(msec);
            if(mListener!=null)
                mListener.onPublish(msec);
        }
    }

    private void stop(){
        pause();
        stopQuitTimer();
        mPlayer.reset();
        mPlayer.release();
        mPlayer=null;
        AppCached.setPlayService(null);
        stopSelf();
    }


    public void next(){
        if(musicList.isEmpty())
            return;

        PlayModeEnum playMode=PlayModeEnum.valueOf(PreferencesUtils.getPlayMode());
        switch(playMode){
            case SHUFFLE:
                mPlayingPosition=new Random().nextInt(musicList.size());
                play(mPlayingPosition);
                break;
            case SINGLE:
                play(mPlayingPosition);
                break;
            case LOOP:
            default:
                play(mPlayingPosition+1);
                break;
        }
    }

    public void prev(){
        if(musicList.isEmpty())
            return;

        PlayModeEnum playMode=PlayModeEnum.valueOf(PreferencesUtils.getPlayMode());
        switch(playMode){
            case SHUFFLE:
                mPlayingPosition=new Random().nextInt(musicList.size());
                play(mPlayingPosition);
                break;
            case SINGLE:
                play(mPlayingPosition);
                break;
            case LOOP:
            default:
                play(mPlayingPosition-1);
                break;
        }
    }

    public boolean isPlaying(){
        return mPlayer!=null && mPlayer.isPlaying();
    }

    public boolean isPausing(){
        return mPlayer!=null && isPausing;
    }

    public boolean isPreparing(){
        return mPlayer!=null && isPreparing;
    }

    public int getPlayingPosition(){
        return mPlayingPosition;
    }

    public void setOnPlayerEventListener(OnPlayerEventListener mListener){
        this.mListener=mListener;
    }

    /*
     *获取正在播放的音乐
     */
    public Music getPlayingMusic(){
        return mPlayingMusic;
    }

    /*
     *更新播放进度任务
     */
    private Runnable mBackgroundRunnable=new Runnable(){
        @Override
        public void run(){
            if(mListener!=null)
                mListener.onPublish(mPlayer.getCurrentPosition());
            mHandler.postDelayed(this,TIME_UPDATE);
        }
    };

    private Runnable mQuitRunnable=new Runnable(){
        @Override
        public void run(){
            quitTimerRemain-= DateUtils.SECOND_IN_MILLIS;
            if(quitTimerRemain>0){
                if(mListener!=null){
                    mListener.onTimer(quitTimerRemain);
                }
                mHandler.postDelayed(this,DateUtils.SECOND_IN_MILLIS);
            }else{
                AppCached.clearStack();
                stop();
            }
        }
    };

    public void startQuitTimer(long milli){
        stopQuitTimer();
        if(milli>0){
            quitTimerRemain+=milli;
            mHandler.post(mQuitRunnable);
        }else{
            quitTimerRemain=0;
            if(mListener!=null)
                mListener.onTimer(quitTimerRemain);
        }
    }

    private void stopQuitTimer(){
        mHandler.removeCallbacks(mQuitRunnable);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        next();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch(focusChange){
            case AudioManager.AUDIOFOCUS_LOSS:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                pause();
                break;
        }
    }

    @Override
    public void onDestroy() {
        AppCached.setPlayService(null);
        super.onDestroy();
        Log.d(TAG,"onDestroy:"+getClass().getSimpleName());
    }

    public class PlayServiceBinder extends Binder {
        public PlayService getPlaySerive(){
            return PlayService.this;
        }
    }
}
