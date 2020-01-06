package indi.toaok.animation.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
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
public class GatewayAnimationView extends AppCompatImageView {

    static final int CIRCLE_DIAMETER = 20;

    //最小的弧度
    int minArcAngle;
    //最小的间隔角度
    int minSpaceAngle;
    //真实的间隔角度
    int realSpaceAngle;
    //真实的起始角度
    int realStartAngle;

    int sum = 2;

    int paidding;

    int defaultSize;

    int arcWidth;


    int gradientStartColor;
    int gradientEndColor;

    Paint mPaint;
    SweepGradient mGradient;
    Matrix gradientMatrix;

    AnimationSet mAnimationSet;
    DrawAnimation mDrawAnimation;
    RotateAnimation mRotateAnimation;


    public GatewayAnimationView(Context context) {
        this(context, null);
    }

    public GatewayAnimationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GatewayAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        minArcAngle = 3;
        minSpaceAngle = 25;
        realSpaceAngle = minSpaceAngle;
        realStartAngle = 0;
        paidding = 10;
        arcWidth = 4;
        defaultSize = MeasureUtil.dip2px(getContext(), CIRCLE_DIAMETER);

        gradientStartColor=Color.BLACK;
        gradientEndColor=Color.WHITE;

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(MeasureUtil.dip2px(getContext(), arcWidth));
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mGradient = new SweepGradient(0,0,gradientStartColor,gradientEndColor);
        gradientMatrix = new Matrix();
        mGradient.setLocalMatrix(gradientMatrix);
        mPaint.setShader(mGradient);


        initAnimation();
    }

    private void initAnimation() {
        mDrawAnimation = new DrawAnimation();
        mDrawAnimation.setInterpolator(new DecelerateInterpolator());
        mDrawAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(new DecelerateInterpolator());
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mAnimationSet = new AnimationSet(true);
        mAnimationSet.addAnimation(mDrawAnimation);
        mAnimationSet.addAnimation(mRotateAnimation);
        mAnimationSet.setRepeatMode(Animation.RESTART);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(defaultSize + arcWidth, heightMeasureSpec + arcWidth);
        int width = measureSize(defaultSize + arcWidth, widthMeasureSpec + arcWidth);
        int min = Math.max(width, height);// 获取View最短边的长度
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int withSpaceSweep = 360 / sum;
        int withOutSpaceSweep = withSpaceSweep - realSpaceAngle;

        canvas.save();
        RectF rect = new RectF(0 + paidding, 0 + paidding, getWidth() - paidding, getHeight() - paidding);//绘制的最终区域，一定填满
        gradientMatrix.setTranslate(getWidth()/2,getHeight()/2);
        mGradient.setLocalMatrix(gradientMatrix);
        for (int i = 0; i < sum; i++) {
            canvas.drawArc(rect, realStartAngle, withOutSpaceSweep, false, mPaint);
            realStartAngle += withSpaceSweep;
        }
        canvas.restore();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    public void startAnimation() {
        mAnimationSet.setDuration(3000);
        this.startAnimation(mAnimationSet);
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
