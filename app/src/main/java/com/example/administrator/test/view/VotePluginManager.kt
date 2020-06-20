package com.example.administrator.test.view

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast

/**
 * @Author: JINO
 * @Description: 投票插件管理器
 * @Date: Create in 18:19 2020/6/17
 */
class VotePluginManager(val rootView:ViewGroup) {

    companion object {
        fun attachTo(root: ViewGroup): VotePluginManager {
            val votePluginManager = VotePluginManager(root)
            votePluginManager.init()
            return votePluginManager
        }
    }

    lateinit var mVoteView : DraggableView
    lateinit var mVoteDetailView : VoteDetailView

    fun init(){
        mVoteView = DraggableView(rootView)
        mVoteView.setOnClickListener {
            Toast.makeText(mVoteView.context, "plugin click", Toast.LENGTH_SHORT).show()
            showDetailPanel()
        }
        mVoteDetailView = VoteDetailView(rootView.context)
        val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        rootView.addView(mVoteDetailView, layoutParams)
        mVoteDetailView.visibility = View.INVISIBLE
    }

    fun showDetailPanel(){
        mVoteDetailView.visibility = View.VISIBLE
        mVoteView.visibility = View.INVISIBLE
    }
}