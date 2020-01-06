package indi.toaok.animation.core.property.widget.coustom;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import indi.toaok.animation.utils.MeasureUtil;

/**
 * @author Toaok
 * @version 1.0  2019/7/18.
 */
public class GatawayAnimationView extends View {

    static final int CIRCLE_DIAMETER = 40;

    //间隔角度
    float mSpaceAngle;
    //起始角度
    float mStartAngle;
    //圆弧的个数
    int mCount = 2;

    //控件默认大小
    int defaultSize;
    //边距
    int paidding;
    //圆弧的宽度
    float arcWidth;

    int gradientStartColor;
    int gradientEndColor;

    Paint mPaint;
    SweepGradient mGradient;
    Matrix gradientMatrix;

    ObjectAnimator mAnimator;

    public GatawayAnimationView(Context context) {
        this(context, null);
    }

    public GatawayAnimationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GatawayAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        arcWidth=10;
        mSpaceAngle = 0;
        mStartAngle = 0;
        paidding = (int) (arcWidth*2);
        defaultSize = MeasureUtil.dip2px(getContext(), CIRCLE_DIAMETER);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(MeasureUtil.dip2px(getContext(), arcWidth));
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        initGradient();
        initAnimation();
    }

    private void initGradient(){
        gradientStartColor = Color.WHITE;
        gradientEndColor = Color.BLUE;
        int[] colors=new int[mCount*2];
        float[] postions=new float[mCount*2];

        float withSpaceSweep = 360 / mCount;

        float withOutSpaceSweep = withSpaceSweep - mSpaceAngle;

        for(int i=0;i<mCount;i++){
            colors[i<<1]=gradientStartColor;
            colors[(i<<1)+1]=gradientEndColor;
            postions[i<<1]=(mStartAngle+withSpaceSweep*i)/360f;
            postions[(i<<1)+1]=(mStartAngle+withSpaceSweep*i+withOutSpaceSweep)/360f;
        }

        mGradient = new SweepGradient(0, 0, colors, postions);
        gradientMatrix = new Matrix();
        mGradient.setLocalMatrix(gradientMatrix);
        mPaint.setShader(mGradient);
    }

    private void initAnimation() {
        mAnimator = ObjectAnimator.ofFloat(this, "rotation", 0, 360);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setDuration(5000);
        mAnimator.setRepeatCount(-1);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
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

        float withSpaceSweep = 360 / mCount;

        float withOutSpaceSweep = withSpaceSweep - mSpaceAngle;

        canvas.save();
        RectF rect = new RectF(0 + paidding, 0 + paidding, getWidth() - paidding, getHeight() - paidding);//绘制的最终区域，一定填满
        gradientMatrix.setTranslate(getWidth() / 2, getHeight() / 2);
        mGradient.setLocalMatrix(gradientMatrix);

        for (int i = 0; i < mCount; i++) {
            canvas.drawArc(rect, mStartAngle, withOutSpaceSweep, false, mPaint);
            mStartAngle += withSpaceSweep;
        }
        canvas.restore();
        startAnimation();
    }


    public void startAnimation() {
        if (mAnimator != null) {
            mAnimator.start();
        }
    }
}
