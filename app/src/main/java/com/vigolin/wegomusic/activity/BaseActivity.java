package com.vigolin.wegomusic.activity;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.vigolin.wegomusic.R;
import com.vigolin.wegomusic.application.AppCached;
import com.vigolin.wegomusic.service.PlayService;
import com.vigolin.wegomusic.utils.binding.ViewBinder;
import com.vigolin.wegomusic.utils.permission.PermissionReq;

/**
 * Created by Administrator on 2017/7/16.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG="activity";
    protected Handler mHandler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate:"+getClass().getSimpleName());

        setSystemBarTransparent();
        AppCached.addToStack(this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        init();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        init();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        init();
    }

    private void init(){
        ViewBinder.bind(this);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        if(toolbar==null){
            throw new IllegalArgumentException("Layout is required a toobar which id is toobar");
        }
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        AppCached.removeFromStack(this);
        super.onDestroy();
        Log.d(TAG,"onDestroy:"+getClass().getSimpleName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setSystemBarTransparent(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    protected void setListener(){}

    public PlayService getPlayservice(){
        PlayService playService=AppCached.getPlayService();
        if(playService==null)
            throw new NullPointerException("PlayService is null");

        return playService;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionReq.onRequestPermissionResult(requestCode,permissions,grantResults);
    }

    public void showSoftKeyboard(final EditText editText){
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        mHandler.postDelayed(new Runnable(){
            @Override
            public void run() {
                InputMethodManager inputManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText,0);
            }
        },200L);
    }

    public void hideSoftKeyboard(){
        if(getCurrentFocus()!=null){
            InputMethodManager inputManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
