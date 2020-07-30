package com.example.administrator.test.view

import android.content.Context
import android.content.res.TypedArray
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.example.administrator.test.R

/**
 * @Author: JINO
 * @Description: 可设置最大高度的RecyclerView
 * @Date: Create in 11:18 2020/6/29
 */
class MaxHeightRecyclerView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
        RecyclerView(context, attributeSet, defStyleAttr) {

    private var mMaxHeight: Int = 0

    init {
        initialize(context, attributeSet)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView)
        mMaxHeight = arr.getDimensionPixelSize(R.styleable.MaxHeightRecyclerView_maxHeight, mMaxHeight)
        arr.recycle()
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        var tempHeightSpec = heightSpec
        if (mMaxHeight > 0) {
            tempHeightSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST)
        }
        super.onMeasure(widthSpec, tempHeightSpec)
    }
}
