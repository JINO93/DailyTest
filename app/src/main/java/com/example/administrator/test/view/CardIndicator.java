package com.example.administrator.test.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.test.util.LogUtil;
import com.example.administrator.test.view.layoutManager.CardStackLayoutManager;

import java.text.MessageFormat;

public class CardIndicator extends View {

    private int dotCount = 5;

    private int dotShowCount = 4;
    private int normalDotRadius = 20;
    private int smallDotRadius = 10;

    private int selectDotIndex = 0;
    private int startIndex;
    private int endIndex = startIndex + dotShowCount - 1;

    private int dotMargin = 10;
    private Paint dotPaint;
    private ValueAnimator valueAnimator;
    private float animatedValue = 0f;
    private boolean slideRight;
    private RecyclerView mRecyclerView;
    private int drawDotCount;

    public CardIndicator(Context context) {
        this(context, null);
    }

    public CardIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void updateDotIndex(boolean isSlideRight) {
        if (isSlideRight) {
            if (dotCount - 1 > selectDotIndex) {
                selectDotIndex++;
            }
            if (endIndex < dotCount - 1) {
                endIndex++;
                startIndex++;
            }
        } else {
            if (0 < selectDotIndex) {
                selectDotIndex--;
            }
            if (startIndex > 0) {
                endIndex--;
                startIndex--;
            }
        }
        LogUtil.d(MessageFormat.format("updateDotIndex  startI:{0}  endI:{1}  selectI:{2}",startIndex,endIndex,selectDotIndex));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int witdh = 2 * normalDotRadius * drawDotCount + dotMargin * (drawDotCount - 1);
        int height = 2 * normalDotRadius;
        setMeasuredDimension(witdh, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = -1; i <= drawDotCount; i++) {
            dotPaint.setColor(i == selectDotIndex - startIndex ? Color.RED : Color.BLACK);
            int x = normalDotRadius + i * (2 * normalDotRadius + dotMargin) + (int) (animatedValue * (2 * normalDotRadius + dotMargin));
            int y = normalDotRadius;
            canvas.drawCircle(x, y, getDotRadius(i), dotPaint);
        }
    }

    private float getDotRadius(int index) {
        if (dotShowCount >= dotCount) {
            return normalDotRadius;
        }
//        if (index != 0 || index < endIndex-1) {
//            return normalDotRadius;
//        }
        //当不处于滑动动画时，根据显示点的开始结束下标判断点的半径
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            if ((index == 0 && startIndex > 0) || (index == dotShowCount - 1 && endIndex + 1 < dotCount)) {
                return smallDotRadius;
            }
        } else {
            if (animatedValue < 0) {
                if (index <= 1 || (endIndex + 1 < dotCount && index == dotShowCount)) {
                    return smallDotRadius;
                }
            } else {
                if (index >= dotShowCount - 2 || (startIndex > 0 && index == -1)) {
                    return smallDotRadius;
                }
            }
        }
        return normalDotRadius;
    }

    /**
     * 选中的点左右滑动
     *
     * @param slideRight true : 向右滑动，false ：向左滑动
     */
    public void slide(boolean slideRight) {
        LogUtil.e("slide:" + slideRight);
        if (slideRight) {
            //当选中的点不在右边倒数第二个时，直接向右移动
            if (endIndex - 1 > selectDotIndex) {
                updateSelectedDotIndex(true);
                invalidate();
            } else {
                //当选中的点右边是最后一个点，直接向右移动
                if (endIndex + 1 == dotCount) {
                    updateSelectedDotIndex(true);
                    invalidate();
                } else {
                    //播放右移动画
                    startSlideAnim(slideRight);
                }
            }
        } else {
            if (startIndex + 1 < selectDotIndex) {
                updateSelectedDotIndex(false);
                invalidate();
            } else {
                if (startIndex == 0) {
                    updateSelectedDotIndex(false);
                    invalidate();
                } else {
                    startSlideAnim(slideRight);
                }
            }
        }
    }

    private void startSlideAnim(boolean slideRight){
        LogUtil.e("startSlideAnim");
        if (valueAnimator != null) {
            if (valueAnimator.isRunning()) {
                LogUtil.d("anim is running");
                valueAnimator.removeAllListeners();
                valueAnimator.cancel();
                updateDotIndex(slideRight);
            }
            valueAnimator = null;
        }
        this.slideRight = slideRight;
        valueAnimator = ValueAnimator.ofFloat(1).setDuration(200);
        if (slideRight) {
            valueAnimator.setFloatValues(0, -1);
        }else{
            valueAnimator.setFloatValues(0, 1);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                LogUtil.w("onEnd");
                valueAnimator = null;
                animatedValue = 0f;
                updateDotIndex(slideRight);
                invalidate();
            }
        });
        valueAnimator.start();
    }

    /**
     * 更新选中点的下标
     *
     * @param increace true:增加  false：减少
     */
    void updateSelectedDotIndex(boolean increace) {
        if (increace && selectDotIndex < dotCount - 1) {
            selectDotIndex++;
        }
        if (!increace && selectDotIndex > 0) {
            selectDotIndex--;
        }
    }

    public void attachToRecyelerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        updateDotCount();
//        mRecyclerView.addOnScrollListener(new InnerRecyclerViewScrollListener());
        mRecyclerView.getAdapter().registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                updateDotCount();
            }
        });
    }

    public void updateDotCount(){
        dotCount = mRecyclerView.getAdapter().getItemCount();
        if(dotCount < dotShowCount){
            drawDotCount = dotCount;
            endIndex = startIndex + drawDotCount - 1;
            requestLayout();
            return;
        }
        drawDotCount = dotShowCount;
        endIndex = startIndex + drawDotCount - 1;
        requestLayout();
    }

    private class InnerRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

        private int lastSelectPos = 0;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
//            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//            if (layoutManager == null) {
//                return;
//            }
//            View topView = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
//            int selectPos = recyclerView.getChildAdapterPosition(topView);
//            if(layoutManager instanceof CardStackLayoutManager){
//                selectPos = ((CardStackLayoutManager) layoutManager).getTopItemPosition();
//            }
//            if (selectPos == lastSelectPos) {
//                return;
//            }
//            slide(selectPos - lastSelectPos > 0);
//            lastSelectPos = selectPos;
//            invalidate();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                return;
            }
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager == null) {
                return;
            }
            View topView = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
            int selectPos = recyclerView.getChildAdapterPosition(topView);
            if(layoutManager instanceof CardStackLayoutManager){
                selectPos = ((CardStackLayoutManager) layoutManager).getTopItemPosition();
            }
            LogUtil.d(MessageFormat.format("topView pos:{0},count:{1}", selectPos,dotCount));
            if (selectPos == lastSelectPos) {
                return;
            }
            slide(selectPos - lastSelectPos > 0);
            lastSelectPos = selectPos;
            invalidate();
        }
    }
}
