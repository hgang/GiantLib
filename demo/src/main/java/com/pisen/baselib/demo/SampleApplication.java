package com.pisen.baselib.demo;

import android.app.Application;

import com.android.volley.manager.RequestManager;

/**
 * @author hegang
 * @version 1.0, 2016/2/18 13:34
 */
public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化volley
        RequestManager.getInstance().init(this);
    }
}
