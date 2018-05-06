package com.hefeibus.www.hefeibus.basemvp;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //注册百度Api
        SDKInitializer.initialize(this);
    }
}
