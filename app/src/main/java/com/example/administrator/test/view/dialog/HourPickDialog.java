package com.example.administrator.test.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.test.R;
import com.example.administrator.test.util.ViewUtils;
import com.example.administrator.test.view.pickerview.DefaultCenterDecoration;
import com.example.administrator.test.view.pickerview.PickerDataSet;
import com.example.administrator.test.view.pickerview.PickerView;
import com.example.administrator.test.view.pickerview.adapter.ArrayWheelAdapter;

import java.util.ArrayList;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 20:03 2019/12/9
 */
public class HourPickDialog extends Dialog {

    private PickerView hourPicker;

    public HourPickDialog(@NonNull Context context) {
        super(context, R.style.BottomDialogTheme);
        configWindow(context);
        setContentView(R.layout.topic_hour_pick_dialog);
        init();
    }

    private void init() {
        hourPicker = findViewById(R.id.view_hour_pick);
        DefaultCenterDecoration decoration = new DefaultCenterDecoration(getContext());
        decoration.setLineColor(Color.parseColor("#0a000000"))
                //.setDrawable(Color.parseColor("#999999"))
                .setLineWidth(1)
                .setMargin(ViewUtils.dipToPx(getContext(), 10), ViewUtils.dipToPx(getContext(), -3),
                        ViewUtils.dipToPx(getContext(), 10), ViewUtils.dipToPx(getContext(), -3));
        hourPicker.setCenterDecoration(decoration);
        hourPicker.setColor(Color.BLACK, Color.BLACK);
        ArrayList<PickerDataSet> hourItems = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            hourItems.add(new HourData(i + 1));
        }
        hourPicker.setAdapter(new ArrayWheelAdapter(hourItems));
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
