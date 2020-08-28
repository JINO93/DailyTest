package com.example.administrator.test.service

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.Process
import android.os.RemoteCallbackList
import com.example.administrator.test.RemoteCallBack
import com.example.administrator.test.RemoteInterface
import com.example.administrator.test.bean.aidl.ExData
import com.example.administrator.test.util.LogUtil
import kotlin.system.exitProcess

class RemoteService : Service() {

    private val mCallBackList = RemoteCallbackList<RemoteCallBack>()

    private val binder = object : RemoteInterface.Stub(){
        override fun exit() {
            exitProcess(0)
        }

        override fun add(a: Int, b: Int, c: ExData?, callback: RemoteCallBack?): Int {
            LogUtil.d("add On process:${Process.myPid()} thread:${Thread.currentThread().name} cHash:${c.hashCode()}")
            Thread.sleep(10_000)
            var i = a + b
            if (c != null) {
                i += c.num
                c.name = "R${c.name}"
            }
            mCallBackList.register(callback)
//            callback?.onCallback(i)
            boardcast(i)
            return i
        }


    }

    fun boardcast(num:Int){
        val count = mCallBackList.beginBroadcast()
        LogUtil.w("boardcast count:$count")
        for(i in 0 until count){
            try {
                mCallBackList.getBroadcastItem(i).onCallback(num)
            } catch (e: Exception) {
                LogUtil.e("remote callback loop error:${e.message}")
            }
        }
        mCallBackList.finishBroadcast()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtil.w("RemoteService---onStartCommand")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        LogUtil.w("RemoteService---onBind")
        return binder
    }


    override fun unbindService(conn: ServiceConnection) {
        super.unbindService(conn)
        LogUtil.w("RemoteService---unbindService")
    }
}
