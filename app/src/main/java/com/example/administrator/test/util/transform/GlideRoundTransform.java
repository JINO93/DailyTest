package com.example.administrator.test.util.transform;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 14:55 2019/12/31
 */
public class GlideRoundTransform extends BitmapTransformation {
    private float radius = 0.0F;

    public GlideRoundTransform(int radius) {
        this.radius = (float)radius;
    }

    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
        return roundCrop(pool, bitmap, this.radius);
    }

    private static Bitmap roundCrop(BitmapPool pool, Bitmap source, float radius) {
        if (source == null) {
            return null;
        } else {
            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0.0F, 0.0F, (float)source.getWidth(), (float)source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }
    }

    public String getId() {
        return this.getClass().getName() + Math.round(this.radius);
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(this.getId().getBytes(CHARSET));
    }

    public int hashCode() {
        return (int)((float)this.getClass().getName().hashCode() * this.radius);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof GlideRoundTransform) {
            return this.radius == ((GlideRoundTransform)obj).radius;
        } else {
            return super.equals(obj);
        }
    }
}
