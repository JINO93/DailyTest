package com.example.administrator.test.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.test.R;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 10:25 2019/7/27
 */
public class AnimatedBgView extends FrameLayout implements View.OnClickListener {

    private static final String TAG = "JINO";
    private Paint bgPaint;
    private LinearGradient linearGradient;
    private int originColor = Color.parseColor("#f5a623");
    private int changeColor = Color.parseColor("#f54323");


    private int[] colors = {
//            Color.parseColor("#fea100"),
            Color.parseColor("#f54323"),
            Color.parseColor("#f52388"),
            Color.parseColor("#f12df5"),
            Color.parseColor("#f52388"),
            Color.parseColor("#f53748"),
            Color.parseColor("#f54323"),
//            Color.parseColor("#fea100")
    };
    //    private int[] colors = {Color.RED, Color.GREEN,Color.BLUE};

    private RectF bgRect;
    private int currentColorIndex;
    private int startPos, endPos;

    TextView tvCoin;
    View vLine;
    ImageView ivRank;
    ViewGroup vRightIcon;
    private AnimatorSet btnAnim;
    private int startWidth;
    private int changeColorTime = 1;

    public AnimatedBgView(Context context) {
        this(context, null);
    }

    public AnimatedBgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatedBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.voice_main_player_buy_button_view, this);
        tvCoin = findViewById(R.id.tv_coin);
        vLine = findViewById(R.id.v_line);
        ivRank = findViewById(R.id.iv_rank);
        vRightIcon = findViewById(R.id.layout_right_icon);

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(originColor);
        setOnClickListener(this);

        initAnim();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        linearGradient = new LinearGradient(0f, getHeight()/2f, getWidth(), getHeight()/2f, colors, null, Shader.TileMode.CLAMP);
//        bgPaint.setShader(linearGradient);
        bgRect = new RectF(0f, 0f, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        bgPaint.setShader(new LinearGradient(0f, getHeight()/2, getWidth(), getHeight()/2, colors, null, Shader.TileMode.CLAMP));
        canvas.drawRoundRect(bgRect, dipToPx(25f), dipToPx(25f), bgPaint);
    }


    @Override
    public void onClick(View v) {
        currentColorIndex = 0;
//        Log.w(TAG, "v:" + vLine.getVisibility());
//        Log.d(TAG, "onClick: ");
//        vLine.setVisibility(GONE);
//        ivRank.setVisibility(GONE);
//        setVisibility(GONE);
        startBgAnim();
//        Log.w(TAG, "v f:" + vLine.getVisibility());
    }

    private void startBgAnim() {
        btnAnim.start();
    }

    private void initAnim() {
        //皇冠消失
        ValueAnimator disppaerAnim = ValueAnimator.ofFloat(1f, 0)
                .setDuration(300);
        disppaerAnim
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        vLine.setAlpha((Float) animation.getAnimatedValue());
                        ivRank.setAlpha((Float) animation.getAnimatedValue());
                    }
                });
        disppaerAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                vLine.setVisibility(GONE);
                ivRank.setVisibility(GONE);
                vRightIcon.setVisibility(GONE);
                requestLayout();
            }
        });
        //背景渐变
        ValueAnimator colorAnim = ValueAnimator.ofFloat(2);
        colorAnim.setRepeatCount(colors.length - 2);
        colorAnim.setDuration(450)
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float animatedValue = (float) animation.getAnimatedValue();
                        linearGradient = new LinearGradient(0f, getHeight() / 2f, getWidth(), getHeight() / 2f,
                                new int[]{colors[currentColorIndex + 1], colors[currentColorIndex]}, new float[]{animatedValue < 1 ? 0 : (animatedValue - 1f),
                                animatedValue < 1 ? animatedValue : 1}, Shader.TileMode.CLAMP);
                        bgPaint.setShader(linearGradient);
//                        bgPaint.setColor(colors[currentColorIndex]);
                        invalidate();
                    }
                });
        colorAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                currentColorIndex++;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                currentColorIndex = 0;
                if (changeColorTime == 1) {
                    colors[0] = originColor;
                    colors[colors.length - 1] = changeColor;
                }else if(changeColorTime == 4){
                    colors[0] = changeColor;
                    colors[colors.length - 1] = originColor;
                }else{
                    colors[0] = changeColor;
                    colors[colors.length - 1] = changeColor;
                }
                changeColorTime++;
                Log.i(TAG, "start:" + SystemClock.currentThreadTimeMillis());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.i(TAG, "end:" + SystemClock.currentThreadTimeMillis());
            }
        });
        //皇冠出现
        ValueAnimator appaerAnim = ValueAnimator.ofFloat(1f)
                .setDuration(300);
        appaerAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    private int curWidth = 0;

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Float animatedValue = (Float) animation.getAnimatedValue();
//                        if (animatedValue == 0f) {
//                            curWidth = getMeasuredWidth();
//                        }
                        ViewGroup.LayoutParams layoutParams = vRightIcon.getLayoutParams();
                        layoutParams.width = (int) (startWidth * animatedValue);
                        Log.w(TAG, "width:" + layoutParams.width);
                        vRightIcon.setLayoutParams(layoutParams);
//                        vRightIcon.setScaleX(animatedValue);
                        vLine.setAlpha(animatedValue);
                        ivRank.setAlpha(animatedValue);
                        requestLayout();
                    }
                });
        appaerAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                vLine.setVisibility(VISIBLE);
                ivRank.setVisibility(VISIBLE);
                vRightIcon.setVisibility(VISIBLE);
//                vRightIcon.setPivotX(vRightIcon.getWidth());
                tvCoin.setText("再次赠送");
                bgPaint.setShader(null);
                requestLayout();
            }
        });

        btnAnim = new AnimatorSet();
        btnAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                startWidth = vRightIcon.getWidth();
                changeColorTime = 1;
                Log.w(TAG, "v width:" + startWidth);
            }

//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                ViewGroup.LayoutParams layoutParams = getLayoutParams();
//                layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//                setLayoutParams(layoutParams);
//                requestLayout();
//            }
        });
        btnAnim.playSequentially(disppaerAnim, colorAnim, colorAnim.clone(), colorAnim.clone(),colorAnim.clone(), appaerAnim);
    }

    public int dipToPx(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }
}
