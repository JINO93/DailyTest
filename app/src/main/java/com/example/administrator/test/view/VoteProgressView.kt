package com.example.administrator.test.view

import android.animation.ValueAnimator
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.example.administrator.test.R
import kotlinx.android.synthetic.main.view_vote_comptition_progress.view.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * @Author: JINO
 * @Description: 投票比分View
 * @Date: Create in 15:20 2020/6/20
 */
class VoteProgressView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attributeSet, defStyleAttr) {

    var leftVoteCount = 0
    var rightVoteCount = 0
    var barMaxWidth = 0
    var barMinWidth = 0

    init {
        inflate(context, R.layout.view_vote_comptition_progress, this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        barMaxWidth = w
        barMinWidth = if (barMaxWidth > 0) barMaxWidth/10 else 0
        v_vote_competition_progress.layoutParams.width = barMaxWidth/2
        adjustCurrentProgress()
    }

    fun adjustCurrentProgress(){
        val layoutParams = v_vote_competition_progress.layoutParams
        val preWidth = layoutParams.width
        var newWidth = 0
        when {
            leftVoteCount - rightVoteCount == 0 -> {
                newWidth = barMaxWidth/2
            }
            leftVoteCount == 0 -> {
                newWidth = barMinWidth
            }
            rightVoteCount == 0 -> {
                newWidth = barMaxWidth - barMinWidth
            }
            else -> {
                var width = (barMaxWidth * leftVoteCount.toFloat() / (leftVoteCount + rightVoteCount)).roundToInt()
                width = min(barMaxWidth - barMinWidth,width)
                width = max(barMinWidth,width)
                newWidth = width
            }
        }
        val widthAnim = ValueAnimator.ofInt(preWidth, newWidth)
        widthAnim.addUpdateListener {
            layoutParams.width = it.animatedValue as Int
            v_vote_competition_progress.layoutParams = layoutParams
        }
        widthAnim.duration = 500
        widthAnim.start()
//        invalidate()
    }

    @JvmOverloads
    fun setVoteData(leftTeam:Int = -1, rightTeam:Int = -1){
        if(leftTeam > 0){
            leftVoteCount = leftTeam
            tv_left_team_vote_count.text = leftVoteCount.toString()
        }
        if(rightTeam > 0){
            rightVoteCount = rightTeam
            tv_right_team_vote_count.text = rightVoteCount.toString()
        }
        adjustCurrentProgress()
    }
}