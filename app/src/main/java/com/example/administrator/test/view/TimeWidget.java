package com.example.administrator.test.view;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.administrator.test.R;
import com.example.administrator.test.service.TimeUpdateService;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 20:10 2019/10/31
 */
public class TimeWidget extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.view_time_widget);
        Intent intent = new Intent(context, TimeUpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.tv_time, pendingIntent);

        //update view
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

}
