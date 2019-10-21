package com.example.administrator.test.activity;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.administrator.test.R;
import com.example.administrator.test.util.LogUtil;
import com.example.administrator.test.util.ViewUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {

    private int rootViewVisibleHeight;
    private View btnFinish;
    private View viewBottomHolder;
    private EditText etContent;
    private View rootView;
    private int keyBoardHeight;
    private int sreenHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        btnFinish = view.findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomContainerDialogFragment.dismiss(ContentFragment.this);
            }
        });
        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomContainerDialogFragment.back(ContentFragment.this);
//                hideSoftInput();
            }
        });
        viewBottomHolder = view.findViewById(R.id.view_bottom_holder);
        etContent = view.findViewById(R.id.et_content);
        etContent.requestFocus();

        final ViewGroup.LayoutParams viewBottomHolderLayoutParams = viewBottomHolder.getLayoutParams();
        final View decorView = getActivity().getWindow().getDecorView();


//        btnFinish.post(new Runnable() {
//            @Override
//            public void run() {
//                Rect r = new Rect();
//                decorView.getWindowVisibleDisplayFrame(r);
//                int visibleHeight = r.height();
//                if (rootViewVisibleHeight == 0) {
//                    rootViewVisibleHeight = visibleHeight;
//                    LogUtil.d("rootViewVisibleHeightxxxx:" + rootViewVisibleHeight);
////                    return;
//                }
//            }
//        });
        Point point = new Point();
        getActivity().getWindow().getWindowManager().getDefaultDisplay().getSize(point);
        LogUtil.d("s w:"+point.toString());
        sreenHeight = point.y;
        rootViewVisibleHeight = sreenHeight;
        LogUtil.w("rH:"+rootViewVisibleHeight);
        LogUtil.w("sbH:"+ViewUtils.getStatusBarHeight(getContext()));
        LogUtil.w("topMH:"+ViewUtils.dipToPx(getContext(),20));

        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获取当前根视图在屏幕上显示的大小
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.height();
                LogUtil.d("visibleHeight:"+visibleHeight);
//                if (rootViewVisibleHeight == 0) {
//                    rootViewVisibleHeight = sreenHeight;
//                    LogUtil.d("rootViewVisibleHeightxxxx:"+rootViewVisibleHeight);
////                    return;
//                }

                //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                if (rootViewVisibleHeight == visibleHeight) {
                    LogUtil.w("---------------");
                    return;
                }

//                keyBoardHeight = Math.abs(rootViewVisibleHeight - visibleHeight);
//                //根视图显示高度变小超过200，可以看作软键盘显示了
//                if (rootViewVisibleHeight - visibleHeight > 200) {
//                    rootViewVisibleHeight = visibleHeight;
//                    adjustBottomView(keyBoardHeight);
//                    setupEditText(keyBoardHeight);
//                    LogUtil.d("show keyBoardHeight:" + keyBoardHeight);
//                    LogUtil.d("rootViewVisibleHeightaaaa:" + rootViewVisibleHeight);
//                    return;
//                }
//
//                //根视图显示高度变大超过200，可以看作软键盘隐藏了
//                if (visibleHeight - rootViewVisibleHeight > 200) {
////                    int keyBoardHeight = visibleHeight - rootViewVisibleHeight;
//                    rootViewVisibleHeight = visibleHeight;
//                    adjustBottomView(1);
//                    LogUtil.d("hide keyBoardHeight:" + keyBoardHeight);
//                    LogUtil.d("rootViewVisibleHeight:" + rootViewVisibleHeight);
//                    return;
//                }

                if (visibleHeight/(sreenHeight * 1f) > 0.8f) {
                    adjustBottomView(1);
                    LogUtil.d("hide kb");
                }else{
                    int tKeyboardHeight = rootViewVisibleHeight - visibleHeight;
                    LogUtil.d("show kb :"+tKeyboardHeight +"---"+"rootHeight:"+rootViewVisibleHeight);
                    adjustBottomView(tKeyboardHeight);
                    setupEditText(tKeyboardHeight);
                }
                rootViewVisibleHeight = visibleHeight;
            }
        });
    }

    private void setupEditText(int keyBoardHeight) {
        float top = etContent.getY();
//        int maxHeight = (int) (rootViewVisibleHeight - top - ViewUtils.dipToPx(getContext(), 60) - ViewUtils.getStatusBarHeight(getContext()));
        int maxHeight = (int) (rootView.getMeasuredHeight() - ViewUtils.dipToPx(getContext(), 60) - keyBoardHeight - top);
        etContent.setMaxHeight(maxHeight);
    }

    private void adjustBottomView(int viewHeight) {
        ViewGroup.LayoutParams layoutParams = viewBottomHolder.getLayoutParams();
        layoutParams.height = viewHeight;
        viewBottomHolder.setLayoutParams(layoutParams);
}

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_in);
        } else {
            return AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_out);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        hideSoftInput();
    }

    private void hideSoftInput() {
        View v = getActivity().getCurrentFocus();
        if (v != null && v.getWindowToken() != null) {
            InputMethodManager manager = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isOpen = manager.isActive();
            if (isOpen) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }
}
