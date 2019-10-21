package com.example.administrator.test.util;

import android.util.Log;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 19:52 2019/9/11
 */
public class LogUtil {

    private static final String TAG = "JINO";

    public static void d(String content){
        Log.d(TAG, content);
    }

    public static void w(String content) {
        Log.w(TAG, content);
    }
}
