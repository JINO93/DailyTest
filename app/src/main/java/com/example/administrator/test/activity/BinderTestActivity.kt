package com.example.administrator.test.activity

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Process
import android.os.RemoteException
import android.support.v7.app.AppCompatActivity
import com.example.administrator.test.R
import com.example.administrator.test.RemoteCallBack
import com.example.administrator.test.RemoteInterface
import com.example.administrator.test.bean.aidl.ExData
import com.example.administrator.test.service.RemoteService
import com.example.administrator.test.util.LogUtil
import kotlinx.android.synthetic.main.activity_binder_test.*

class BinderTestActivity : AppCompatActivity() {
    private lateinit var callBack: RemoteCallBack.Stub
    private lateinit var recipient: () -> Unit
    private var remote: RemoteInterface? = null
    private lateinit var conn: ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binder_test)

        recipient = {
            LogUtil.e("binder dead")
            (remote as? IBinder)?.unlinkToDeath(recipient, 0)
            bind()
        }
        conn = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                LogUtil.w("onServiceDisconnected")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                LogUtil.w("onServiceConnected")
                service?.linkToDeath(recipient, 0)
                remote = RemoteInterface.Stub.asInterface(service)
            }

        }
        callBack = object : RemoteCallBack.Stub() {
            override fun onCallback(num: Int) {
                LogUtil.e("on remote callback :$num  in process:${Process.myPid()}  thread:${Thread.currentThread().name}")
            }

        }

        btn_bind.setOnClickListener {
            bind()
        }

        btn_unbind.setOnClickListener {
            unbindService(conn)
        }

        btn_test.setOnClickListener {
            val eD = ExData("JINO", 12)
            LogUtil.d("source hash:${eD.hashCode()}")
            val sum = try {

                remote?.add(1, 1, eD, callBack)
            } catch (e: Exception) {
                LogUtil.e("add error:${e.message}")
            }
            LogUtil.d("result hash:${eD.hashCode()}")
            LogUtil.d("res:$sum in process:${Process.myPid()} data:$eD --- Thread:${Thread.currentThread().name}")
        }

        btn_exit.setOnClickListener {
            try {
                remote?.exit()
            } catch (e: RemoteException) {
                LogUtil.e("remote error:$e.message")
            }
        }
    }

    private fun bind() {
        bindService(Intent(this, RemoteService::class.java), conn, Service.BIND_AUTO_CREATE)
    }
}