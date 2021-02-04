package com.example.administrator.test.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.test.Constant;
import com.example.administrator.test.R;
import com.example.administrator.test.adapter.ContributeItemAdapter;
import com.example.administrator.test.bean.ContributeItemData;
import com.example.administrator.test.fragment.TestScalePicFragment;
import com.example.administrator.test.service.ForeGroundService;
import com.example.administrator.test.util.FragmentBackHelper;
import com.example.administrator.test.util.LogUtil;
import com.example.administrator.test.util.ViewUtils;
import com.example.administrator.test.util.transform.GlideRoundTransform;
import com.example.administrator.test.view.dialog.BuyVipSuccessDialog;
import com.example.administrator.test.view.dialog.GuideRecordHintDialog;
import com.example.administrator.test.view.dialog.HourPickDialog;
import com.yibasan.lizhi.sdk.network.http.HttpRequest;
import com.yibasan.lizhi.sdk.network.http.rx.RxResponseListener;
import com.yibasan.lizhifm.sdk.platformtools.executor.ThreadExecutor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "channel_test";
    private NotificationManager notificationManager;
    private AlarmManager alarmManager;
    private int count = 0;
    private ImageView ivDisplay;
    private ViewGroup btnContainer;
    private TextView tvPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.startMethodTracing("testTrace.trace");
        setContentView(R.layout.activity_main);
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        String t = "购买多份声音，自留1份收听外，其余可赠送给好友，让主播声音被更多人听见\\n 你赠送声音后，若好友超过24小时未领取，则赠送链接失效。你还可重新发起赠送";
        ((TextView) findViewById(R.id.tv_test)).setText(t);
        btnContainer = findViewById(R.id.layout_btn_container);

        ivDisplay = findViewById(R.id.iv_display);
        addBtn("卡片堆叠", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SlidingCardActivity.class);
            }
        });
        addBtn("浮动动画", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator flowAnim = ObjectAnimator.ofFloat(v, View.TRANSLATION_Y, -5, 5)
                        .setDuration(500);
                flowAnim.setRepeatMode(ValueAnimator.REVERSE);
                flowAnim.setRepeatCount(ValueAnimator.INFINITE);
                flowAnim.start();
            }
        });

//        addBtn("Flutter Test", v -> startActivity(FlutterTestActivity.class));

        addBtn("Guide Dialog", v -> new GuideRecordHintDialog(this).show());


        addBtn("Hour pick Dialog", v -> new HourPickDialog(this).show());

        addBtn("Full pic test", v -> startActivity(FullScreenPicActivity.class));


        addBtn("test lzHttp", v -> testHttp());

        addBtn("get ua", v -> getUA());

        addBtn("test pic scale", v -> new TestScalePicFragment().show(getSupportFragmentManager(),"picScale"));

        addBtn("floatView",v -> startActivity(FloatViewActivity.class));

        addBtn("test canvas",v->startActivity(CanvasActivity.class));


        addBtn("binder Test",v->startActivity(BinderTestActivity.class));

        addBtn("Video scene transition", v -> startActivity(SceneTransitionActivity.class));

        View tvPaint = findViewById(R.id.tv_paint);
        Drawable drawable = DrawableCompat.wrap(tvPaint.getBackground());
        DrawableCompat.setTint(drawable,getResources().getColor(android.R.color.holo_purple));


        changeTextColor();

        ProcessLifecycleOwner.get().getLifecycle().addObserver(
                new LifecycleObserver() {
                    @OnLifecycleEvent(Lifecycle.Event.ON_START)
                    public void onStart(){
                        LogUtil.d("-----------onStart");
                    }
                }
        );
        Debug.stopMethodTracing();
    }

    private void getUA() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                WebView webView = new WebView(getApplicationContext());
                LogUtil.e(webView.getSettings().getUserAgentString());
                Looper.myLooper().quit();
            }
        }).start();
//        int[] ts = new ArrayList<Integer>().toArray(new In[0]);
    }

    private void testHttp() {
        HttpRequest.Builder builder = new HttpRequest.Builder();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "JINO");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        builder.contentType(HttpRequest.CONTENT_TYPE_JSON_UTF8)
                .url("http://httpbin.org/post")
                .method(HttpRequest.POST)
                .stringBody(jsonObject.toString())
                .build()
                .newCall(new RxResponseListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.d(s);
                    }

                    @Override
                    public void onError(int i, String s) {
                        LogUtil.e(s);
                    }
                });
    }

    private void changeTextColor() {
        tvPaint = findViewById(R.id.tv_paint);
        TextPaint paint = tvPaint.getPaint();
        LinearGradient linearGradient = new LinearGradient(0, 0, 0, tvPaint.getMeasuredHeight() / 3,
                //                new int[]{Color.parseColor("#c9944e"),
                //                        Color.parseColor("#efb15f"),
                //                        Color.parseColor("#c7924d")},
                new int[]{Color.RED,
                        Color.GREEN,
//                                        Color.RED
                },
                //                        new float[]{0,0.3f,1f},
                new float[]{0,/*0.5f,*/1f},
                Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        tvPaint.invalidate();
        tvPaint.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private void startActivity(Class<?> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    private void addBtn(String title, View.OnClickListener onClickListener) {
        Button button = new Button(this);
        button.setText(title);
        button.setOnClickListener(onClickListener);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        params.topMargin = ViewUtils.dipToPx(this, 16);
        btnContainer.addView(button, params);
    }

    @Override
    public void onBackPressed() {
        LogUtil.d("activity onBackPressed");
        if (!FragmentBackHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    public void createChannel(View view) {
        if (notificationManager == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(CHANNEL_ID) != null) {
                Toast.makeText(this, "channel already exist.", Toast.LENGTH_SHORT).show();
                notificationManager.deleteNotificationChannel(CHANNEL_ID);
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "test", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setVibrationPattern(new long[]{0});
                notificationChannel.setSound(null, null);
                notificationManager.createNotificationChannel(notificationChannel);
            } else {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "test", NotificationManager.IMPORTANCE_HIGH);
                channel.setVibrationPattern(new long[]{0});
                channel.setSound(null, null);
                notificationManager.createNotificationChannel(channel);
            }
        }

        //设置自定义布局
        RemoteViews normalView = new RemoteViews(getPackageName(),R.layout.view_notification_normal);
        RemoteViews bigView = new RemoteViews(getPackageName(),R.layout.view_notification_big);

        android.support.v4.media.app.NotificationCompat.MediaStyle mediaStyle = new android.support.v4.media.app.NotificationCompat.MediaStyle();
        mediaStyle.setShowCancelButton(true);
        mediaStyle.setShowActionsInCompactView(0,1,2);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setColorized(true)
                .setColor(Color.RED)
//                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.test))
                .setContentTitle("Title")
                .setContentText("I'm content")
                .addAction(android.R.drawable.ic_media_previous,"pre",null)
                .addAction(android.R.drawable.ic_media_play,"Play",null)
                .addAction(android.R.drawable.ic_media_next,"next",null)
                .setCustomContentView(normalView)
//                .setCustomBigContentView(bigView)
                .setCustomHeadsUpContentView(normalView)
                .setContent(normalView)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setStyle(mediaStyle)
                .build();
        notificationManager.notify(1, notification);
    }

    public void setAlarm(View view) {
        if (alarmManager == null) {
            return;
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, new Intent("com.jino.test"),
                PendingIntent.FLAG_UPDATE_CURRENT);
        int delayTime = 5 * 60 * 1000;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delayTime,
                    pendingIntent);
            Log.w(Constant.TAG, "set Alarm with setExactAndAllowWhileIdle");
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delayTime,
                    pendingIntent);
            Log.w(Constant.TAG, "set Alarm with setExact");
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delayTime,
                    pendingIntent);
            Log.w(Constant.TAG, "set Alarm with set");
        }
    }

    public void cancelAlarm(View view) {
        if (alarmManager == null) {
            return;
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, new Intent("com.jino.test"),
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        Log.w(Constant.TAG, "cancel alarm");
    }

    public void requestPermission(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "granted permission", Toast.LENGTH_SHORT).show();
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults != null || grantResults.length > 0) {
                requestPermission(null);
            }
        }
    }

    public void startForegroudnNotificatioin(View view) {
//        bindService(new Intent(this,ForeGroundService.class))
        startService(new Intent(this, ForeGroundService.class));
    }


    public void testDialog(View view) {
//        new WebDialog().show(getSupportFragmentManager(),"");
        new BuyVipSuccessDialog(this).show();
//        new InputDialog(this).show();
    }

    public void testWebView(View view) {
        startActivity(new Intent(this, WebViewTestActivity.class));
    }

    public void loadBitmap(View view) {
        Glide.with(this)
                .load("http://n.sinaimg.cn/tech/transform/785/w413h372/20191230/4ea5-imkzenp8136538.gif")
                .placeholder(R.mipmap.ic_launcher_)
                .apply(new RequestOptions().transform(new GlideRoundTransform(ViewUtils.dipToPx(view.getContext(),8))))
                .into(ivDisplay);
    }


    public void testAnimation(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_new_msg_bubble_enter));
    }

    public void testAnimator(final View view) {
        ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -view.getWidth(), 0).setDuration(500).start();
//        view.animate().translationX(0).setDuration(500).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                view.setVisibility(View.VISIBLE);
//                view.setTranslationX(-view.getWidth());
//            }
//        }).start();

    }

    public void showBottomShapeFragment(View view) {
        BottomContainerDialogFragment.newInstance(ViewUtils.dipToPx(this, 20), true,
                true, BlankFragment.newInstance())
                .show(getSupportFragmentManager(), "dialog");
    }

    public void showPopup(View view) {
        ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        ContributeItemAdapter contributeItemAdapter = new ContributeItemAdapter();
        ArrayList<ContributeItemData> contributeItemDataList = new ArrayList<>();
        contributeItemDataList.add(new ContributeItemData("A", "TitleA", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "A", Toast.LENGTH_SHORT).show();
            }
        }));
        contributeItemDataList.add(new ContributeItemData("B", "TitleB", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "B", Toast.LENGTH_SHORT).show();
            }
        }));
        contributeItemDataList.add(new ContributeItemData("C", "TitleC", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "C", Toast.LENGTH_SHORT).show();
            }
        }));
        contributeItemAdapter.setData(contributeItemDataList);
        listPopupWindow.setAdapter(contributeItemAdapter);
        listPopupWindow.setAnchorView(view);
        listPopupWindow.setWidth(ViewUtils.dipToPx(this, 250));
        listPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setDropDownGravity(Gravity.END);
        listPopupWindow.setModal(true);
        listPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.topic_10dp_solid_4c000000));
        listPopupWindow.setHorizontalOffset(ViewUtils.dipToPx(this, 0));//相对锚点偏移值，正值表示向右偏移
        listPopupWindow.setVerticalOffset(ViewUtils.dipToPx(this, 4));
        listPopupWindow.show();

    }
}
