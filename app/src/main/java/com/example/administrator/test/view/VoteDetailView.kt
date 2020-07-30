package com.example.administrator.test.view

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.administrator.test.R

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 18:28 2020/6/17
 */
class VoteDetailView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
        FrameLayout(context, attributeSet, defStyleAttr) {

    init {
        inflate(context, R.layout.view_live_vote_plugin_detail, this)
        setBackgroundColor(Color.parseColor("#55000000"))
//        val imageView = ImageView(context)
//        setBackgroundColor(Color.RED)
//        imageView.scaleType = ImageView.ScaleType.FIT_XY
//        imageView.setImageResource(R.mipmap.ic_launcher)
//        addView(imageView, LayoutParams(300, 300))
    }
}