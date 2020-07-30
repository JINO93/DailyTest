package com.example.administrator.test.view

import android.animation.*
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.example.administrator.test.util.LogUtil
import com.yibasan.lizhifm.sdk.platformtools.ApplicationContext
import com.yibasan.lizhifm.sdk.platformtools.ui.ViewUtils
import kotlinx.android.synthetic.main.view_live_vote_plugin_detail.view.*

/**
 * @Author: JINO
 * @Description: 投票插件管理器
 * @Date: Create in 18:19 2020/6/17
 */
class VotePluginManager(val rootView: ViewGroup) {

    companion object {
        fun attachTo(root: ViewGroup): VotePluginManager {
            val votePluginManager = VotePluginManager(root)
            votePluginManager.init()
            return votePluginManager
        }
    }

    private var mPluginViewX: Float = 0f
    private var mPluginViewY: Float = 0f
    lateinit var mVoteView: DraggableView
    lateinit var mVoteDetailView: VoteDetailView

    fun init() {
        mVoteView = DraggableView(rootView)
        mVoteView.setOnClickListener {
            Toast.makeText(mVoteView.context, "plugin click", Toast.LENGTH_SHORT).show()
            startVoteEndAnim()
//            showDetailPanel()
        }
        mVoteDetailView = VoteDetailView(rootView.context)
        mVoteDetailView.setOnClickListener {
            showPluginPanel()
        }
        val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layoutParams.gravity = Gravity.CENTER
        rootView.addView(mVoteDetailView, layoutParams)
        mVoteDetailView.visibility = View.INVISIBLE
    }


    private fun startVoteEndAnim() {
        mPluginViewX = mVoteView.x
        mPluginViewY = mVoteView.y
        //挂件动画
        val pluginAnim = ObjectAnimator.ofPropertyValuesHolder(mVoteView,
                PropertyValuesHolder.ofFloat(View.Y, mPluginViewY - ViewUtils.dipToPx(22f).toFloat()),
                PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0f)
        ).apply {
            addListener(object : AnimatorListenerAdapter() {
                var startTranslationY = 0f
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    mVoteView.visibility = View.INVISIBLE
                    mVoteView.y = mPluginViewY
                }

                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    mVoteDetailView.visibility = View.VISIBLE
//                    startTranslationY = mVoteView.translationY
                }
            })
            duration = 160
        }

        val detailPanelEndY = (ViewUtils.getDisplayHeight(ApplicationContext.getContext()) - mVoteDetailView.measuredHeight) / 2f

        //详情面板动画
        val detailStep1Anim = ObjectAnimator.ofPropertyValuesHolder(mVoteDetailView,
                PropertyValuesHolder.ofFloat(View.Y, detailPanelEndY + ViewUtils.dipToPx(46f),
                        detailPanelEndY - ViewUtils.dipToPx(10f)),
                PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0.8f, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.8f, 1f)
        ).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    mVoteDetailView.pivotX = mVoteDetailView.measuredWidth / 2f
                    mVoteDetailView.pivotY = mVoteDetailView.measuredHeight / 2f
                }
            })
            duration = 200
        }
        val detailStep2Anim = ObjectAnimator.ofPropertyValuesHolder(mVoteDetailView,
                PropertyValuesHolder.ofFloat(View.Y, detailPanelEndY)
        ).apply {
            startDelay = 200
            duration = 120
        }

        val mShowVoteEndDetailPanelAnim = AnimatorSet()
        mShowVoteEndDetailPanelAnim?.apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    mVoteView.isEnabled = false
                    mVoteDetailView.isEnabled = false
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    mVoteView.isEnabled = true
                    mVoteDetailView.isEnabled = true
                }
            })
            playTogether(pluginAnim, detailStep1Anim, detailStep2Anim)
        }?.start()
    }

    fun showDetailPanel() {

        mPluginViewX = mVoteView.x
        mPluginViewY = mVoteView.y
        val displayWidth = ViewUtils.getDisplayWidth(mVoteView.context)
        val displayHeight = ViewUtils.getDisplayHeight(mVoteView.context)
        val dx = displayWidth - mPluginViewX - mVoteView.measuredWidth
        val dy = displayHeight - mPluginViewY - mVoteView.measuredHeight
//        LogUtil.d("pTX:$mPluginViewX,pTY:$mPluginViewY , pX:${mVoteView.x}, pY:${mVoteView.y}")
        val detailPanelStartX = displayWidth - mVoteDetailView.measuredWidth - dx
        val detailPanelStartY = displayHeight - mVoteDetailView.measuredHeight - dy
        val detailPanelEndX = (displayWidth - mVoteDetailView.measuredWidth)/2f
        val detailPanelEndY = (displayHeight - mVoteDetailView.measuredHeight)/2f
        LogUtil.d("showDetailPanel   dx:$dx  dy:$dy  detailPanelStartX:$detailPanelStartX,detailPanelStartY:$detailPanelStartY")

        val pluginAnim = ObjectAnimator.ofPropertyValuesHolder(mVoteView,
                PropertyValuesHolder.ofFloat(View.X, mPluginViewX, (displayWidth - mVoteView.measuredWidth) / 2f),
                PropertyValuesHolder.ofFloat(View.Y, mPluginViewY, (displayHeight - mVoteView.measuredHeight) / 2f),
                PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0f)
        ).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    mVoteView.visibility = View.INVISIBLE
                }

                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    mVoteDetailView.visibility = View.VISIBLE
                }
            })
            duration = 300L
        }
        val detailAnim = ObjectAnimator.ofPropertyValuesHolder(mVoteDetailView,
                PropertyValuesHolder.ofFloat(View.X, detailPanelStartX, detailPanelEndX),
                PropertyValuesHolder.ofFloat(View.Y, detailPanelStartY, detailPanelEndY),
                PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0f, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f, 1f)
        ).apply {
            addListener(object :AnimatorListenerAdapter(){
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    mVoteDetailView.pivotX = mVoteDetailView.measuredWidth.toFloat()
                    mVoteDetailView.pivotY = mVoteDetailView.measuredHeight.toFloat()
                }
            })
            duration = 350L
        }
        val closeBtnAnim = ObjectAnimator.ofFloat(mVoteDetailView.view_vote_detail_close, View.TRANSLATION_Y,
                -mVoteDetailView.view_vote_detail_close.measuredHeight.toFloat(), 0f).apply {
            duration = 300L
            startDelay = 350L
        }

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(pluginAnim, detailAnim,closeBtnAnim)
        animatorSet.start()
    }

    fun showPluginPanel() {
        val displayWidth = ViewUtils.getDisplayWidth(mVoteView.context)
        val displayHeight = ViewUtils.getDisplayHeight(mVoteView.context)
        val dx = displayWidth - mPluginViewX - mVoteView.measuredWidth
        val dy = displayHeight - mPluginViewY - mVoteView.measuredHeight
        val detailPanelStartX = displayWidth - mVoteDetailView.measuredWidth - dx
        val detailPanelStartY = displayHeight - mVoteDetailView.measuredHeight - dy
        LogUtil.d("showPluginPanel  dx:$dx  dy:$dy detailPanelStartX:$detailPanelStartX,detailPanelStartY:$detailPanelStartY")


        val pluginAnim = ObjectAnimator.ofPropertyValuesHolder(mVoteView,
                PropertyValuesHolder.ofFloat(View.X, mPluginViewX),
                PropertyValuesHolder.ofFloat(View.Y, mPluginViewY),
                PropertyValuesHolder.ofFloat(View.ALPHA,  0f,1f)
        ).apply {
            duration = 300L
        }
        val detailOriginX = mVoteDetailView.x
        val detailOriginY = mVoteDetailView.y
        val detailAnim = ObjectAnimator.ofPropertyValuesHolder(mVoteDetailView,
                PropertyValuesHolder.ofFloat(View.X, detailOriginX,detailPanelStartX),
                PropertyValuesHolder.ofFloat(View.Y, detailOriginY,detailPanelStartY),
                PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 0f)
        ).apply{
            duration = 350L
        }
        val closeBtnAnim = ObjectAnimator.ofFloat(mVoteDetailView.view_vote_detail_close, View.TRANSLATION_Y,
                0f,-mVoteDetailView.view_vote_detail_close.measuredHeight.toFloat()).apply {
            duration = 300L
        }

        val animatorSet = AnimatorSet()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                mVoteDetailView.apply {
                    visibility = View.INVISIBLE
                    x = detailOriginX
                    y = detailOriginY
                    view_vote_detail_close.translationY = 0f
                }
            }

            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                mVoteView.visibility = View.VISIBLE
                mVoteDetailView.pivotX = mVoteDetailView.measuredWidth.toFloat()
                mVoteDetailView.pivotY = mVoteDetailView.measuredHeight.toFloat()
            }
        })
        animatorSet.playTogether(pluginAnim, detailAnim,closeBtnAnim)
        animatorSet.start()
    }
}