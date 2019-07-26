package indi.toaok.animation.core.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import indi.toaok.animation.R;

/**
 * @author Toaok
 * @version 1.0  2019/7/26.
 */
public class RollImageView extends View {

    Bitmap mBitmap;
    Picture mPicture;

    int bitmapWidth;
    int bitmapHeight;

    Rect mBitmatSrcRect;
    Rect mBitmatDstRect;

    int top = 0;
    int bottom = 0;

    public RollImageView(Context context) {
        this(context, null);
    }

    public RollImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //注意：关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_beautiful_girl);
        mPicture = new Picture();
        bottom = top + bitmapHeight;
        bitmapWidth = mBitmap.getWidth();
        bitmapHeight = mBitmap.getHeight();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmatSrcRect = new Rect(0, 1000, bitmapWidth, bitmapHeight);
        mBitmatDstRect = new Rect(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mBitmatSrcRect, mBitmatDstRect, null);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimator();
    }

    @SuppressLint("WrongConstant")
    private void startAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                top = (int) ((float) valueAnimator.getAnimatedValue()* -10);
                postInvalidate();
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(Animation.RESTART);
        valueAnimator.start();
    }

}
