package com.example.administrator.test.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 19:25 2019/9/16
 */
public class BottomContainerFrameLayout extends FrameLayout {
    private Rect mChangeImageBackgroundRect;

    public BottomContainerFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public BottomContainerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomContainerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isInChangeImageZone(this, (int) ev.getRawX(), (int) ev.getRawY());
//        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return true;
    }

    private boolean isInChangeImageZone(View view, int x, int y) {

        if (null == mChangeImageBackgroundRect) {
            mChangeImageBackgroundRect = new Rect();
        }
        view.getDrawingRect(mChangeImageBackgroundRect);

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        mChangeImageBackgroundRect.left = location[0];
        mChangeImageBackgroundRect.top = location[1];
        mChangeImageBackgroundRect.right = mChangeImageBackgroundRect.right + location[0];
        mChangeImageBackgroundRect.bottom = mChangeImageBackgroundRect.bottom + location[1];

        return mChangeImageBackgroundRect.contains(x, y);

    }
}
