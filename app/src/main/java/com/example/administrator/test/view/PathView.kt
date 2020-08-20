package com.example.administrator.test.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yibasan.lizhifm.sdk.platformtools.ui.ViewUtils

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 11:20 2020/8/18
 */
class PathView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
        View(context, attributeSet, defStyleAttr) {

    var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mPathMeasure = PathMeasure()
    var mPath = Path()
    var mDetPath = Path()
    val mPos = FloatArray(2)
    val mTan = FloatArray(2)
    var mPercent = 0f

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = ViewUtils.dipToPx(2f).toFloat()
        mPath.addCircle(0f, 0f, 200f, Path.Direction.CW)
        mPathMeasure.setPath(mPath, true)

        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1500
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                mPercent = it.animatedValue as Float
                invalidate()
            }
        }.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            save()
            translate(300f, 300f)
            mPaint.color = Color.CYAN
            drawPath(mPath,mPaint)

            mPaint.color = Color.BLUE
            mDetPath.reset()
            val end = mPathMeasure.length * mPercent
            mPathMeasure.getPosTan(end,mPos,mTan)
            val angle = (Math.atan2(mTan[1].toDouble(), mTan[0].toDouble()) * 180f/ Math.PI).toFloat()
            drawCircle(mPos[0],mPos[1],10f,mPaint)
//            rotate(angle)
            restore()
        }
    }

}