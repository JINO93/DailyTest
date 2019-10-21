package com.example.administrator.test.activity;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.test.R;
import com.example.administrator.test.view.BottomContainerSheetBehavior;


/**
 * 用于可多级跳转的容器弹窗
 */
public class BottomContainerDialogFragment2 extends BottomSheetDialogFragment {


    private int mTopOffset = 0;
    private boolean mCanTouchOutsideCancel;
    private boolean mCanSlideCancel = true;
    private Fragment mRootFragment;
    private int backgroundRes;
    private View rootView;

    public static BottomContainerDialogFragment newInstance(Fragment rootFragment) {
        return newInstance(0, false, false, rootFragment);
    }

    /**
     * 创建实例
     *
     * @param topOffset             容器距离顶部的距离（容器默认是全屏高度）
     * @param canTouchOutsideCancel 是否可以点击外部取消
     * @param canSlideCancel        是否可以滑动取消
     * @param rootFragment          根Fragment（既第一个要展示的Fragment）
     * @return
     */
    public static BottomContainerDialogFragment newInstance(int topOffset, boolean canTouchOutsideCancel, boolean canSlideCancel, Fragment rootFragment) {
        BottomContainerDialogFragment fragment = new BottomContainerDialogFragment();
        fragment.setTopOffset(topOffset);
        fragment.setCanTouchOutsideCancel(canTouchOutsideCancel);
        fragment.setCanSlideCancel(canSlideCancel);
        fragment.setRootFragment(rootFragment);
        return fragment;
    }

    /**
     * 跳转到别的fragment
     *
     * @param currentFragment 当前fragment
     * @param fragment        目标跳转fragment
     */
    public static void add(Fragment currentFragment, Fragment fragment) {
        FragmentManager fragmentManager = currentFragment.getFragmentManager();
        if (fragmentManager == null || fragment == null) {
            return;
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.fragment_container, fragment).hide(currentFragment);
        fragmentTransaction.commit();
    }


    /**
     * 回退上一级，如果没有上一级则关闭容器
     *
     * @param fragment 当前fragment
     */
    public static void back(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        FragmentManager fragmentManager = fragment.getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            Fragment parentFragment = fragment.getParentFragment();
            if (parentFragment instanceof BottomSheetDialogFragment) {
                ((BottomSheetDialogFragment) parentFragment).dismiss();
            }
        }
    }

    /**
     * 关闭弹窗
     *
     * @param fragment 当前fragment
     */
    public static void dismiss(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        Fragment parentFragment = fragment.getParentFragment();
        if (parentFragment instanceof BottomSheetDialogFragment) {
            ((BottomSheetDialogFragment) parentFragment).dismiss();
        }
    }

    public void setTopOffset(int topOffset) {
        this.mTopOffset = topOffset;
    }

    public void setCanTouchOutsideCancel(boolean canTouchOutsideCancel) {
        this.mCanTouchOutsideCancel = canTouchOutsideCancel;
    }

    public void setCanSlideCancel(boolean canSlideCancel) {
        this.mCanSlideCancel = canSlideCancel;
    }

    public void setRootFragment(Fragment rootFragment) {
        this.mRootFragment = rootFragment;
    }

    public void setContainerBackgroundRes(int res) {
        this.backgroundRes = res;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_bottom, container, false);

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mCanTouchOutsideCancel) {
//                    getDialog().dismiss();
//                }
//            }
//        });
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, mRootFragment).commit();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (backgroundRes != 0) {
            rootView.setBackgroundResource(backgroundRes);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomDialog(getContext(), R.style.BottomDialogTheme);
    }

    private int getHeight() {
        int height = 1920;
        if (getContext() != null) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Point point = new Point();
            if (wm != null) {
                // 使用Point已经减去了状态栏高度
                wm.getDefaultDisplay().getSize(point);
                height = point.y - getTopOffset();
            }
        }
        return height;
    }

    private int getTopOffset() {
        return mTopOffset;
    }

    private class BottomDialog extends BottomSheetDialog {

        private BottomSheetBehavior<View> mBottomSheetBehavior;
        private Window mWindow;
        private boolean isSlideDismiss;

        public BottomDialog(@NonNull Context context) {
            super(context);
        }

        public BottomDialog(@NonNull Context context, int theme) {
            super(context, theme);
            mWindow = getWindow();
        }

        @Override
        protected void onStart() {
            super.onStart();
            init();
        }

        @Override
        public void dismiss() {
            if (isSlideDismiss || getChildFragmentManager().getBackStackEntryCount() <= 0) {
                if (isSlideDismiss) {
                    isSlideDismiss = false;
                }
                super.dismiss();
            } else {
                getChildFragmentManager().popBackStack();
            }
        }

        private void init() {
            View bottomSheet = mWindow.findViewById(android.support.design.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                //设置容器高度
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
                layoutParams.setBehavior(new BottomContainerSheetBehavior());
                layoutParams.height = getHeight();
                //设置behavior
                mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                mBottomSheetBehavior.setPeekHeight(getHeight());
                mBottomSheetBehavior.setHideable(mCanSlideCancel); //设置可否滑动消失
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                mBottomSheetBehavior.setSkipCollapsed(false);
                mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                            isSlideDismiss = true;
                            dismiss();
                        }
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                    }
                });
            }
        }
    }
}
