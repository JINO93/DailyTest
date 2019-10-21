package com.example.administrator.test.view;

import android.graphics.Rect;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.test.R;
import com.example.administrator.test.util.LogUtil;

/**
 * @Author: JINO
 * @Description: 容器下滑取消手势处理
 * @Date: Create in 20:44 2019/9/16
 */
public class BottomContainerSheetBehavior extends BottomSheetBehavior {

    private Rect mChangeImageBackgroundRect;

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent event) {
//        LogUtil.d("onInterceptTouchEvent :" + child.getId());
        ViewGroup fragmentContainer = child.findViewById(R.id.fragment_container);
        boolean canIntercept = false;
        if (fragmentContainer.getChildCount() > 0) {
            View view = fragmentContainer.getChildAt(0);
            canIntercept = isInViewZone(view, (int) event.getRawX(), (int) event.getRawY());
        }
        return super.onInterceptTouchEvent(parent, child, event) && canIntercept;
    }

    /**
     * 判断坐标是否在view上
     *
     * @param view 目标view
     * @param x    坐标x
     * @param y    坐标y
     * @return true：坐标在view上  false：坐标不在view上
     */
    private boolean isInViewZone(View view, int x, int y) {
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
