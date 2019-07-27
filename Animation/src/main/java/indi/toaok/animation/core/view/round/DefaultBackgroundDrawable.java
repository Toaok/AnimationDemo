package indi.toaok.animation.core.view.round;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

/**
 * @author Toaok
 * @version 1.0  2019/7/27.
 */
public class DefaultBackgroundDrawable extends Drawable {
    int mHeight;
    int mWidth;
    Paint mPaint;
    Rect mRect;
    int mAlpha = 255;

    int[] mColors;

    public DefaultBackgroundDrawable(int width, int height) {
        mWidth = width;
        mHeight = height;
        init();
    }

    private void init() {
        generateColors();
        mPaint = new Paint();
        LinearGradient backGradient = new LinearGradient(0, 0, 0, mHeight,mColors, null, Shader.TileMode.CLAMP);
        mPaint.setShader(backGradient);
        mRect = new Rect(0, 0, mWidth, mHeight);
    }

    private void generateColors() {
        mColors = new int[256 * 256 * 256];
        for (int r = 0; r <= 255; r++) {
            for (int g = 0; g <= 255; g++) {
                for (int b = 0; b <= 255; b++) {
                    mColors[r + g + b] = Color.argb(0, r, g, b);
                }
            }
        }
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(mRect, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mAlpha = alpha;
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter filter) {
        mPaint.setColorFilter(filter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
