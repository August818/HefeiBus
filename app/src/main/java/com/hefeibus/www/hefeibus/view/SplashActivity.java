package com.hefeibus.www.hefeibus.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpActivity;
import com.hefeibus.www.hefeibus.basemvp.IPresenter;
import com.hefeibus.www.hefeibus.utils.Parameters;
import com.hefeibus.www.hefeibus.view.mian_framework.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * splash activity
 * Created by cx on 2018/3/17.
 */


public class SplashActivity extends BaseMvpActivity {
    private static final String TAG = "SplashActivity";

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
        initDatabase();

        //4.跳转 main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, 100);
    }

    @Override
    protected IPresenter onCreatePresenter() {
        return null;
    }

    /**
     * 检查版本情况
     */
    private void initDatabase() {
        //复制标记
        boolean isExisted = false;

        //检测App关联的文件
        String[] associateFile = getApplicationContext().fileList();
        for (String s : associateFile) {
            if (s.equals(Parameters.LINE_GROUP_DATABASE_NAME)) {
                File file = new File(getFilesDir().getAbsolutePath() + "/" + Parameters.LINE_GROUP_DATABASE_NAME);
                isExisted = !file.delete();
            }
        }

        //检测并复制
        try {
            if (!isExisted) {
                int resource = R.raw.hefeibus_2;
                InputStream inputStream = getResources().openRawResource(resource);
                FileOutputStream outputStream = openFileOutput(Parameters.LINE_GROUP_DATABASE_NAME, Context.MODE_PRIVATE);
                int c;
                while ((c = inputStream.read()) != -1) {
                    outputStream.write(c);
                }
                outputStream.close();
                inputStream.close();
                Log.d(TAG, "initDatabase: Database file write succeed");
            } else {
                Log.d(TAG, "initDatabase: Already has Database File!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Database File Init Failed!", Toast.LENGTH_SHORT).show();
        }

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
