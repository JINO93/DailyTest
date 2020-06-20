package com.example.administrator.test;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.yibasan.lizhifm.sdk.platformtools.ApplicationContext;

import io.flutter.app.FlutterApplication;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 15:34 2019/10/21
 */
public class MyApplication extends FlutterApplication {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        ApplicationContext.init(this);
    }

}
