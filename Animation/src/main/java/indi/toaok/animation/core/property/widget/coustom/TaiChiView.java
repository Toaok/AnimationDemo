package indi.toaok.animation.core.property.widget.coustom;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import indi.toaok.animation.utils.MeasureUtil;

/**
 * @author Toaok
 * @version 1.0  2019/7/24.
 */
public class TaiChiView extends View {


    static final int CIRCLE_DIAMETER = 200;

    private Paint mBlackPaint;
    private Paint mWhitePaint;

    private int paidding;

    int defaultSize;

    public TaiChiView(Context context) {
        this(context, null);
    }

    public TaiChiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaiChiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        defaultSize = MeasureUtil.dip2px(getContext(), CIRCLE_DIAMETER);

        mBlackPaint = new Paint();
        mBlackPaint.setStyle(Paint.Style.FILL);
        mBlackPaint.setColor(Color.BLACK);
        mBlackPaint.setAntiAlias(true);
        mBlackPaint.setDither(true);
        mBlackPaint.setStrokeCap(Paint.Cap.ROUND);

        mWhitePaint = new Paint();
        mWhitePaint.setStyle(Paint.Style.FILL);
        mWhitePaint.setColor(Color.WHITE);
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setDither(true);
        mWhitePaint.setStrokeCap(Paint.Cap.ROUND);

        paidding = 10;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(defaultSize, heightMeasureSpec);
        int width = measureSize(defaultSize, widthMeasureSpec);
        int min = Math.min(width, height);// 获取View最短边的长度
        setMeasuredDimension(min, min);// 强制改View为以最短边为长度的正方形
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        canvas.save();
        canvas.drawBitmap(drawTaiChi(width, height), 0, 0, null);
        canvas.restore();
    }


    private Bitmap drawTaiChi(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        int radius = Math.min(width, height) / 2 - paidding;
        //画布移动到中心
        canvas.translate(width / 2, height / 2);

        RectF mainRectF = new RectF(-radius, -radius, radius, radius);//绘制的最终区域，一定填满

        final float blackStartAngle = 90;
        final float blackSweepAngle = 180;

        final float whiteStartAngle = -90;
        final float whitwSweepAngle = 180;
        //绘制两个半圆
        canvas.drawArc(mainRectF, blackStartAngle, blackSweepAngle, true, mBlackPaint);
        canvas.drawArc(mainRectF, whiteStartAngle, whitwSweepAngle, true, mWhitePaint);

        int smallRadius = radius / 2;

        //绘制两小圆
        RectF smallBlackRectF = new RectF(-smallRadius, 0, smallRadius, smallRadius * 2);
        canvas.drawArc(smallBlackRectF, whiteStartAngle, whitwSweepAngle, true, mBlackPaint);
        RectF smallWhiteRectF = new RectF(-smallRadius, -smallRadius * 2, smallRadius, 0);
        canvas.drawArc(smallWhiteRectF, blackStartAngle, blackSweepAngle, true, mWhitePaint);

        //绘制两个鱼眼
        canvas.drawCircle(0, -smallRadius, smallRadius / 4, mBlackPaint);
        canvas.drawCircle(0, smallRadius, smallRadius / 4, mWhitePaint);
        return bitmap;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation(5000);
    }

    @SuppressLint("WrongConstant")
    public void startAnimation(int durationMillis) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(durationMillis);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(Animation.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int rotation = (int) ((float) valueAnimator.getAnimatedValue() * 360);
                setRotation(rotation);
            }
        });
        valueAnimator.start();
    }
}
