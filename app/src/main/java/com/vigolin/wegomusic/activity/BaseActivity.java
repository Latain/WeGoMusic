package com.vigolin.wegomusic.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vigolin.wegomusic.R;
import com.vigolin.wegomusic.application.AppCached;
import com.vigolin.wegomusic.utils.binding.ViewBinder;

/**
 * Created by Administrator on 2017/7/16.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG="activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate:"+getClass().getSimpleName());

        AppCached.addToStack(this);
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
}
