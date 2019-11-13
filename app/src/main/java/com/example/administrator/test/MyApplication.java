package com.example.administrator.test;

import android.app.Application;
import android.content.Context;

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
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

}
