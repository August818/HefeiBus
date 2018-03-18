package com.hefeibus.www.hefeibus.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
        return R.layout.activity_splash;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //1.判断 Android Runtime Permission
        checkRuntimePermissions();

        //2.判断 network status
        checkNetworkConnections();

        //3.判断 version status
        checkVersionUpdate();

        //4.跳转 main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, 1500);
    }

    /**
     * 检查版本情况
     */
    private void checkVersionUpdate() {

    }

    /**
     * 检查网络连通情况
     */
    private void checkNetworkConnections() {

    }

    /**
     * 检查运行时权限
     */
    private void checkRuntimePermissions() {

    }

    /**
     * 跳转到主界面
     */
    private void startMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
