package com.example.administrator.test.view

import android.content.Context
import android.graphics.Color
import android.support.v4.view.ViewCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.yibasan.lizhifm.sdk.platformtools.ui.ViewUtils

/**
 * @Author: JINO
 * @Description: 投票头像控件
 * @Date: Create in 14:24 2020/6/23
 */
class VoteTeamView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
        RelativeLayout(context, attributeSet, defStyleAttr) {

    init {
//        orientation = HORIZONTAL
//        gravity = Gravity.CENTER
    }

    var leftToRight = true

    var itemPadding = ViewUtils.dipToPx(4f)

    var itemSize = ViewUtils.dipToPx(52f)

    private var data: List<ItemData> = mutableListOf()

    val ITEM_BASE_ID = 0x990

    val ITEM_TEXT_ID = 0x890

    fun invalidateData() {
        if (data.isEmpty()) {
            return
        }
        removeAllViews()
        if (leftToRight) {
            data.forEachIndexed { index, itemData ->
                val ivUserAvatar = ImageView(context)
                ivUserAvatar.id = ITEM_BASE_ID + index
                val layoutParams = RelativeLayout.LayoutParams(itemSize, itemSize)
                if (index != data.size - 1) {
                    layoutParams.marginStart = -itemPadding
                    layoutParams.addRule(RelativeLayout.END_OF,ivUserAvatar.id + 1)
                }

                init(ivUserAvatar, index, itemData)
                addView(ivUserAvatar, layoutParams)
            }
        }else{
            data.asReversed().forEachIndexed { index, itemData ->
                val ivUserAvatar = ImageView(context)
                ivUserAvatar.id = ITEM_BASE_ID + index
                val layoutParams = RelativeLayout.LayoutParams(itemSize, itemSize)
                if (index != 0) {
                    layoutParams.marginStart = -itemPadding
                    layoutParams.addRule(RelativeLayout.END_OF,ivUserAvatar.id - 1)
                }else{
                    layoutParams.addRule(RelativeLayout.END_OF,ITEM_TEXT_ID)
                }

                init(ivUserAvatar, index, itemData)
                addView(ivUserAvatar, layoutParams)
            }
        }

        if (data.size == 1) {
            val tvUserName = TextView(context)
            tvUserName.apply {
                id = ITEM_TEXT_ID
                text = data[0].userName
                textSize = 11f
                setTextColor(Color.BLACK)
                maxLines = 1
                ellipsize = TextUtils.TruncateAt.END
            }
            val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.apply {
                addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
                if (leftToRight) {
                    addRule(RelativeLayout.END_OF,ITEM_BASE_ID)
                    marginStart = ViewUtils.dipToPx(2f)
                } else {
//                layoutParams.addRule(START_OF,ITEM_BASE_ID)
                    marginEnd = ViewUtils.dipToPx(2f)
                }
            }
            addView(tvUserName, layoutParams)
        }
    }

    fun setData(data: List<ItemData>) {
        this.data = data
        invalidateData()
    }

    fun init(imageView: ImageView, index:Int, itemData: ItemData) {
        Glide.with(context).load(itemData.avatar).circleCrop().into(imageView)
//        imageView.setTag(R.id.live_tag_vote_team_item_index, index)
//        imageView.setOnClickListener(this)
    }

    data class ItemData(val userId: Long, val avatar: String, val userName: String)
}