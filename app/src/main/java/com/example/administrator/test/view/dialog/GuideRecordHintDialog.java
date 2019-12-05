package com.example.administrator.test.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.test.R;


/**
 * @Author: JINO
 * @Description: 领读榜提示弹窗
 * @Date: Create in 19:59 2019/11/14
 */
public class GuideRecordHintDialog extends Dialog {

    public GuideRecordHintDialog(@NonNull Context context) {
        super(context,R.style.BottomDialogTheme);
        configWindow(context);
        setContentView(R.layout.record_guide_record_hint_dialog);
        setCancelable(true);
        init();
    }

    private void init() {
        findViewById(R.id.iv_content).setOnClickListener(v -> {
            dismiss();
        });
    }

    private void configWindow(Context context) {
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        Window window = this.getWindow();
        if (window == null) {
            return;
        }
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (screenWidth * 0.867f);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setGravity(Gravity.CENTER);
    }
}
