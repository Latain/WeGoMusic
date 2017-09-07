package com.vigolin.wegomusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.vigolin.wegomusic.constants.Actions;
import com.vigolin.wegomusic.service.PlayService;

public class StatusBarReceiver extends BroadcastReceiver {
    public static final String ACTION_STATUS_BAR="com.vigolin.wegomusic.STATUS_BAR_ACTIONS";
    public static final String EXTRA="extra";
    public static final String EXTRA_NEXT="extra_next";
    public static final String EXTRA_PLAY_PAUSE="extra_play_pause";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent==null || TextUtils.isEmpty(intent.getAction()))
            return;
        String action=intent.getStringExtra(EXTRA);
        if(TextUtils.equals(action,EXTRA_NEXT))
            PlayService.startCommand(context, Actions.ACTION_MEDIA_NEXT);
        else if(TextUtils.equals(action,EXTRA_PLAY_PAUSE))
            PlayService.startCommand(context,Actions.ACTION_MEDIA_PLAY_PAUSE);
    }
}
