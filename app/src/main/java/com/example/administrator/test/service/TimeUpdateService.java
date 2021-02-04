package com.example.administrator.test.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.administrator.test.R;
import com.example.administrator.test.view.TimeWidget;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 20:17 2019/10/31
 */
public class TimeUpdateService extends Service {

    private Timer mTimer;
    private String tag = "JINO";

    @Override
    public void onCreate() {
        super.onCreate();
        //开启定时器
        startTimer();
    }

    private void startTimer() {
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            int i  = 1 ;
            @Override
            public void run() {
                //ui定时刷新
                updateAppWidget(i);
                i+=1;
                Log.i(tag, "1秒一次的定时任务现在正在运行..........");
            }
        }, 0, 1000);
    }

    protected void updateAppWidget(int i) {
        //获取AppWidget对象
        AppWidgetManager aWM = AppWidgetManager.getInstance(this);
        //获取窗体小部件布局转换成的view对象(定位应用的包名,当前应用中的那块布局文件)
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.view_time_widget);
        //给窗体小部件布view对象,内部控件赋值
        remoteViews.setTextViewText(R.id.tv_time, "服务开启:" + i + "秒");

        //上下文环境,窗体小部件对应广播接受者的字节码文件
        ComponentName componentName = new ComponentName(this, TimeWidget.class);
        //更新窗体小部件
        aWM.updateAppWidget(componentName, remoteViews);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
