package com.example.administrator.test.activity

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.administrator.test.R
import com.example.administrator.test.video.XMediaView
import kotlinx.android.synthetic.main.activity_scene_detail.*

class SceneDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val enterTransition = TransitionInflater.from(this)
                .inflateTransition(R.transition.trans_scene_img)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.sharedElementEnterTransition = enterTransition
        }
        setContentView(R.layout.activity_scene_detail)

        iv_detail.post {
            val mediaView = XMediaView.mediaView
            if (mediaView != null) {
                if (mediaView.parent != null) {
                    (mediaView.parent as ViewGroup).removeView(mediaView)
                }
                val layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER)
                iv_detail.addView(mediaView, layoutParams)
            }
        }
    }
}