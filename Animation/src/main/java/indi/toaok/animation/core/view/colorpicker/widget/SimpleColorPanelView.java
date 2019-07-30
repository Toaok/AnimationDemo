package indi.toaok.animation.core.view.colorpicker.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 颜色选择面板，参见：https://github.com/jbruchanov/AndroidColorPicker
 *
 * @author 李玉江[QQ:1023694760]
 * @since 2015/7/20
 */
public class SimpleColorPanelView extends View {
    private static final int[] GRAD_COLORS = new int[]{Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA, Color.RED};
    private static final int[] GRAD_ALPHA = new int[]{Color.BLACK, Color.WHITE, Color.TRANSPARENT};

    private Shader mShader;
    private Paint mPaint;
    private Paint mPaintBackground;
    private RectF mGradientRect = new RectF();

    private float mRadius = 0;

    public SimpleColorPanelView(Context context) {
        super(context);
        init();
    }

    public SimpleColorPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleColorPanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setClickable(true);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBackground.setColor(Color.WHITE);
        setLayerType(View.LAYER_TYPE_SOFTWARE, isInEditMode() ? null : mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShader != null) {
            canvas.drawRoundRect(mGradientRect, mRadius, mRadius, mPaintBackground);
            canvas.drawRoundRect(mGradientRect, mRadius, mRadius, mPaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mGradientRect.set(getPaddingLeft(), getPaddingTop(), right - left - getPaddingRight(), bottom - top - getPaddingBottom());
        if (changed) {
            buildShader();
        }
    }

    private void buildShader() {

        LinearGradient gradientShader = new LinearGradient(mGradientRect.left, mGradientRect.top, mGradientRect.left, mGradientRect.bottom, GRAD_COLORS, null, Shader.TileMode.CLAMP);
        LinearGradient alphaShader = new LinearGradient(mGradientRect.left, mGradientRect.top, mGradientRect.right, mGradientRect.top, GRAD_ALPHA, null, Shader.TileMode.CLAMP);
        mShader = new ComposeShader(alphaShader, gradientShader, PorterDuff.Mode.MULTIPLY);

        mPaint.setShader(mShader);
    }

}