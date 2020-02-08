package indi.toaok.animation.core.property.widget.coustom;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import indi.toaok.animation.R;
import indi.toaok.animation.utils.MeasureUtil;

/**
 * @author Toaok
 * @version 1.0  2019/7/18.
 */
public class GatawayTestAnimationView extends View {

    //默认大小
    static final int DEFAULT_SIZE = 40;

    //间隔角度
    float[] mSpaceAngles;
    //起始角度
    float mStartAngle;
    //圆弧的宽度
    float mArcWidth;
    //圆弧的个数
    int mCount;
    int mArcStartColor;
    int mArcEndColor;
    //是否是顺时针
    boolean isClockwise;


    //控件默认大小
    int defaultSize;
    //边距
    int paidding;

    Paint mPaint;
    SweepGradient mGradient;
    Matrix gradientMatrix;

    ObjectAnimator mAnimator;

    public GatawayTestAnimationView(Context context) {
        this(context, null);
    }

    public GatawayTestAnimationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GatawayTestAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        initPaint();
        initGradient();
        initAnimation();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GatawayTestAnimationView);
        mStartAngle = a.getInteger(R.styleable.GatawayTestAnimationView_startAngle, 0);
        mArcWidth = a.getDimension(R.styleable.GatawayTestAnimationView_arcWidth, 5);
        mArcStartColor = a.getColor(R.styleable.GatawayTestAnimationView_arcStartColor, ContextCompat.getColor(context, R.color.colorPrimary));
        mArcEndColor = a.getColor(R.styleable.GatawayTestAnimationView_arcEndColor, ContextCompat.getColor(context, R.color.colorPrimary));
        isClockwise = a.getBoolean(R.styleable.GatawayTestAnimationView_isClockwise, true);
        String spaceAngles = a.getString(R.styleable.GatawayTestAnimationView_spaceAngles);
        a.recycle();
        initSpaceArgles(spaceAngles);
        paidding = (int) (mArcWidth * 2);
        defaultSize = MeasureUtil.dip2px(getContext(), DEFAULT_SIZE);
    }

    private void initSpaceArgles(String spaceAngles) {
        if (TextUtils.isEmpty(spaceAngles)) {
            spaceAngles = "";
        }
        String[] angles = spaceAngles.split(",");
        mSpaceAngles = new float[angles.length];
        for (int i = 0; i < angles.length; i++) {
            try {
                mSpaceAngles[i] = Float.parseFloat(angles[i]);
            } catch (NumberFormatException e) {
                mSpaceAngles[i] = 0;
            }
        }
        mCount = mSpaceAngles.length;

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(MeasureUtil.dip2px(getContext(), mArcWidth));
    }

    private void initGradient() {
        int[] colors = new int[mCount * 2];
        float[] postions = new float[mCount * 2];

        //总间距
        float spaceAngleSum = 0;
        for (float i : mSpaceAngles) {
            spaceAngleSum += i;
        }

        //每段弧的角度(去掉间距)
        float withOutSpaceSweep = (360 - spaceAngleSum) / mCount;

        spaceAngleSum = 0;
        for (int i = 0; i < mCount; i++) {
            colors[i << 1] = mArcStartColor;
            colors[(i << 1) + 1] = mArcEndColor;
            spaceAngleSum += mSpaceAngles[i];
            postions[i << 1] = (mStartAngle + withOutSpaceSweep * i + spaceAngleSum) / 360f;
            postions[(i << 1) + 1] = (mStartAngle + withOutSpaceSweep * (i + 1) + spaceAngleSum) / 360f;
        }

        mGradient = new SweepGradient(0, 0, colors, postions);
        gradientMatrix = new Matrix();
        mGradient.setLocalMatrix(gradientMatrix);
        mPaint.setShader(mGradient);
    }

    private void initAnimation() {
        int angle = 360;
        mAnimator = ObjectAnimator.ofFloat(this, "rotation", 0, isClockwise ? angle * 1 : angle * -1);
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

        //总间距
        float spaceAngleSum = 0;
        for (float i : mSpaceAngles) {
            spaceAngleSum += i;
        }

        //每段弧的角度(去掉间距)
        float withOutSpaceSweep = (360 - spaceAngleSum) / mCount;

        gradientMatrix.setTranslate(getWidth() / 2, getHeight() / 2);
        mGradient.setLocalMatrix(gradientMatrix);
        RectF rect = new RectF(0 + paidding, 0 + paidding, getWidth() - paidding, getHeight() - paidding);//绘制的最终区域，一定填满
        canvas.save();
        for (int i = 0; i < mCount; i++) {
            canvas.drawArc(rect, mStartAngle += mSpaceAngles[i], withOutSpaceSweep, false, mPaint);
            mStartAngle += withOutSpaceSweep;
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
