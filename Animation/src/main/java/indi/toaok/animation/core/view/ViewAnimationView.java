package indi.toaok.animation.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;

import indi.toaok.animation.utils.MeasureUtil;

/**
 * @author Toaok
 * @version 1.0  2019/7/18.
 */
public class ViewAnimationView extends View implements Animation.AnimationListener {

    //最小的间隔角度
    int minSpaceAngle;
    //真实的间隔角度
    int realSpaceAngle;
    //真实的起始角度
    int realStartAngle;

    int sum = 3;

    int paidding;

    int defaultSize;

    Paint mPaint;
    AnimationSet mAnimationSet;
    DrawAnimation mDrawAnimation;
    RotateAnimation mRotateAnimation;

    public ViewAnimationView(Context context) {
        this(context, null);
    }

    public ViewAnimationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        minSpaceAngle = 15;
        realSpaceAngle = minSpaceAngle;
        realStartAngle = 0;
        paidding = 10;
        defaultSize = MeasureUtil.dip2px(getContext(), 50);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(12);

        mDrawAnimation = new DrawAnimation();
        mDrawAnimation.setInterpolator(new DecelerateInterpolator());
        mDrawAnimation.setRepeatCount(1);
        mDrawAnimation.setAnimationListener(this);
        mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(new DecelerateInterpolator());
        mRotateAnimation.setRepeatCount(1);
        mRotateAnimation.setAnimationListener(this);
        mAnimationSet = new AnimationSet(true);
        mAnimationSet.addAnimation(mDrawAnimation);
        mAnimationSet.addAnimation(mRotateAnimation);
        mAnimationSet.setRepeatMode(Animation.REVERSE);

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
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int withSpaceSweep = 360 / sum;
        int withOutSpaceSweep = withSpaceSweep - realSpaceAngle;

        RectF rect = new RectF(0 + paidding, 0 + paidding, getWidth() - paidding, getHeight() - paidding);//绘制的最终区域，一定填满
        for (int i = 0; i < sum; i++) {
            canvas.drawArc(rect, realStartAngle, withOutSpaceSweep, false, mPaint);
            realStartAngle += withSpaceSweep;
        }
    }


    public void startAnimation(int time) {
        mAnimationSet.setDuration(time);
        this.startAnimation(mAnimationSet);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public class DrawAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            int calculationSpace = (int) (interpolatedTime * 360 / sum);

            if (calculationSpace > 360 / sum - minSpaceAngle) {
                realSpaceAngle = 360 / sum - minSpaceAngle;
            } else if (calculationSpace > minSpaceAngle) {
                realSpaceAngle = calculationSpace;
            } else {
                realSpaceAngle = minSpaceAngle;
            }
            realStartAngle = 360 / sum + realSpaceAngle / 2;
            postInvalidate();
        }
    }


}
