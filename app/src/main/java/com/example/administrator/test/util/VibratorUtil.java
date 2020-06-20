package com.example.administrator.test.util;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.yibasan.lizhifm.sdk.platformtools.ApplicationContext;

/**
 * @author hefuyi
 * @created 2019/3/18
 * @describe: 振动器工具类
 **/
public class VibratorUtil {

    private static volatile VibratorUtil sVibratorUtil;
    private Vibrator mVibrator;

    private VibratorUtil() {
        initVibrator();
    }

    private void initVibrator() {
        mVibrator = (Vibrator) ApplicationContext.getContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static VibratorUtil getInstance() {
        if (sVibratorUtil == null) {
            synchronized (VibratorUtil.class) {
                if (sVibratorUtil == null) {
                    sVibratorUtil = new VibratorUtil();
                }
            }
        }
        return sVibratorUtil;
    }

    /**
     * 开启震动
     *
     * @param milliseconds 震动时间
     */
    public void startVibrator(long milliseconds) {
        if (mVibrator != null && !mVibrator.hasVibrator()) {
            return;
        }
        if (mVibrator == null) {
            initVibrator();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect vibrationEffect = VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE);
            mVibrator.vibrate(vibrationEffect);
        } else {
            mVibrator.vibrate(milliseconds);
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mVibrator != null) {
            mVibrator.cancel();
            mVibrator = null;
        }
    }
}
