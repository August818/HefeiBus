package com.hefeibus.www.hefeibus.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpActivity;
import com.hefeibus.www.hefeibus.basemvp.IPresenter;
import com.hefeibus.www.hefeibus.utils.ActivityController;
import com.hefeibus.www.hefeibus.utils.Parameters;
import com.hefeibus.www.hefeibus.view.mian_framework.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * splash activity
 * Created by cx on 2018/3/17.
 */


public class SplashActivity extends BaseMvpActivity {

    private final String[] dangerPermissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


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
        checkRuntimePermissions();
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
        } finally {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startMainActivity();
                }
            }, 3000);
        }

    }

    /**
     * 检查运行时权限
     */
    private void checkRuntimePermissions() {
        //检查未授权,加到 need 列表中
        List<String> ungrantedPermissions = new ArrayList<>();
        for (String s : dangerPermissions) {
            if (ContextCompat.checkSelfPermission(this, s) != PackageManager.PERMISSION_GRANTED) {
                ungrantedPermissions.add(s);
            }
        }
        if (ungrantedPermissions.isEmpty()) {
            initDatabase();
            Log.d(TAG, "checkRuntimePermissions: Permission check Successful");
        } else {
            ActivityCompat.requestPermissions(
                    SplashActivity.this,
                    ungrantedPermissions.toArray(new String[ungrantedPermissions.size()]),
                    1);
        }
    }


    /**
     * 动态权限处理 回调
     *
     * @param requestCode  请求号
     * @param permissions  权限列表
     * @param grantResults 是否授权
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length == 0 || grantResults.length == 0) {
            naviToSetting();
        } else {
            switch (requestCode) {
                case 1: {
                    boolean isGranted = true;
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            isGranted = false;
                            break;
                        }
                    }
                    if (isGranted) {
                        Log.d(TAG, "onRequestPermissionsResult: Permission Granted!");
                        initDatabase();
                    } else {
                        naviToSetting();
                    }
                }
            }
        }
    }

    /**
     * 检查权限时，如果没有获取到所需的权限弹出对话框
     * 2个路径：
     * 退出或者前往设置
     */
    private void naviToSetting() {
        new AlertDialog.Builder(this)
                .setTitle("权限申请失败")
                .setCancelable(false)
                .setMessage("没有获得权限程序将无法正常运行,点击设置进入设置界面授予权限,点击取消退出程序")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityController.getInstance().quit();
                    }
                })
                .show();
    }


    /**
     * 检查网络连通情况
     */
    private void checkNetworkConnections() {

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
