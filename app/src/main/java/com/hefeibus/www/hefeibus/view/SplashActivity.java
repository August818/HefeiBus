package com.hefeibus.www.hefeibus.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.Trace;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.base.BaseActivity;

/**
 * splash activity
 * Created by cx on 2018/3/17.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void setAttributes() {

    }

    @Override
    protected void findViews() {

    }

    @Override
    protected int setLayoutView() {
        return R.layout.splash_layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //1.判断 Android Runtime Permission
        //2.判断 network status
        //3.判断 version status
        //4.跳转 main activity

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, 3000);
    }

    /**
     * 跳转到主界面
     */
    private void startMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainAcivity.class);
        startActivity(intent);
        this.finish();
    }
}
