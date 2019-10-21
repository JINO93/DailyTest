package com.example.administrator.test.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.test.R;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 14:21 2019/8/7
 */
public class InputDialog extends Dialog {

    private EditText etInput;

    public InputDialog(@NonNull Context context) {
        super(context, R.style.inputDialog);
        configWindow(context);
        setContentView(R.layout.dialog_input);
        init();
    }

    private void init() {
        etInput = findViewById(R.id.ed1);
        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), etInput.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configWindow(Context context) {
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        Window window = this.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = screenWidth;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
    }
}
