package com.example.administrator.test.video

import android.content.Context
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.view.Gravity
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.example.administrator.test.R
import com.example.administrator.test.util.LogUtil
import kotlinx.android.synthetic.main.view_x_media.view.*

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 17:48 2020/9/2
 */
class XMediaView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
        RelativeLayout(context, attributeSet, defStyleAttr), IMediaListener {

    private var prepareFinish: Boolean = false
    private var texture_display: TextureView? = null

    private var xMediaPlay: XMediaPlay

    companion object{
        var mediaView:XMediaView? = null
    }

    init {
        View.inflate(context, R.layout.view_x_media,this)
        xMediaPlay = XMediaPlay(this)
        iv_play_or_pause.setOnClickListener {
            if (!prepareFinish) {
                return@setOnClickListener
            }
            if (!xMediaPlay.isPlaying) {
                iv_preview.visibility = View.GONE
                xMediaPlay.start()
            }else{
                xMediaPlay.pause()
            }
        }
        start()
    }

    fun start(){
        mediaView = this
        if(texture_display != null){
            container_display.removeView(texture_display)
            texture_display
        }
        texture_display = TextureView(context)
        texture_display?.surfaceTextureListener = xMediaPlay
        val layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER)
        container_display.addView(texture_display, layoutParams)
    }

    override fun onVideoSizeChanged(width: Int, height: Int) {
        LogUtil.d("onVideoSizeChanged  width:$width  height:$height")
    }

    override fun onInfo(what: Int, extra: Int) {
        LogUtil.d("onInfo  what:$what  extra:$extra")
    }

    override fun onError(what: Int, extra: Int) {
        LogUtil.d("onError what:$what  extra:$extra")
    }

    override fun setBufferProgress(percent: Int) {
        LogUtil.d("setBufferProgress percent:$percent")
    }

    override fun onSeekComplete() {
        LogUtil.d("onSeekComplete")
    }

    override fun onPrepared() {
        LogUtil.d("onPrepared")
        prepareFinish = true
    }

    override fun onCompletion() {
        LogUtil.d("onCompletion")
    }

    fun release() {
        xMediaPlay.release()
    }

    override fun setSurfaceTexture(surfaceTexture: SurfaceTexture) {
        texture_display?.surfaceTexture = surfaceTexture
    }

}
