package com.example.administrator.test.view.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import com.example.administrator.test.R
import com.yibasan.lizhifm.sdk.platformtools.ui.ViewUtils

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 16:52 2020/6/29
 */
class VoteRecordDialog(context: Context, cancelable: Boolean = true) :
        Dialog(context, cancelable, null) {

    init {
        configWindow(context)
        setContentView(R.layout.view_live_vote_record)
    }

    private fun configWindow(context: Context) {
        window?.apply {
            decorView.setPadding(0, 0, 0, 0)
            val params = attributes
            params.width = ViewUtils.getDisplayWidth(context)
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            attributes = params
            setGravity(Gravity.BOTTOM)
        }
    }
}