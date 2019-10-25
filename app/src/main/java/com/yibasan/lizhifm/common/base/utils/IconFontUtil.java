package com.yibasan.lizhifm.common.base.utils;

import android.graphics.Typeface;

import com.example.administrator.test.MyApplication;

/**
 * Created by Administrator on 2017/11/24.
 */

public class IconFontUtil {
    private static Typeface mIconfont;
    private static Typeface mIconfontV1;

    public static Typeface getIconfont() {
        if (mIconfont == null) {
            mIconfont = Typeface
                    .createFromAsset(MyApplication.getContext().getAssets(), "iconfont/lizhifm.ttf");
        }
        return mIconfont;
    }

    public static Typeface getIconfontV1() {
        if (mIconfontV1 == null) {
            mIconfontV1 = Typeface
                    .createFromAsset(MyApplication.getContext().getAssets(), "iconfont/lizhi.ttf");
        }
        return mIconfontV1;
    }
}
