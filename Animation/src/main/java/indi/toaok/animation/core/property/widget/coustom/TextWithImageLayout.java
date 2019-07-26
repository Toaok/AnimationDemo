package indi.toaok.animation.core.property.widget.coustom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.animation.LinearInterpolator;

import com.blankj.utilcode.util.SpanUtils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import indi.toaok.animation.MeasureUtil;
import indi.toaok.animation.core.property.widget.span.ForegroundAlphaColorSpan;
import indi.toaok.animation.core.property.widget.span.ForegroundAlphaColorSpanGroup;
import indi.toaok.animation.utils.LogUtil;

/**
 * @author Toaok
 * @version 1.0  2019/7/24.
 */
public class TextWithImageLayout extends ConstraintLayout {

    private static String sText = "这是一个针对技术开发者的一个应用，你可以在掘金上获取最新最优质的技术干货，不仅仅是Android知识、前端、后端以至于产品和设计都有涉猎，想成为全栈工程师的朋友不要错过！";

    private static String sRegex = "[\\x00-\\xff]+";

    private static int[] ATTRS = new int[]{
            android.R.attr.textSize,//16842901
            android.R.attr.textColor,//16842904
            android.R.attr.text//16843087
    };

    private static final int INDEX_ATTR_TEXT_SIZE = 0;
    private static final int INDEX_ATTR_TEXT_COLOR = 1;
    private static final int INDEX_ATTR_TEXT = 2;

    //绘制文本的画笔
    private TextPaint mTextPaint;

    //文字大小px
    private int mTextSize;
    //文本颜色
    private int mTextColor;
    //文本
    private CharSequence mText;

    //span工具
    SpanUtils mSpanUtils;
    //每个字的span的集合
    ForegroundAlphaColorSpanGroup mForegroundAlphaColorSpanGroup;
    //数值动画
    ValueAnimator valueAnimator;

    //存储绘制Text的layout
    SparseArray<SparseArray<StaticLayout>> mLayoutArray;

    public TextWithImageLayout(Context context) {
        this(context, null);
    }

    public TextWithImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextWithImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        textLayout(getWidth(), getHeight());
    }

    private void textLayout(int width, int height) {
        //每个汉字的宽
        int wordWidth = (int) getFontWight(mTextPaint, mText);
        //每个汉字的高
        int wordHeight = (int) getFontHeight(mTextPaint);

        //根据高来算出每行显示多少文字（垂直方向上）
        int rowWords = height / wordHeight;

        //根据高来算出有多少行（垂直方向上）
        int rows = (int) Math.ceil((mText.length() * 1.0) / rowWords);

        if (width > wordWidth && height > wordHeight) {
            mLayoutArray = new SparseArray<>();
            for (int row = 0; row < rows; row++) {
                //行字符串
                CharSequence rowSequence;
                if ((row + 1) * rowWords < mText.length()) {
                    rowSequence = mText.subSequence(row * rowWords, (row + 1) * rowWords);
                } else {
                    rowSequence = mText.subSequence(row * rowWords, mText.length());
                }
//                rowSequence = "这是一个针对技术开发者的一个应用，你可以在掘金上获取";
                LogUtil.e(rowSequence.toString());
                String[] rowStrings = getStrings(rowSequence.toString(), sRegex);
                LogUtil.e(Arrays.asList(rowStrings).toString());
                if (rowStrings != null) {

                    SparseArray<StaticLayout> rowLayouts = new SparseArray<>();
                    for (int column = 0; column < rowStrings.length; column++) {
                        String s = rowStrings[column];
                        if (!TextUtils.isEmpty(s)) {
                            int start = rowSequence.toString().indexOf(s);
                            int end = start + s.length();
                            StaticLayout staticLayout;
                            if (isMatcher(s, sRegex)) {
                                staticLayout = generateStaticLayout(rowSequence.subSequence(start, end), (int) mTextPaint.measureText(s));
                            } else {
                                staticLayout = generateStaticLayout(rowSequence.subSequence(start, end), wordWidth);
                            }
                            rowLayouts.append(column, staticLayout);
                        }
                    }
                    mLayoutArray.append(row, rowLayouts);
                }
            }
        }
    }


    private StaticLayout generateStaticLayout(CharSequence source, int width) {
        return new StaticLayout(source, mTextPaint, width, Layout.Alignment.ALIGN_CENTER, 1.0F, 0F, false);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initDefault();
        initAttrs(context, attrs);
        initPaint();
        initAnimSpan();
    }

    private void initDefault() {
        mTextSize = (int) MeasureUtil.sp2px(getContext(), 18);
        mTextColor = Color.BLACK;
        mText = sText;
    }


    private void initPaint() {
        mTextPaint = new TextPaint();
        mTextPaint.setDither(true);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTypeface(Typeface.DEFAULT);
        mTextPaint.setTextSize(mTextSize);
    }

    private void initAnimSpan() {
        mForegroundAlphaColorSpanGroup = new ForegroundAlphaColorSpanGroup(0);

        mSpanUtils = new SpanUtils();
        for (int i = 0, len = sText.length(); i < len; ++i) {
            ForegroundAlphaColorSpan span = new ForegroundAlphaColorSpan(Color.TRANSPARENT);
            mSpanUtils.append(sText.substring(i, i + 1)).setSpans(span);
            mForegroundAlphaColorSpanGroup.addSpan(span);
        }
        mText = mSpanUtils.create();
    }


    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, ATTRS);
        mTextSize = ta.getDimensionPixelSize(INDEX_ATTR_TEXT_SIZE, mTextSize);
        mTextColor = ta.getColor(INDEX_ATTR_TEXT_COLOR, mTextColor);
        mText = ta.getString(INDEX_ATTR_TEXT);
        ta.recycle();
    }


    /**
     * 当attachToWindow时启动动画
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnim();
    }

    @Override
    public void dispatchDraw(Canvas canvas) {

        //每个汉字的宽
        int wordWidth = (int) getFontWight(mTextPaint, mText);
        int wordHeight = (int) getFontHeight(mTextPaint);

        if (mLayoutArray != null) {
            canvas.save();
            canvas.translate(getWidth(), 0);
            for (int row = 0; row < mLayoutArray.size(); row++) {
                canvas.translate(-wordWidth, 0);
                SparseArray<StaticLayout> rowsLayout = mLayoutArray.get(row);
                float dy = 0;
                for (int colum = 0; rowsLayout != null && colum < rowsLayout.size(); colum++) {
                    if (colum > 0) {
                        CharSequence sequence = rowsLayout.get(colum - 1).getText();
                        float offsetDy = wordHeight * sequence.length();
                        canvas.translate(0, offsetDy);
                        dy += offsetDy;
                    }

                    Layout rowLayout = rowsLayout.get(colum);

                    if (isMatcher(rowLayout.getText().toString(), sRegex)) {
                        canvas.translate(wordWidth, 0);
                        canvas.rotate(90);
                        rowLayout.draw(canvas);
                        canvas.rotate(-90);
                        canvas.translate(-wordWidth, 0);
                    } else {
                        rowLayout.draw(canvas);
                    }
                }
                canvas.translate(0, -dy);
            }
            canvas.restore();
        }
        super.dispatchDraw(canvas);
    }


    /**
     * baseline：字符基线
     * ascent：字符最高点到baseline的推荐距离
     * top：字符最高点到baseline的最大距离
     * descent：字符最低点到baseline的推荐距离
     * bottom：字符最低点到baseline的最大距离
     * leading：行间距，即前一行的descent与下一行的ascent之间的距离
     *
     * @param paint
     * @return
     */
    private float getFontHeight(Paint paint) {
        float height = 0;
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        height = fontMetrics.descent - fontMetrics.ascent;
        return height;
    }

    /**
     * 获取一个汉字的宽度
     *
     * @return
     */
    private float getFontWight(Paint paint, CharSequence sequence) {
        return paint.measureText(sequence, 0, 1);
    }


    /**
     * 开始动画
     */
    private void startAnim() {
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // printer
                mForegroundAlphaColorSpanGroup.setAlpha((Float) animation.getAnimatedValue());
                postInvalidate();
            }
        });

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(100 * mText.length());
        valueAnimator.setStartDelay(2000);
        valueAnimator.start();
    }


    /**
     * 根据正则将字符串切割
     *
     * @param str   字符串
     * @param regex 匹配规则
     * @return
     */
    private String[] getStrings(String str, String regex) {
        String[] result;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        String[] matchers = getMatcher(matcher);
        String[] strings = str.split(regex);

        result = new String[(matchers == null ? 0 : matchers.length) + (strings == null ? 0 : strings.length)];
        for (int i = 0; i < result.length; i++) {//
            if (strings[0] == null || strings[0].equals("")) {
                if (i % 2 == 0) {
                    if (i / 2 < matchers.length) {
                        result[i] = matchers[i / 2];
                    }
                } else {
                    if (i / 2 + 1 < strings.length) {
                        result[i] = strings[i / 2 + 1];
                    }
                }
            } else {
                if (i % 2 == 0) {
                    if (i / 2 < strings.length) {
                        result[i] = strings[i / 2];
                    }
                } else {
                    if (i / 2 < matchers.length) {
                        result[i] = matchers[i / 2];
                    }

                }
            }
        }
        return result;
    }


    /**
     * 获取匹配的字符串
     *
     * @param matcher
     * @return
     */
    private String[] getMatcher(Matcher matcher) {
        StringBuffer sb = new StringBuffer();
        boolean isFind = matcher.find();
        while (true) {
            if (!isFind) {
                break;
            }
            sb.append(matcher.group());
            isFind = matcher.find();
            if (isFind) {
                sb.append(",");
            }
        }
        return sb.toString() == null || sb.toString().equals("") ? null : sb.toString().split(",");
    }

    public boolean isMatcher(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
}
