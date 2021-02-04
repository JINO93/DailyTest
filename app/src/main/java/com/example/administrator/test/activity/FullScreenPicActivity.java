package com.example.administrator.test.activity;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.administrator.test.R;
import com.example.administrator.test.util.LogUtil;
import com.yibasan.lizhifm.lzlogan.Logz;
import com.yibasan.lizhifm.sdk.platformtools.ApplicationContext;
import com.yibasan.lizhifm.sdk.platformtools.ui.ViewUtils;

import java.text.MessageFormat;

public class FullScreenPicActivity extends AppCompatActivity {

    private boolean isAdd;
    private BroadcastReceiver mWifiChangeReceiver = new WifiChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_pic);
        LogUtil.w(MessageFormat.format("diH:{0}  sH:{0}"
                , ViewUtils.getDisplayHeight(this)
                ,getScreenHeight()
        ));

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiChangeReceiver, filter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mWifiChangeReceiver);
    }

    public int getScreenHeight() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        showDialog();
        return super.onTouchEvent(event);
    }

    private void showDialog() {
        if (isAdd) {
            return;
        }
        new FullScreenPicDialog().show(getFragmentManager(), "JINO");
        isAdd = !isAdd;
    }

    public static class FullScreenPicDialog extends DialogFragment{


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.SplashDialog);
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Dialog dialog = super.onCreateDialog(savedInstanceState);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = 0;
            }
            return dialog;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageResource(R.drawable.icon_splash);
            return imageView;
        }

//        @Override
//        public void show(FragmentManager manager, String tag) {
//            if (!isAdded()) {
//                FragmentTransaction fragmentTransaction = manager.beginTransaction();
//                fragmentTransaction.add(FullScreenPicDialog.this, tag);
//                fragmentTransaction.commitAllowingStateLoss();
//            }
//        }
    }

    class WifiChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(WifiManager.NETWORK_STATE_CHANGED_ACTION == intent.getAction()){
//                Parcelable parcelables = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
//                if(parcelables != null){
//                    NetworkInfo networkInfo = (NetworkInfo)parcelables;
//                    boolean connected = networkInfo.isConnected();
////                    EventBus.getDefault().post(new WifiChangeEvent(connected));
//                    LogUtil.w("wifi state change,state:" + connected);
//                }
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                LogUtil.w("wifi state:"+info.getState());
                if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
                    LogUtil.w("wifi断开");
                } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    //获取当前wifi名称
                    LogUtil.w("连接到网络 " + wifiInfo.getSSID());
                }
            }
        }
    }
}
