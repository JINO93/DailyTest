package com.example.administrator.test.video

import android.graphics.SurfaceTexture

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 17:52 2020/9/2
 */
interface IMediaListener {

    fun setSurfaceTexture(surfaceTexture: SurfaceTexture)
    fun onVideoSizeChanged(width: Int, height: Int)
    fun onInfo(what: Int, extra: Int)

    fun onError(what: Int, extra: Int)

    fun setBufferProgress(percent: Int)

    fun onSeekComplete()

    fun onPrepared()

    fun onCompletion()
}