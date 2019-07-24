package indi.toaok.animation.core.property.widget.coustom;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import indi.toaok.animation.MeasureUtil;

/**
 * @author hpp
 * @version 1.0  2019/7/18.
 */
public class PropertyAnimationWidget extends View {


    static final int CIRCLE_DIAMETER = 40;


    //最小的弧度
    int minArcAngle;
    //最小的间隔角度
    int minSpaceAngle;
    //真实的间隔角度
    int realSpaceAngle;
    //真实的起始角度
    int realStartAngle;

    int sum = 3;

    int paidding;

    int defaultSize;

    boolean isPositive = true;

    Paint mPaint;
    AnimatorSet mPositiveAnimatorSet;
    AnimatorSet mNegativeAnimatorSet;


    ObjectAnimator mPositiveValueAnimator;
    ObjectAnimator mNegativeValueAnimator;

    ObjectAnimator mPositiveAlphaAnimator;
    ObjectAnimator mNegativeAlphaAnimator;

    ObjectAnimator mPositiveTranslationAnimator;
    ObjectAnimator mNegativeTranslationAnimator;

    ObjectAnimator mPositiveRotationAnimator;
    ObjectAnimator mNegativeRotationAnimator;


    public PropertyAnimationWidget(Context context) {
        this(context, null);
    }

    public PropertyAnimationWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PropertyAnimationWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        minArcAngle = 3;
        minSpaceAngle = 25;
        realSpaceAngle = minSpaceAngle;
        realStartAngle = 0;
        paidding = 10;
        defaultSize = MeasureUtil.dip2px(getContext(), CIRCLE_DIAMETER);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(MeasureUtil.dip2px(getContext(),4));
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        initAnimation();
    }

    @Override
    public void setTranslationZ(float translationZ) {
        super.setTranslationZ(translationZ);
    }

    @SuppressLint("ObjectAnimatorBinding")
    private void initAnimation() {
        mPositiveRotationAnimator = ObjectAnimator.ofFloat(this, "rotation", 0, 720);
        mPositiveRotationAnimator.setInterpolator(new DecelerateInterpolator());

        mPositiveAlphaAnimator = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f);
        mPositiveAlphaAnimator.setInterpolator(new DecelerateInterpolator());

        mPositiveTranslationAnimator=ObjectAnimator.ofFloat(this, "translationY", 0f, 200f);
        mPositiveTranslationAnimator.setInterpolator(new DecelerateInterpolator());

        mPositiveValueAnimator = ObjectAnimator.ofInt(this, "spaceAngle", minSpaceAngle, 360 / sum - minArcAngle);
        mPositiveValueAnimator.setInterpolator(new DecelerateInterpolator());

        mPositiveAnimatorSet = new AnimatorSet();
        mPositiveAnimatorSet.setInterpolator(new DecelerateInterpolator());
        mPositiveAnimatorSet.playTogether(mPositiveRotationAnimator,mPositiveAlphaAnimator, mPositiveTranslationAnimator,mPositiveValueAnimator);


        mNegativeRotationAnimator = ObjectAnimator.ofFloat(this, "rotation", 720, 0);
        mNegativeRotationAnimator.setInterpolator(new DecelerateInterpolator());

        mNegativeAlphaAnimator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
        mNegativeAlphaAnimator.setInterpolator(new DecelerateInterpolator());

        mNegativeTranslationAnimator=ObjectAnimator.ofFloat(this, "translationY", 200f, 0f);
        mNegativeTranslationAnimator.setInterpolator(new DecelerateInterpolator());

        mNegativeValueAnimator = ObjectAnimator.ofInt(this, "spaceAngle", 360 / sum - minArcAngle, minSpaceAngle);
        mNegativeValueAnimator.setInterpolator(new DecelerateInterpolator());

        mNegativeAnimatorSet = new AnimatorSet();
        mNegativeAnimatorSet.setInterpolator(new DecelerateInterpolator());
        mNegativeAnimatorSet.playTogether(mNegativeRotationAnimator,mNegativeAlphaAnimator, mNegativeTranslationAnimator,mNegativeValueAnimator);
    }

    private void initStartAnimator(){

    }

    private void initRefreshingAnimator(){

    }
    private void initFinishAnimator(){

    }


    /**
     * 用于ObjectAnimator回调
     * ObjectAnimator中的propertyName
     *
     * @param spaceAngle
     */
    public void setSpaceAngle(int spaceAngle) {
        if (spaceAngle > 360 / sum - minArcAngle) {
            realSpaceAngle = 360 / sum - minArcAngle;
        } else if (spaceAngle > minSpaceAngle) {
            realSpaceAngle = spaceAngle;
        } else {
            realSpaceAngle = minSpaceAngle;
        }
        realStartAngle = 360 / sum + realSpaceAngle / 2;
        postInvalidate();
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int withSpaceSweep = 360 / sum;
        int withOutSpaceSweep = withSpaceSweep - realSpaceAngle;


        canvas.save();
        RectF rect = new RectF(0 + paidding, 0 + paidding, getWidth() - paidding, getHeight() - paidding);//绘制的最终区域，一定填满
        for (int i = 0; i < sum; i++) {
            canvas.drawArc(rect, realStartAngle, withOutSpaceSweep, false, mPaint);
            realStartAngle += withSpaceSweep;
        }
        canvas.restore();
    }


    public void startAnimation(int time) {

        if (isPositive) {
            mPositiveAnimatorSet.setDuration(time);
            mPositiveAnimatorSet.start();
        } else {
            mNegativeAnimatorSet.setDuration(time);
            mNegativeAnimatorSet.start();
        }
        isPositive = !isPositive;
    }
}
