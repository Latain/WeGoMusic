package com.vigolin.wegomusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vigolin.wegomusic.constants.Actions;
import com.vigolin.wegomusic.service.PlayService;

/**
 * Created by Administrator on 2017/8/3.
 */

public class NoisyAudioStreamReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PlayService.startCommand(context, Actions.ACTION_MEDIA_PLAY_PAUSE);
    }
}
