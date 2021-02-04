package com.example.administrator.test.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.administrator.test.MyApplication;
import com.example.administrator.test.R;
import com.example.administrator.test.util.LogUtil;
import com.example.administrator.test.util.ViewUtils;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 16:19 2020/3/18
 */
public class TestScalePicFragment extends DialogFragment {

    @BindView(R.id.iv_scale)
    ImageView ivScale;
    @BindView(R.id.v_bottom_mask)
    View vBottomMask;
    private Unbinder unbinder;

    public static final String PIC_URL = "http://img21.mtime.cn/CMS/Gallery/2011/07/02/170837.97014036_{0}X{1}.jpg";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.SplashDialog);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getDialog() != null) {
            Window window = getDialog().getWindow();
//            if (window != null) {
//                window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            }
//            getDialog().getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                             View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                             |View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE
//            );
//            transparencyBar(window);
        }
    }

    public static void transparencyBar(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.BLACK);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_test_pic_scale, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.with(this)
                .navigationBarAlpha(0.0f)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init();
        unbinder = ButterKnife.bind(this, view);
        Glide.with(getActivity())
                .asBitmap()
                .load(MessageFormat.format(PIC_URL,  "1242","2688"))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        updateTextureViewSize(resource);
//                        int displayWeight = ViewUtils.getDisplayWeight(MyApplication.getContext());
//                        int displayHeight = getScreenHeight();
//                        LogUtil.d(MessageFormat.format("load pic success,pW:{0}  pH:{1}  sW:{2}  sH  {3}",
//                                resource.getWidth(),
//                                resource.getHeight(),
//                                displayWeight,
//                                displayHeight
//                        ));
//                        float radio = displayHeight*1.0f / displayWeight;
//                        if (radio > 18f/9f) {
//                            resource = getBitmapByScaleWidth(resource, displayWeight);
//                            LogUtil.d(MessageFormat.format("convert pic success,pW:{0}  pH:{1}  sW:{2}  sH  {3}",
//                                    resource.getWidth(),
//                                    resource.getHeight(),
//                                    displayWeight,
//                                    displayHeight
//                            ));
//                        }else if(radio > 16f/9f || radio < 16f/9f){
//                            resource = getZoomedBitmap(resource, displayWeight,
//                                    displayHeight);
//                            LogUtil.d(MessageFormat.format("convert pic success,pW:{0}  pH:{1}  sW:{2}  sH  {3}",
//                                    resource.getWidth(),
//                                    resource.getHeight(),
//                                    displayWeight,
//                                    displayHeight
//                            ));
//                            vBottomMask.setVisibility(View.INVISIBLE);
//                        }
////                        Toast.makeText(getContext(),"load pic success",Toast.LENGTH_SHORT).show();
//                        ViewGroup.LayoutParams layoutParams = ivScale.getLayoutParams();
//                        layoutParams.height = resource.getHeight();
//                        ivScale.setLayoutParams(layoutParams);
//                        ivScale.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
////                .dontTransform()
//                .addListener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
////                        Toast.makeText(getContext(),"load pic failed",Toast.LENGTH_SHORT).show();
//                        LogUtil.e("load pic failed : "+e.getMessage());
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        LogUtil.d(MessageFormat.format("load pic success,pW:{0}  pH:{1}  sW:{2}  sH  {3}",
//                                resource.getIntrinsicWidth(),
//                                resource.getIntrinsicHeight(),
//                                ViewUtils.getDisplayWeight(MyApplication.getContext()),
//                                ViewUtils.getDisplayHeight(MyApplication.getContext())
//                        ));
////                        Toast.makeText(getContext(),"load pic success",Toast.LENGTH_SHORT).show();
//                        ivScale.setImageDrawable(resource);
//                        return true;
//                    }
//                })
//                .into(ivScale);
    }

    public void updateTextureViewSize(Bitmap bitmap) {
        float viewWidth = com.yibasan.lizhifm.sdk.platformtools.ui.ViewUtils.getDisplayWidth(getContext());
        float viewHeight = com.yibasan.lizhifm.sdk.platformtools.ui.ViewUtils.getDisplayHeight(getContext());
        viewWidth = 720;
        viewHeight = 1520;
        int videoWidth = bitmap.getWidth();
        int videoHeight = bitmap.getHeight();


        float scaleX = 1.0f;
        if (videoWidth > viewWidth ) {
            scaleX = videoWidth / (viewWidth*1.0f);
        } else if (videoWidth < viewWidth ) {
            scaleX = (viewWidth*1.0f) / videoWidth;
        }
        int pivotPointX =(int) (viewWidth / 2);
        int pivotPointY= (int) (viewHeight / 2);
//        this.videoHeight = (int) (videoHeight * scaleX);

        Matrix matrix = new Matrix();
        matrix.preTranslate((viewWidth - videoWidth) / 2, (viewHeight - videoHeight) / 2);
        matrix.preScale(videoWidth /viewWidth, videoHeight / viewHeight);
        matrix.postScale(scaleX, scaleX, pivotPointX, pivotPointY);
//        setTransform(matrix);
        Bitmap outBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        ivScale.setImageBitmap(outBitmap);
//        postInvalidate();
    }

    private int getScreenHeight(){
        Point size = new Point();
        getDialog().getWindow().getWindowManager().getDefaultDisplay().getRealSize(size);
        LogUtil.d("screen:"+size);
        return size.y;
    }

    public static Bitmap getBitmapByScaleWidth(Bitmap bgimage, float needWidth){
        float width = bgimage.getWidth();
        float height =bgimage.getHeight();
        Matrix matrix = new Matrix();
        float ratio = needWidth * 1.0f / width;
        matrix.postScale(ratio,ratio);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    public static Bitmap getZoomedBitmap(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
