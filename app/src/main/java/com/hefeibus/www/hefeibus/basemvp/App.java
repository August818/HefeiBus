package com.hefeibus.www.hefeibus.basemvp;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.hefeibus.www.hefeibus.utils.Parameters;

public class App extends Application {

    private static final String TAG = "App";
    private boolean isCaching;

    @Override
    public void onCreate() {
        super.onCreate();
        //注册百度Api
        SDKInitializer.initialize(this);
        initCacheStatus();
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
        SharedPreferences preferences = getSharedPreferences(Parameters.APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Parameters.IS_CACHING, isCaching);
        editor.apply();
    }

    /**
     * 初始化缓存开关
     */
    private void initCacheStatus() {
        SharedPreferences preferences = getSharedPreferences(Parameters.APP_PREFERENCES, MODE_PRIVATE);
        isCaching = preferences.getBoolean(Parameters.IS_CACHING, false);
    }
}
