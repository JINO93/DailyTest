package com.example.administrator.test.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.administrator.test.R;

public class ForeGroundService extends Service {

    private static final int ServiceID = 0x1;

    private static boolean mShowNotification;

    public ForeGroundService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, getNotification(getApplicationContext()));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    public class LocalBinder extends Binder {
        public ForeGroundService getService() {
            return ForeGroundService.this;
        }
    }


    static class Connection implements ServiceConnection {
        Service mainService;

        Connection(Service mainService) {
            this.mainService = mainService;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {

            // sdk >=18
            // 的，会在通知栏显示service正在运行，这里不要让用户感知，所以这里的实现方式是利用2个同进程的service，利用相同的notificationID，
            // 2个service分别startForeground，然后只在1个service里stopForeground，这样即可去掉通知栏的显示
            try {
                if (!mShowNotification) {
                    return;
                }
                Service assistService = ((ForeGroundService.LocalBinder) binder).getService();
//                if (ModuleServiceUtil.VoiceService.playListManager != null) {
//                }
                mainService.startForeground(ServiceID, getNotification(mainService.getBaseContext()));
                assistService.startForeground(ServiceID, getNotification(assistService.getBaseContext()));

                assistService.stopForeground(true);
                mainService.unbindService(this);
            } catch (Exception e) {
                Log.e("JINO", e.getMessage());
            }
        }
    }

    public static void setForeground(Service targetService, boolean showNotification) {
        mShowNotification = showNotification;
        ForeGroundService.Connection connection = new ForeGroundService.Connection(targetService);
        targetService.bindService(new Intent(targetService, ForeGroundService.class), connection, Service.BIND_AUTO_CREATE);
    }

    static Notification getNotification(Context context) {
        String channel_id = "test_channel";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id, "test 1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setVibrationPattern(new long[]{0});
            channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.mipmap.ic_launcher_)
                .setContentTitle("Foreground notification")
                .setContentText("lalalalalalalal")
                .setSound(null)
                .setOnlyAlertOnce(true)
                .setVibrate(new long[]{0})
                .build();
//        notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        return notification;
    }
}
