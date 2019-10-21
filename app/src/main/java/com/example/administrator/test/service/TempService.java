package com.example.administrator.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 11:13 2019/7/11
 */
public class TempService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ForeGroundService.setForeground(this,true);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
