package com.example.administrator.test.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.administrator.test.R
import com.example.administrator.test.video.XMediaView
import kotlinx.android.synthetic.main.activity_scene_transition.*

class SceneTransitionActivity : AppCompatActivity() {
    private var xMediaView: XMediaView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene_transition)

        container_preview.setOnClickListener {
            val sceneBundle = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this, container_preview, "scene_img")
                    .toBundle()
            startActivity(Intent(this,SceneDetailActivity::class.java),sceneBundle)
            overridePendingTransition(0,0)
        }
        xMediaView = XMediaView(this)
        val layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER)
        container_preview.addView(xMediaView, layoutParams)
    }

    override fun onResume() {
        super.onResume()
        if (container_preview.childCount == 0) {
            val parent = xMediaView?.apply {
                (parent as ViewGroup).removeView(this)
                val layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER)
                container_preview.addView(this, layoutParams)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        xMediaView?.release()
    }
}