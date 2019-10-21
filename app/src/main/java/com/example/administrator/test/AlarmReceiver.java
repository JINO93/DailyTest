package com.example.administrator.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;

import static com.example.administrator.test.Constant.TAG;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "on alarm");
    }
}
