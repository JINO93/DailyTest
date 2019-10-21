package com.example.administrator.test.view.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.example.administrator.test.R;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 15:19 2019/8/12
 */
public class BuyVipSuccessDialog extends Dialog {

    private View viewAvatar;
    private ImageView bgStarIv;
    private View rootLayout;
    private View topHeartIv;
    private View topBgIv;
    private View rightAvatarIv;
    private View sideHeartIv;
    private View titleTv;
    private View layoutContent;

    private AnimatorSet showAnim;
    private int avatarLayoutWidth;

    public BuyVipSuccessDialog(@NonNull Context context) {
        super(context, R.style.TransparentDialog);
        configWindow(context);
        setContentView(R.layout.dialog_buy_vip_success);
        init();
    }

    private void init() {
        viewAvatar = findViewById(R.id.layout_avatar);
        bgStarIv = findViewById(R.id.iv_bg_star);
        rootLayout = findViewById(R.id.layout_root);
        topHeartIv = findViewById(R.id.iv_top_heart);
        sideHeartIv = findViewById(R.id.iv_side_heart);
        topBgIv = findViewById(R.id.iv_top_bg);
        rightAvatarIv = findViewById(R.id.iv_avatar_right);
        titleTv = findViewById(R.id.tv_title);
        layoutContent = findViewById(R.id.layout_text_content);

        //背景颜色动画
        ObjectAnimator bgColorAnim = ObjectAnimator.ofFloat(rootLayout, View.ALPHA, 0, 0.94f)
                .setDuration(1000);

        //顶部颜色动画
        ObjectAnimator topBgAnim = ObjectAnimator.ofFloat(topBgIv, View.TRANSLATION_Y, -300, 0)
                .setDuration(600);

        //背景星星动画
        ObjectAnimator bgStarAnim = ObjectAnimator.ofPropertyValuesHolder(bgStarIv,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 2, 1.2f, 1.4f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 2, 1.2f, 1.4f),
                PropertyValuesHolder.ofFloat(View.ALPHA, 0, 1)
        );
        bgStarAnim.setInterpolator(new OvershootInterpolator());
        bgStarAnim.setStartDelay(400);
        bgStarAnim.setDuration(400);

        //头像动画
        final ValueAnimator avatarAnim = ValueAnimator.ofFloat(1.5f, 0.6f, 1.1f,1/*,0.9f,1*/);
        avatarAnim.setInterpolator(new LinearInterpolator());
        avatarAnim.setDuration(400);
        avatarAnim.setStartDelay(400);
        avatarAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams layoutParams = viewAvatar.getLayoutParams();
                layoutParams.width = (int) (avatarLayoutWidth * (float) animation.getAnimatedValue());
                Log.i("JINO", "width:" + layoutParams.width);
                viewAvatar.requestLayout();
            }
        });
        avatarAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                viewAvatar.setVisibility(View.VISIBLE);
                avatarLayoutWidth = viewAvatar.getLayoutParams().width;
            }
        });

        //顶上心形
        final ObjectAnimator topHeartAnim = ObjectAnimator.ofPropertyValuesHolder(topHeartIv,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0, 1),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0, 1),
                PropertyValuesHolder.ofFloat(View.ALPHA, 0, 1)
        );
        topHeartAnim.setDuration(400);
        topHeartAnim.setInterpolator(new OvershootInterpolator());
        topHeartAnim.setStartDelay(600);
        topHeartAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                topHeartIv.setVisibility(View.VISIBLE);
            }
        });
        final ObjectAnimator titleShowAnim = ObjectAnimator.ofPropertyValuesHolder(titleTv,
                PropertyValuesHolder.ofFloat(View.ALPHA, 0, 1)
        );
        titleShowAnim.setDuration(400);
        titleShowAnim.setInterpolator(new OvershootInterpolator());
        titleShowAnim.setStartDelay(600);
        titleShowAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                titleTv.setVisibility(View.VISIBLE);
            }
        });
        AnimatorSet stepOneAnim = new AnimatorSet();
        stepOneAnim.playTogether(bgColorAnim, topBgAnim, bgStarAnim, avatarAnim, topHeartAnim, titleShowAnim);

        //---------------------------------------

        //背景星星第二步动画
        ObjectAnimator bgStarStepTwoAnim = ObjectAnimator.ofPropertyValuesHolder(bgStarIv,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1.4f, 1),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.4f, 1)
        );
        final ValueAnimator avatarStepTwoAnim = ValueAnimator.ofFloat(1, 0.75f);
        avatarStepTwoAnim.setDuration(200);
        avatarStepTwoAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams layoutParams = viewAvatar.getLayoutParams();
                layoutParams.width = (int) (avatarLayoutWidth * (float) animation.getAnimatedValue());
                Log.i("JINO", "width:" + layoutParams.width);
                viewAvatar.requestLayout();
            }
        });
        //右边头像缩小动画
        ObjectAnimator rightAvatarAnim = ObjectAnimator.ofPropertyValuesHolder(rightAvatarIv,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 0.5f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 0.5f)
        );
        rightAvatarAnim.setDuration(200);
        rightAvatarAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                rightAvatarIv.setPivotX(rightAvatarIv.getWidth());
                rightAvatarIv.setPivotY(rightAvatarIv.getHeight());
            }
        });
        //三颗心消失动画
        ObjectAnimator topHeartDisappear = ObjectAnimator.ofFloat(topHeartIv, View.ALPHA, 1, 0).setDuration(100);
        //两颗心出现动画
        ObjectAnimator sideHeartAppear = ObjectAnimator.ofFloat(sideHeartIv, View.ALPHA, 0, 1).setDuration(200);
        sideHeartAppear.setStartDelay(100);
        sideHeartAppear.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                sideHeartIv.setVisibility(View.VISIBLE);
            }
        });
        //显示文字内容
        ObjectAnimator contentTextAppear = ObjectAnimator.ofFloat(layoutContent, View.ALPHA, 0, 1).setDuration(300);
        contentTextAppear.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                layoutContent.setVisibility(View.VISIBLE);
            }
        });
        AnimatorSet stepTwoAnim = new AnimatorSet();
        stepTwoAnim.playTogether(bgStarStepTwoAnim, avatarStepTwoAnim, rightAvatarAnim,
                topHeartDisappear, sideHeartAppear, contentTextAppear);


        showAnim = new AnimatorSet();
        showAnim.playSequentially(stepOneAnim, stepTwoAnim);
        showAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                rootLayout.setAlpha(0f);
                viewAvatar.setVisibility(View.INVISIBLE);
                topHeartIv.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void configWindow(Context context) {
        Window window = this.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void show() {
        super.show();
        showAnim.start();
    }
}
