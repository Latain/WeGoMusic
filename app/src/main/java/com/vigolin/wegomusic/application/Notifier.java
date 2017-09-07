package com.vigolin.wegomusic.application;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.vigolin.wegomusic.R;
import com.vigolin.wegomusic.activity.SplashActivity;
import com.vigolin.wegomusic.constants.Extras;
import com.vigolin.wegomusic.module.Music;
import com.vigolin.wegomusic.receiver.StatusBarReceiver;
import com.vigolin.wegomusic.service.PlayService;
import com.vigolin.wegomusic.utils.CoverLoader;
import com.vigolin.wegomusic.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VigoLin on 2017/8/2.
 */

public class Notifier {
    private static final int NOTIFICATION_ID=0X111;
    private PlayService mPlayService;
    private NotificationManager mNotificationManager;

    private static class NotifierHolder{
        public static Notifier notifierInstance=new Notifier();
    }

    public static Notifier getInstance(PlayService playService){
        Notifier notifier=NotifierHolder.notifierInstance;
        notifier.mPlayService=playService;
        notifier.mNotificationManager=(NotificationManager)((Context)playService).getSystemService(Context.NOTIFICATION_SERVICE);
        return notifier;
    }

    public void showPlay(Music music){
        mPlayService.startForeground(NOTIFICATION_ID,buildNotification(mPlayService,music,true));
    }

    public void showPause(Music music){
        mPlayService.stopForeground(false);
        mNotificationManager.notify(NOTIFICATION_ID,buildNotification(mPlayService,music,false));
    }

    public void cancelAll(){
        mNotificationManager.cancelAll();
    }

    public void setPlayService(PlayService playService){
        this.mPlayService=playService;
    }

    private Notification buildNotification(Context context,Music music,boolean isPlaying){
        Intent intent=new Intent(context,SplashActivity.class);
        intent.putExtra(Extras.EXTRA_NOTIFICATION,true);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification=new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notification)
                .setCustomContentView(getRemoteViews(context,music,isPlaying))
                .build();
        return notification;
    }

    private RemoteViews getRemoteViews(Context context, Music music, boolean isPlaying){
        String title=music.getTitle();
        String subTitle= FileUtils.getArtistAndAlbum(music.getArtist(),music.getAlbum());
        Bitmap cover= CoverLoader.getInstance().loadThumbnail(music);

        RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.notification);
        if(cover!=null){
            remoteViews.setImageViewBitmap(R.id.iv_icon,cover);
        }else{
            remoteViews.setImageViewResource(R.id.iv_icon,R.drawable.ic_launcher);
        }

        remoteViews.setTextViewText(R.id.tv_title,title);
        remoteViews.setTextViewText(R.id.tv_subtitle,subTitle);
        boolean isLightNotificationTheme=isLightNotificationTheme(mPlayService);

        Intent playIntent=new Intent();
        playIntent.setAction(StatusBarReceiver.ACTION_STATUS_BAR);
        playIntent.putExtra(StatusBarReceiver.EXTRA,StatusBarReceiver.EXTRA_PLAY_PAUSE);
        PendingIntent playPendingIntent=PendingIntent.getBroadcast(context,0,playIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setImageViewResource(R.id.iv_play_pause,getPlayIconRes(isLightNotificationTheme,isPlaying));
        remoteViews.setOnClickPendingIntent(R.id.iv_play_pause,playPendingIntent);

        Intent nextIntent=new Intent();
        nextIntent.setAction(StatusBarReceiver.ACTION_STATUS_BAR);
        nextIntent.putExtra(StatusBarReceiver.EXTRA,StatusBarReceiver.EXTRA_NEXT);
        PendingIntent nextPendingIntent=PendingIntent.getBroadcast(context,1,nextIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setImageViewResource(R.id.iv_next,getNextIconRes(isLightNotificationTheme));
        remoteViews.setOnClickPendingIntent(R.id.iv_next,nextPendingIntent);

        return remoteViews;
    }

    private static int getPlayIconRes(boolean isLightNotificationTheme,boolean isPlaying){
        if(isPlaying){
            return isLightNotificationTheme?R.drawable.ic_status_bar_pause_dark_selector :R.drawable.ic_status_bar_pause_light_selector;
        }else{
            return isLightNotificationTheme?R.drawable.ic_status_bar_play_dark_selector:R.drawable.ic_status_bar_play_light_selector;
        }
    }

    private static int getNextIconRes(boolean isLightNotificationTheme){
        return isLightNotificationTheme?R.drawable.ic_status_bar_next_dark_selector:R.drawable.ic_status_bar_next_light_selector;
    }

    private static boolean isLightNotificationTheme(Context context){
        int color=getNotificationLayoutTextColor(context);
        return isSimilarColor(Color.BLACK,color);
    }

    private static int getNotificationLayoutTextColor(Context context){
        Notification notification=new NotificationCompat.Builder(context).build();
        int notificationLayoutId=notification.contentView.getLayoutId();
        ViewGroup notificationLayout=(ViewGroup) LayoutInflater.from(context).inflate(notificationLayoutId,null);

        TextView titleView=(TextView)notificationLayout.findViewById(android.R.id.title);
        if(titleView!=null){
            return titleView.getCurrentTextColor();
        }else{
            return findTextColor(notificationLayout);
        }
    }

    private static int findTextColor(ViewGroup notificationLayout){
        List<TextView> textViewList=new ArrayList<>();
        findTextView(notificationLayout,textViewList);

        float maxSize=-1;
        TextView mTextView=null;
        for(TextView textView:textViewList){
            float textSize=textView.getTextSize();
            if(textSize>maxSize){
                maxSize=textSize;
                mTextView=textView;
            }
        }
        if(mTextView!=null)
            return mTextView.getCurrentTextColor();
        return Color.BLACK;
    }

    private static void findTextView(View view, List<TextView> textViewList){
        if(view instanceof ViewGroup){
            ViewGroup viewGroup=(ViewGroup)view;
            for(int i=0;i<viewGroup.getChildCount();i++){
                findTextView(viewGroup.getChildAt(i),textViewList);
            }
        }else if(view instanceof TextView){
            textViewList.add((TextView)view);
        }
    }

    private static boolean isSimilarColor(int baseColor,int color){
        int simpleBaseColor=baseColor | 0xff000000;
        int simpleColor=color | 0xff000000;
        int baseRed= Color.red(simpleBaseColor)-Color.red(simpleColor);
        int baseGreen=Color.green(simpleBaseColor)-Color.green(simpleColor);
        int baseBlue=Color.blue(simpleBaseColor)-Color.blue(simpleColor);
        double value=Math.sqrt(baseRed*baseRed+baseGreen*baseGreen+baseBlue*baseBlue);
        return value>180.0;
    }
}
