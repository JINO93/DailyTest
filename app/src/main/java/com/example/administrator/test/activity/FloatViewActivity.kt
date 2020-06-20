package com.example.administrator.test.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import com.example.administrator.test.R
import com.example.administrator.test.view.DraggableView
import com.example.administrator.test.view.VotePluginManager
import kotlinx.android.synthetic.main.activity_float_view.*

class FloatViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_view)
        VotePluginManager.attachTo(window.decorView as ViewGroup)
    }
}