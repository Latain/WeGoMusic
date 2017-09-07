package com.vigolin.wegomusic.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.vigolin.wegomusic.R;
import com.vigolin.wegomusic.application.AppCached;
import com.vigolin.wegomusic.http.HttpCallback;
import com.vigolin.wegomusic.http.HttpClient;
import com.vigolin.wegomusic.module.Splash;
import com.vigolin.wegomusic.service.PlayService;
import com.vigolin.wegomusic.utils.FileUtils;
import com.vigolin.wegomusic.utils.PreferencesUtils;
import com.vigolin.wegomusic.utils.ToastUtils;
import com.vigolin.wegomusic.utils.binding.Bind;
import com.vigolin.wegomusic.utils.permission.PermissionReq;
import com.vigolin.wegomusic.utils.permission.PermissionResult;
import com.vigolin.wegomusic.utils.permission.Permissions;

import java.io.File;
import java.util.Calendar;

public class SplashActivity extends BaseActivity{
    private static final String SPLASH_FILE_NAME="splash";
    @Bind(R.id.iv_splash)
    private ImageView mSplashImage;
    @Bind(R.id.tv_copyright)
    private TextView tvCopyright;

    private ServiceConnection mPlayServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int year= Calendar.getInstance().get(Calendar.YEAR);
        tvCopyright.setText(getString(R.string.copyright,year));

        checkService();
    }

    private void checkService(){
        if(AppCached.getPlayService()==null){
            startService();
            updateSplash();
            initSplash();

            mHandler.postDelayed(new Runnable(){
                @Override
                public void run() {
                  bindService();
                }
            },1000);
        }else{
            startMusicActivity();
            finish();
        }
    }

    private void startService(){
        Intent intent=new Intent();
        intent.setClass(this,PlayService.class);
        startService(intent);
    }

    private void bindService(){
        Intent intent=new Intent(this,PlayService.class);
        mPlayServiceConnection=new PlayServiceConnection();
        bindService(intent,mPlayServiceConnection,BIND_AUTO_CREATE);
    }

    private void initSplash(){
        File file=new File(FileUtils.getSplashDir(this),SPLASH_FILE_NAME);
        if(file.exists()){
            Bitmap bitmap= BitmapFactory.decodeFile(file.getPath());
            mSplashImage.setImageBitmap(bitmap);
        }
    }

    private void updateSplash(){
        HttpClient.getSplash(new HttpCallback<Splash>(){
            @Override
            public void onSuccess(Splash splash) {
                if(splash==null || TextUtils.isEmpty(splash.getUrl()))
                    return;

                final String imageUrl=splash.getUrl();
                String cachedUrl= PreferencesUtils.getSplashUrl();
                if(TextUtils.equals(imageUrl,cachedUrl))
                    return;

                HttpClient.downloadFile(imageUrl,FileUtils.getSplashDir(SplashActivity.this),SPLASH_FILE_NAME,new HttpCallback<File>(){
                    @Override
                    public void onSuccess(File file) {
                        PreferencesUtils.saveSplashUrl(imageUrl);
                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                });
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }

    private void startMusicActivity(){
        Intent intent=new Intent(this,MusicActivity.class);
        intent.putExtras(getIntent());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private class PlayServiceConnection implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final PlayService mPlayService=((PlayService.PlayServiceBinder)service).getPlayService();
            AppCached.setPlayService(mPlayService);

            PermissionReq.with(SplashActivity.this)
                    .setPermissions(Permissions.STORAGE)
                    .setPermissionResult(new PermissionResult(){
                        @Override
                        public void onGranted() {
                            mPlayService.updateMusicList();
                            startMusicActivity();
                            finish();
                        }

                        @Override
                        public void onDenied() {
                            ToastUtils.show(getString(R.string.no_permission,Permissions.STORAGE_DESC,"扫描本地歌曲"));
                            finish();
                            mPlayService.stop();
                        }
                    })
                    .request();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
