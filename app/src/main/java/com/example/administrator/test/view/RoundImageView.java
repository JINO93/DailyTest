package com.example.administrator.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.administrator.test.R;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 11:39 2019/8/16
 */
public class RoundImageView extends AppCompatImageView {

    private Paint paint;
    private Paint paint2;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mRoundPath;

    private Paint strokePaint;
    private int strokeWidthPx;
    private int strokeColor = 0xFFFFFF;

    private Xfermode mClear = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    private Xfermode mDstOut = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundImageView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        strokeWidthPx = a.getDimensionPixelOffset(R.styleable.RoundImageView_strokeWidth, 2);
        strokeColor = a.getColor(R.styleable.RoundImageView_strokeColor, strokeColor);
        a.recycle();

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setXfermode(mDstOut);

        paint2 = new Paint();

        mRoundPath = new Path();

        strokePaint = new Paint();
        strokePaint.setAntiAlias(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mCanvas != null) {
            mCanvas.setBitmap(null);
            mCanvas = null;
        }
        recycleBitmap();
    }

    private void recycleBitmap() {
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        if (mBitmap != null && mBitmap.getWidth() == w && mBitmap.getHeight() == h) {
            return;
        } else {
            if (w > 0 && h > 0) {
                recycleBitmap();
                try {
                    mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                    if (mCanvas != null) {
                        mCanvas.setBitmap(mBitmap);
                    } else {
                        mCanvas = new Canvas(mBitmap);
                    }
                } catch (Exception e) {
//                    Ln.e(e);
                } catch (Throwable t) {
//                    Ln.e(t);
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (mCanvas != null) {

            paint.setXfermode(mClear);
            mCanvas.drawPaint(paint);
            super.draw(mCanvas);
            paint.setXfermode(mDstOut);
            mRoundPath.reset();

            mRoundPath.addOval(new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom()), Path.Direction.CW);
            mRoundPath.addRect(new RectF(0, 0, getWidth(), getHeight()), Path.Direction.CW);
            mRoundPath.setFillType(Path.FillType.EVEN_ODD);
            mCanvas.drawPath(mRoundPath, paint);

            if (mBitmap != null && !mBitmap.isRecycled()) {
                canvas.drawBitmap(mBitmap, 0, 0, paint2);
            }
        } else {
            super.draw(canvas);
        }

        // 绘制外圈
        if (strokeColor != 0) {

            float center = getWidth() / 2f;

            if (strokeWidthPx != 0) {
                this.strokePaint.setStyle(Paint.Style.STROKE);
                this.strokePaint.setStrokeWidth(strokeWidthPx);
            }

            this.strokePaint.setColor(strokeColor);

            if (strokeWidthPx > 0) {
                canvas.drawCircle(center, center, center - strokeWidthPx / 2f, this.strokePaint);
            }
        }
    }

    /**
     * @param color
     * @param width px
     */
    public void setStroke(int color, int width) {
        strokeColor = color;
        this.strokeWidthPx = width;
    }
}
