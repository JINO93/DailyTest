package com.example.administrator.test.view

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageView
import com.example.administrator.test.R

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 18:28 2020/6/17
 */
class VoteDetailView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attributeSet, defStyleAttr) {

    init {
//        inflate(context,0,this)
        val imageView = ImageView(context)
        setBackgroundColor(Color.RED)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        imageView.setImageResource(R.mipmap.ic_launcher)
        addView(imageView, LayoutParams(300, 300))
    }
}