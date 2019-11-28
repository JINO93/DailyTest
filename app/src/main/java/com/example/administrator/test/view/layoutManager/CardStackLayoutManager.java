package com.example.administrator.test.view.layoutManager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Keep;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by 钉某人
 * github: https://github.com/DingMouRen
 * email: naildingmouren@gmail.com
 */
@Keep
public class CardStackLayoutManager extends RecyclerView.LayoutManager {
    private boolean mHasChild = false;
    private int mItemViewWidth;
    private int mItemViewHeight;
    private int mScrollOffset = Integer.MAX_VALUE;
    private final float mScale;
    private int mItemMargin;
    private int mItemCount;
//    private final SkidRightSnapHelper mSkidRightSnapHelper;
    private int mItemStackCount = 3;

    private int animateValue;
    private int mDuration = 300;
    private int lastAnimateValue;
    private VelocityTracker mVelocityTracker;
    private ObjectAnimator animator;
    private int mMinVelocityX;
    private RecyclerView.Recycler recycler;
    private Method sSetScrollState;
    private RecyclerView mRV;
    private int lastTopItemPos = -1;
    private OnStackChangeListener mListener;

    /**
     * Sets true to scroll layout in left direction.
     */
    public boolean isReverseDirection = true;

    public CardStackLayoutManager(float scale, boolean isRight2Left, int itemMargin, int itemStackCount) {
        mItemStackCount = itemStackCount;
        isReverseDirection = isRight2Left;
        this.mScale = scale;
        mItemMargin = itemMargin;
//        mSkidRightSnapHelper = new SkidRightSnapHelper();
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        mRV = view;
        super.onAttachedToWindow(view);
        mMinVelocityX = ViewConfiguration.get(view.getContext()).getScaledMinimumFlingVelocity();
//        mSkidRightSnapHelper.attachToRecyclerView(view);
        view.setOnTouchListener(mTouchListener);
        view.setOnFlingListener(mOnFlingListener);
    }

    int pointerId = 0;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
//            if (event.getAction() == MotionEvent.ACTION_POINTER_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_UP) {
//                return true;
//            }
            mVelocityTracker.addMovement(event);
            if (event.getAction() == MotionEvent.ACTION_DOWN /*|| event.getAction() == MotionEvent.ACTION_POINTER_DOWN*/) {
                if (animator != null && animator.isRunning())
                    animator.cancel();
                pointerId = event.getPointerId(event.getActionIndex());
            }else if (event.getAction() == MotionEvent.ACTION_UP /*|| event.getAction() == MotionEvent.ACTION_POINTER_UP*/) {
                if (v.isPressed()) v.performClick();
                mVelocityTracker.computeCurrentVelocity(1000, 14000);
                float xVelocity = mVelocityTracker.getXVelocity(pointerId);
                int o = isReverseDirection ?
                        ((mItemCount - 1) * mItemViewWidth + mScrollOffset) % mItemViewWidth :
                        mScrollOffset % mItemViewWidth;
                int scrollX;
                if (Math.abs(xVelocity) < mMinVelocityX && o != 0) {
                    if (xVelocity > 0)
                        scrollX = mItemViewWidth - o;
                    else scrollX = -o;
                    int dur = (int) (Math.abs((scrollX + 0f) / mItemViewWidth) * mDuration);
                    brewAndStartAnimator(dur, scrollX);
                }
            }
            return false;
        }

    };

    private RecyclerView.OnFlingListener mOnFlingListener = new RecyclerView.OnFlingListener() {
        @Override
        public boolean onFling(int velocityX, int velocityY) {
            int o = isReverseDirection ?
                    ((mItemCount - 1) * mItemViewWidth + mScrollOffset) % mItemViewWidth :
                    mScrollOffset % mItemViewWidth;
            int s = mItemViewWidth - o;
            int scrollX;
            int vel = absMax(velocityX, velocityY);
            if (vel < 0) {
                scrollX = s;
                if(o == 0){
                    scrollX = 0;
                }
            } else
                scrollX = -o;
            int dur = computeSettleDuration(Math.abs(scrollX), Math.abs(vel));
            brewAndStartAnimator(dur, scrollX);
            setScrollStateIdle();
            return true;
        }
    };

    /**
     * we need to set scrollstate to {@link RecyclerView#SCROLL_STATE_IDLE} idle
     * stop RV from intercepting the touch event which block the item click
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setScrollStateIdle() {
        try {
            if (sSetScrollState == null)
                sSetScrollState = RecyclerView.class.getDeclaredMethod("setScrollState", int.class);
            sSetScrollState.setAccessible(true);
            sSetScrollState.invoke(mRV, RecyclerView.SCROLL_STATE_IDLE);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private int absMax(int a, int b) {
        if (Math.abs(a) > Math.abs(b))
            return a;
        else return b;
    }

    private int computeSettleDuration(int distance, float xvel) {
        float sWeight = 0.5f * distance / mItemViewWidth;
        float velWeight = xvel > 0 ? 0.5f * mMinVelocityX / xvel : 0;

        return (int) ((sWeight + velWeight) * mDuration);
    }

    private void brewAndStartAnimator(int dur, int finalXorY) {
        animator = ObjectAnimator.ofInt(CardStackLayoutManager.this, "animateValue", 0, finalXorY);
        animator.setDuration(dur);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                lastAnimateValue = 0;
//                LogUtil.w("on Anim end.");
                mRV.onScrollStateChanged(RecyclerView.SCROLL_STATE_IDLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                lastAnimateValue = 0;
            }
        });
    }

    public void setAnimateValue(int animateValue) {
        this.animateValue = animateValue;
        int dy = this.animateValue - lastAnimateValue;
        mScrollOffset = mScrollOffset + dy;
        fill(recycler);
        lastAnimateValue = animateValue;
    }

    public int getAnimateValue() {
        return animateValue;
    }

    public int getFixedScrollPosition(int direction, float fixValue) {
        if (mHasChild) {
            if (mScrollOffset % mItemViewWidth == 0) {
                return RecyclerView.NO_POSITION;
            }
            float itemPosition = position();
            int layoutPosition = (int) (direction > 0 ? itemPosition + fixValue : itemPosition + (1 - fixValue)) - 1;
            return convert2AdapterPosition(layoutPosition);
        }
        return RecyclerView.NO_POSITION;
    }

    public int getTopItemPosition(){
//        LogUtil.w("position:"+position());
        return (int) (mItemCount - Math.floor(position()));
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.recycler = recycler;
        if (state.getItemCount() == 0 || state.isPreLayout()) return;
        //如果调用removeAndRecycleAllViews会导致item不断调用onBindViewHolder的问题
        detachAndScrapAttachedViews(recycler);
        if (!mHasChild) {
//            mItemViewHeight = getVerticalSpace();
//            mItemViewWidth = (int) (mItemViewHeight / mItemHeightWidthRatio);
            View view = recycler.getViewForPosition(0);
            measureChildWithMargins(view,0,0);
            mItemViewWidth = view.getMeasuredWidth();
            mItemViewHeight = view.getMeasuredHeight();
            mHasChild = true;
        }
        mItemCount = getItemCount();
        mScrollOffset = makeScrollOffsetWithinRange(mScrollOffset);
        fill(recycler);
    }

    private float position() {
        return isReverseDirection ?
                (mScrollOffset + mItemCount * mItemViewWidth) * 1.0F/ mItemViewWidth :
                mScrollOffset * 1.0F / mItemViewWidth;
    }

    public void fill(RecyclerView.Recycler recycler) {
        int bottomItemPosition = (int) Math.floor(position());
        int topItemPos = mItemCount - (int) position();
        if (topItemPos != lastTopItemPos) {
            mListener.onTopStackChange(lastTopItemPos,topItemPos);
            lastTopItemPos = topItemPos;
        }
        final int space = getHorizontalSpace(); //展示宽度
        final int bottomItemVisibleSize = isReverseDirection ?
                ((mItemCount - 1) * mItemViewWidth + mScrollOffset) % mItemViewWidth :
                mScrollOffset % mItemViewWidth;
        final float offsetPercent = bottomItemVisibleSize * 1.0f / mItemViewWidth;

        int remainSpace = isReverseDirection ? (space - mItemViewWidth)/2 : (space - mItemViewWidth)/2;//除了Item之外的宽度

        final int baseOffsetSpace = mItemMargin;

        ArrayList<ItemViewInfo> layoutInfos = new ArrayList<>();
        int lastItemPos = bottomItemPosition - mItemStackCount;
        if(lastItemPos < 0){
            lastItemPos = 0;
        }
        for (int i = bottomItemPosition, j = 1;
             i > lastItemPos; i--, j++) {
            double maxOffset = baseOffsetSpace / 2f * Math.pow(mScale, j);

            float adjustedPercent = isReverseDirection ? -offsetPercent : +offsetPercent;
            int start = (int) (remainSpace - adjustedPercent * maxOffset);

            float scaleXY = (float) (Math.pow(mScale, j - 1) * (1 - offsetPercent * (1 - mScale)));
            float percent = start * 1.0f / space;
            ItemViewInfo info = new ItemViewInfo(start, scaleXY, offsetPercent, percent);

            layoutInfos.add(0, info);

            double delta = isReverseDirection ? maxOffset : -maxOffset;
            remainSpace += delta;

//            boolean isOutOfSpace = isReverseDirection ?
//                    remainSpace > baseOffsetSpace * 2 :
//                    remainSpace <= 0;
            if (i - 1 == lastItemPos) {
                info.setTop((int) (remainSpace - delta));
                info.setPositionOffset(0);
                info.setLayoutPercent(info.getTop() / space);
                info.setScaleXY( (float) Math.pow(mScale, j - 1));
//                break;
            }
        }

        if (bottomItemPosition < mItemCount) {
            final int start = isReverseDirection ?
                    bottomItemVisibleSize - mItemViewWidth :
                    space - bottomItemVisibleSize;
            layoutInfos.add(
                    new ItemViewInfo(start,
                            1.0f,
                            offsetPercent,
                            start * 1.0f / space).setIsBottom());
        } else {
            bottomItemPosition -= 1;
        }

        int layoutCount = layoutInfos.size();
        Log.d("JINO","layoutInfos size="+layoutCount);
        for (ItemViewInfo layoutInfo : layoutInfos) {
            Log.i("JINO","layoutInfo:"+layoutInfo);
        }

        final int startPos = bottomItemPosition - (layoutCount - 1);
        final int endPos = bottomItemPosition;
        final int childCount = getChildCount();
        LogUtil.w(MessageFormat.format("layoutCount:{0} --  startPos:{1}  --- endPos:{2} -- childCount:{3}", layoutCount, startPos, endPos, childCount));

        for (int i = childCount - 1; i >= 0; i--) {
            View childView = getChildAt(i);
            int pos = convert2LayoutPosition(getPosition(childView));
            if (pos > endPos || pos < startPos) {
                removeAndRecycleView(childView, recycler);
            }
        }
        detachAndScrapAttachedViews(recycler);

        for (int i = 0; i < layoutCount; i++) {
            int position = convert2AdapterPosition(startPos + i);
            if(position < 0 || mItemCount - 1 < position){
                continue;
            }
            fillChild(recycler.getViewForPosition(position), layoutInfos.get(i));
        }
    }

    private void fillChild(View view, ItemViewInfo layoutInfo) {
        addView(view);
        measureChildWithExactlySize(view);
        int scaleFix = (int) (mItemViewWidth * (1 - layoutInfo.getScaleXY()) / 2);
        if (isReverseDirection) {
            scaleFix = -scaleFix;
        }

        int left = layoutInfo.getTop() - scaleFix;
        int top = getPaddingTop();
        int right = layoutInfo.getTop() + mItemViewWidth - scaleFix;
        int bottom = top + mItemViewHeight;

        layoutDecoratedWithMargins(view, left, top, right, bottom);
//        if (isReverseDirection) {
//            view.setPivotX(view.getMeasuredWidth());
//            view.setPivotY(view.getMeasuredHeight()/2f);
//        }
        ViewCompat.setScaleX(view, layoutInfo.getScaleXY());
        ViewCompat.setScaleY(view, layoutInfo.getScaleXY());
    }

    private void measureChildWithExactlySize(View child) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(
                mItemViewWidth - lp.leftMargin - lp.rightMargin, View.MeasureSpec.EXACTLY);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(
                mItemViewHeight - lp.topMargin - lp.bottomMargin, View.MeasureSpec.EXACTLY);
        child.measure(widthSpec, heightSpec);
    }

    private int makeScrollOffsetWithinRange(int scrollOffset) {
        if (isReverseDirection) {
            return Math.max(Math.min(0, scrollOffset), -(mItemCount - 1) * mItemViewWidth);
        } else {
            return Math.min(Math.max(mItemViewWidth, scrollOffset), mItemCount * mItemViewWidth);
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int delta = isReverseDirection ? -dx : dx;
        delta = (int) (delta * 0.5f);
        int pendingScrollOffset = mScrollOffset + delta;
        mScrollOffset = makeScrollOffsetWithinRange(pendingScrollOffset);
        fill(recycler);
        return dx;
    }

    public int calculateDistanceToPosition(int targetPos) {
        if (isReverseDirection)  {
            return mItemViewWidth * targetPos + mScrollOffset;
        }
        int pendingScrollOffset = mItemViewWidth * (convert2LayoutPosition(targetPos) + 1);
        return pendingScrollOffset - mScrollOffset;
    }

    @Override
    public void scrollToPosition(int position) {
        if (position < 0 || position >= mItemCount) {
            return;
        }
//        int tempOffset = mItemViewWidth * (convert2LayoutPosition(position) + 1);
        int dis = isReverseDirection ? -(position - lastTopItemPos) * mItemViewWidth : (position - lastTopItemPos) * mItemViewWidth;
        brewAndStartAnimator(computeSettleDuration(Math.abs(dis),0),dis);
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    public int convert2AdapterPosition(int layoutPosition) {
        return mItemCount - 1 - layoutPosition;
    }

    public int convert2LayoutPosition(int adapterPostion) {
        return mItemCount - 1 - adapterPostion;
    }

    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    public void setOnStackChangeListener(OnStackChangeListener stackChangeListener) {
        this.mListener = stackChangeListener;
    }

    private static class ItemViewInfo {
        private float mScaleXY;
        private float mLayoutPercent;
        private float mPositionOffset;
        private int mTop;
        private boolean mIsBottom;

        public ItemViewInfo(int top, float scaleXY, float positonOffset, float percent) {
            this.mTop = top;
            this.mScaleXY = scaleXY;
            this.mPositionOffset = positonOffset;
            this.mLayoutPercent = percent;
        }

        public ItemViewInfo setIsBottom() {
            mIsBottom = true;
            return this;
        }

        public float getScaleXY() {
            return mScaleXY;
        }

        public void setScaleXY(float mScaleXY) {
            this.mScaleXY = mScaleXY;
        }

        public float getLayoutPercent() {
            return mLayoutPercent;
        }

        public void setLayoutPercent(float mLayoutPercent) {
            this.mLayoutPercent = mLayoutPercent;
        }

        public float getPositionOffset() {
            return mPositionOffset;
        }

        public void setPositionOffset(float mPositionOffset) {
            this.mPositionOffset = mPositionOffset;
        }

        public int getTop() {
            return mTop;
        }

        public void setTop(int mTop) {
            this.mTop = mTop;
        }

        @Override
        public String toString() {
            return "ItemViewInfo{" +
                    "mScaleXY=" + mScaleXY +
                    ", mLayoutPercent=" + mLayoutPercent +
                    ", mPositionOffset=" + mPositionOffset +
                    ", mTop=" + mTop +
                    ", mIsBottom=" + mIsBottom +
                    '}';
        }
    }

    public interface OnStackChangeListener{
        /**
         * 堆最上面卡片的变化回调
         * @param oldPosition 变动前的卡片下标
         * @param newPostion 变动后的卡片下标
         */
        void onTopStackChange(int oldPosition, int newPostion);
    }
}
