package com.example.administrator.test.view

import android.graphics.Color
import android.graphics.PointF
import android.graphics.Rect
import android.support.constraint.ConstraintLayout
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.example.administrator.test.R
import com.example.administrator.test.util.LogUtil
import com.example.administrator.test.util.VibratorUtil
import com.yibasan.lizhifm.sdk.platformtools.ui.ViewUtils
import kotlin.math.abs

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 16:20 2020/6/16
 */
class DraggableView @JvmOverloads constructor(private val root: ViewGroup, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(root.context, attrs, defStyleAttr) {

    private var mClickListener: OnClickListener? = null
    var scaledWindowTouchSlop: Int = 3

    //    lateinit var mDragHelper: ViewDragHelper
    private var mIsDragging = false
    private val mTouchPoint = PointF()

    init {
        val imageView = ImageView(context)
        setBackgroundColor(Color.RED)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        imageView.setImageResource(R.mipmap.ic_launcher)
        addView(imageView, LayoutParams(150, 150))
        val layoutParams = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.BOTTOM or Gravity.END
        layoutParams.bottomMargin = ViewUtils.dipToPx(10f)
        layoutParams.marginEnd = ViewUtils.dipToPx(10f)
        root.addView(this, layoutParams)
//        scaledWindowTouchSlop = ViewConfiguration.get(context).scaledWindowTouchSlop
//        mDragHelper = ViewDragHelper.create(root,object: ViewDragHelper.Callback() {
//            override fun tryCaptureView(child: View?, pointerId: Int): Boolean {
//                return child == this@DraggableView
//            }
//
//            override fun clampViewPositionHorizontal(child: View?, left: Int, dx: Int): Int {
//                return left
//            }
//
//            override fun clampViewPositionVertical(child: View?, top: Int, dy: Int): Int {
//                return top
//            }
//        })
    }

    override fun setOnClickListener(l: OnClickListener?) {
        mClickListener = l
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    var startTime = 0L
    var lastAction: Int = -1

    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        LogUtil.d("onTouchEvent:"+event?.action)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startTime = System.currentTimeMillis()
                mTouchPoint.apply {
                    x = event.rawX
                    y = event.rawY
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (mIsDragging) {
                    var dx = (event.rawX - mTouchPoint.x).toInt()
                    var dy = (event.rawY - mTouchPoint.y).toInt()
                    if (abs(dx) > scaledWindowTouchSlop || abs(dy) > scaledWindowTouchSlop) {
//                        if (left + dx < 0) {
//                            dx = 0 - left
//                        } else if (right + dx > root.measuredWidth) {
//                            dx = root.measuredWidth - right
//                        }
//                        if (top + dy < 0) {
//                            dy = 0 - top
//                        } else if (bottom + dy > root.measuredHeight) {
//                            dy = root.measuredHeight - bottom
//                        }
//                        ViewCompat.offsetLeftAndRight(this, dx)
//                        ViewCompat.offsetTopAndBottom(this, dy)
                        //理论中X轴拖动的距离
                        var endX: Float = x + dx
                        //理论中Y轴拖动的距离
                        var endY: Float = y + dy
                        //X轴可以拖动的最大距离
                        val maxX: Float = (root.measuredWidth - width).toFloat()
                        //Y轴可以拖动的最大距离
                        val maxY: Float = (root.measuredHeight - height).toFloat()
                        //X轴边界限制
                        endX = if (endX < 0) 0f else if (endX > maxX) maxX else endX
                        //Y轴边界限制
                        endY = if (endY < 0) 0f else if (endY > maxY) maxY else endY
                        //开始移动
                        x = endX
                        y = endY
                        mTouchPoint.apply {
                            x = event.rawX
                            y = event.rawY
                        }
                    }
                } else if (inRangeOfView(event)) {
                    mIsDragging = System.currentTimeMillis() - startTime >= 500
                    if (mIsDragging) {
                        VibratorUtil.getInstance().startVibrator(100)
                    }
                }
            }
            else -> {
                if (mIsDragging) {
                    mIsDragging = false
                    autoFillEdge()
                }

                if (lastAction == MotionEvent.ACTION_DOWN) {
                    mClickListener?.onClick(this)
                }
            }
        }
//        mDragHelper.processTouchEvent(event)
        lastAction = event?.action ?: -1
        return true
    }

    private fun autoFillEdge() {
        LogUtil.d("vC:" + left + width / 2)
        LogUtil.d("pvC:" + root.left + root.width / 2)
        LogUtil.d("laX:" + mTouchPoint.x)
        if (mTouchPoint.x < root.width / 2) {
            animate()
                    .x(0f)
                    .setDuration(500L)
                    .start()
        } else {
            animate()
                    .x(root.right.toFloat() - width)
                    .setDuration(500L)
                    .start()
        }
    }

    private fun inRangeOfView(event: MotionEvent?): Boolean {
        return event?.let {
            val location = IntArray(2)
            getLocationOnScreen(location)
            val viewX = location[0]
            val viewY = location[1]
            if (it.rawX < viewX
                    || it.rawX > (viewX + width)
                    || it.rawY < viewY
                    || it.rawY > (viewY + height)) {
                return false
            }
            return true
        } ?: false
    }


}