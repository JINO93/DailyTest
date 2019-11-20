package com.example.administrator.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 21:08 2019/11/12
 */
public class OvalLine extends View {

    private Paint linePaint;
    private Path linePath;

    public OvalLine(Context context) {
        this(context, null);
    }

    public OvalLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public OvalLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.RED);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LinearGradient linearGradient = new LinearGradient(0, 0, w, 0,
                new int[]{Color.parseColor("#00fec665"),
                        Color.parseColor("#80ffc664"),
                        Color.parseColor("#e6ffc663"),
                        Color.parseColor("#80ffc663"),
                        Color.parseColor("#00ffc663")},
//                new int[]{Color.RED,
//                        Color.GREEN,
//                        Color.RED},
//                        new float[]{0,0.3f,1f},
                new float[]{0,0.28f,0.51f,0.73f,1f},
                Shader.TileMode.CLAMP);
        linePaint.setShader(linearGradient);
        linePath = new Path();
        linePath.moveTo(0, h / 2f);
        linePath.quadTo(w / 2f, 0, w, h / 2f);
        linePath.quadTo(w / 2f, h, 0, h / 2f);
        linePath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(linePath, linePaint);
    }
}
