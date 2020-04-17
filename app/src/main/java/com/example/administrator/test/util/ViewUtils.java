package com.example.administrator.test.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 20:21 2019/9/26
 */
public class ViewUtils {

    private static int mDisplayHeight;

    private static int mDisplayWidth;

    public static int dipToPx(Context context, float dip) {
        if (context != null) {
            return roundUp(dip * context.getResources().getDisplayMetrics().density);
        }
        return 0;
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
        }
        return 0;
    }

    public static int getDisplayHeight(Context context) {
        if (mDisplayHeight <= 0) {
            mDisplayHeight = context.getResources().getDisplayMetrics().heightPixels;
        }
        return mDisplayHeight;
    }

    public static int getDisplayWeight(Context context) {
        if (mDisplayWidth <= 0) {
            mDisplayWidth = context.getResources().getDisplayMetrics().widthPixels;
        }
        return mDisplayWidth;
    }




    public static int roundUp(float f) {
        return (int) (0.5f + f);
    }

    /**
     * 判断是否存在虚拟按键
     *
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context mContex) {
        boolean hasNavigationBar = false;
        try {
            Resources rs = mContex.getResources();
            int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id);
            }
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            Log.d("ViewUtils", "checkDeviceHasNavigationBar crash" + e.toString());
        }
        return hasNavigationBar;
    }

    public static int getNavBarHeight(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    public static boolean isNavBarVisible(@NonNull final Window window,Context context) {
        boolean isVisible = false;
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        for (int i = 0, count = decorView.getChildCount(); i < count; i++) {
            final View child = decorView.getChildAt(i);
            final int id = child.getId();
            if (id != View.NO_ID) {
                String resourceEntryName = context
                        .getResources()
                        .getResourceEntryName(id);
                if ("navigationBarBackground".equals(resourceEntryName)
                        && child.getVisibility() == View.VISIBLE) {
                    isVisible = true;
                    break;
                }
            }
        }
        if (isVisible) {
            int visibility = decorView.getSystemUiVisibility();
            isVisible = (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
        }
        return isVisible;
    }
}
