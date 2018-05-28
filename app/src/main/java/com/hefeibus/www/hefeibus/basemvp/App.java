package com.hefeibus.www.hefeibus.basemvp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.hefeibus.www.hefeibus.sqlite.HistoryDatabase;
import com.hefeibus.www.hefeibus.utils.Parameters;

public class App extends Application {

    private static final String TAG = "App";
    private boolean isCaching;
    private Toast toast;
    private HistoryDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        //注册百度Api
        SDKInitializer.initialize(this);
        initCacheStatus();
        database = new HistoryDatabase(this);
    }

    /**
     * @return 返回缓存开关状态
     */
    public boolean isCaching() {
        return isCaching;
    }

    /**
     * 设置缓存功能开启或关闭
     *
     * @param isCaching 开关缓存
     */
    public void setCachingStatus(boolean isCaching) {
        this.isCaching = isCaching;
        Log.d(TAG, "setCachingStatus: " + isCaching);
        if (isCaching) {
            showToast("已开启数据缓存");
        } else {
            showToast("已关闭数据缓存");
        }
        SharedPreferences preferences = getSharedPreferences(Parameters.APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Parameters.IS_CACHING, isCaching);
        if (isCaching) {
            editor.putBoolean(Parameters.CLEAR_CACHE, false);
            Log.d(TAG, "setCachingStatus: 开启缓存");
        } else {
            //如果关闭了缓存，下次启动时重新建库
            editor.putBoolean(Parameters.CLEAR_CACHE, true);
            Log.d(TAG, "setCachingStatus: 已关闭缓存，将在下次启动时重建数据库");
        }
        editor.apply();
    }

    /**
     * 初始化缓存开关
     */
    private void initCacheStatus() {
        SharedPreferences preferences = getSharedPreferences(Parameters.APP_PREFERENCES, MODE_PRIVATE);
        isCaching = preferences.getBoolean(Parameters.IS_CACHING, false);
    }

    @SuppressLint("ShowToast")
    private void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }


    public HistoryDatabase getHistoryDataBase() {
        return database;
    }
}
