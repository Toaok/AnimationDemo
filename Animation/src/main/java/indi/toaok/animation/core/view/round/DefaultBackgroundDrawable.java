package indi.toaok.animation.core.view.round;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.Nullable;

import indi.toaok.animation.utils.MeasureUtil;

/**
 * @author Toaok
 * @version 1.0  2019/7/27.
 */
public class DefaultBackgroundDrawable extends Drawable {

    private static final int[] GRAD_COLORS = new int[]{Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA, Color.RED};
    private static final int[] GRAD_ALPHA = new int[]{Color.BLACK, Color.WHITE, Color.BLACK};

    private Context mContext;

    private Shader mShader;

    private Paint mPaint;
    private Paint mPaintBackground;
    private RectF mGradientRect;
    private float mRadius = 0;

    int mAlpha = 255;

    public DefaultBackgroundDrawable(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBackground.setColor(Color.WHITE);
    }


    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mGradientRect = new RectF(0, 0, getIntrinsicWidth(), getIntrinsicHeight());
        LinearGradient gradientShader = new LinearGradient(mGradientRect.left, mGradientRect.top, mGradientRect.left, mGradientRect.bottom, GRAD_COLORS, null, Shader.TileMode.CLAMP);
        LinearGradient alphaShader = new LinearGradient(mGradientRect.left, mGradientRect.top, mGradientRect.right, mGradientRect.top, GRAD_ALPHA, null, Shader.TileMode.CLAMP);
        mShader = new ComposeShader(alphaShader, gradientShader, PorterDuff.Mode.MULTIPLY);
        mPaint.setShader(mShader);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.drawRoundRect(mGradientRect, mRadius, mRadius, mPaintBackground);
        canvas.drawRoundRect(mGradientRect, mRadius, mRadius, mPaint);
        canvas.restore();
    }


    @Override
    public int getIntrinsicWidth() {
        return MeasureUtil.getScreenWight(mContext);
    }

    @Override
    public int getIntrinsicHeight() {
        return MeasureUtil.getScreenHeight(mContext) * 2;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
        mAlpha = alpha;
        mPaint.setAlpha(mAlpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter filter) {
        mPaint.setColorFilter(filter);
        invalidateSelf();
    }

}
