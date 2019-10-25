package com.example.administrator.test.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 18:15 2019/10/21
 */
public class CardIndicator2 extends LinearLayout {

    private int selectedDrawableRes;
    private int unSelectedDrawableRes;
    private int maxShowDot;
    private boolean nextPageScale;

    private int currentIndex;

    public CardIndicator2(Context context) {
        this(context, null);
    }

    public CardIndicator2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardIndicator2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
