package indi.toaok.animation.core.view.round;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import indi.toaok.animation.R;

/**
 * @author Toaok
 * @version 1.0  2019/7/26.
 */
public class RollImageView extends RoundBackGroundView {

    float mSpeed;

    @DrawableRes
    int mDrawableRes;

    Drawable mDrawable;

    Bitmap mBitmap;

    //图片的宽和高
    int bitmapWidth;
    int bitmapHeight;

    //显示的宽和高
    int displayWidth;
    int displayHeight;

    //垂直和水平方向上的偏移量
    int offsetY;
    int offsetX;

    //垂直方向上偏移速度
    int mSpeedY;

    //绘制bitmap的大小(对bitmap进行矩形裁剪)
    Rect mSrcRect;
    //绘制的位置
    Rect mDstRectBottom;
    Rect mDstRectTop;

    //蒙板
    Rect mMaskRect;
    //蒙板画笔
    Paint mMaskPaint;

    //蒙板的透明度
    @FloatRange(from = 0, to = 1)
    float mMaskAlpha;


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
        initDefault();

        initMaskPaint();
    }

    private void initDefault() {
        offsetY = 0;
        offsetX = 0;
        mSpeed = 2;
        mSpeedY = (int) (mSpeed * getResources().getDisplayMetrics().density);
        mMaskAlpha = 0f;
        bitmapWidth = bitmapHeight = 1;
        mDrawableRes = R.drawable.image_bg;
        mDrawable = new DefaultBackgroundDrawable(getContext());
    }

    private void initMaskPaint() {
        mMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMaskPaint.setColor(Color.BLACK);
        mMaskPaint.setStyle(Paint.Style.FILL);
        mMaskPaint.setAlpha((int) (mMaskAlpha * 255));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mBitmap == null) {
            try {
                mBitmap = scale(getBitmap(mDrawableRes), right - left, false);
                if (mBitmap == null) {
                    mDrawable.setBounds(left, top, right, bottom);
                    mBitmap = drawable2Bitmap(mDrawable);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mBitmap = drawable2Bitmap(mDrawable);
            }
            bitmapWidth = mBitmap.getWidth();
            bitmapHeight = mBitmap.getHeight();
            mSrcRect = new Rect(0, 0, bitmapWidth, bitmapHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        displayWidth = w;
        displayHeight = h;
        mMaskRect = new Rect(0, 0, w, h);
    }

    @Override
    protected void drawBackgroud(Canvas canvas) {
        super.drawBackgroud(canvas);

        mDstRectTop = new Rect(0, -offsetY, displayWidth, bitmapHeight - offsetY);
        mDstRectBottom = new Rect(0, bitmapHeight - offsetY, displayWidth, bitmapHeight * 2 - offsetY);
        canvas.drawBitmap(mBitmap, mSrcRect, mDstRectTop, null);
        canvas.drawBitmap(mBitmap, mSrcRect, mDstRectBottom, null);
        canvas.drawRect(mMaskRect, mMaskPaint);
    }

    @Override
    protected void update() {
        super.update();
        offsetY += mSpeedY;
        offsetY = offsetY % bitmapHeight;

    }


    public Bitmap drawable2Bitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    public Bitmap getBitmap(@DrawableRes final int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        final Resources resources = getResources();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inScaled = false;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }


    public static Bitmap scale(final Bitmap src,
                               final float newWidth,
                               final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        float newHeight = newWidth * src.getHeight() / src.getWidth();
        Bitmap ret = Bitmap.createScaledBitmap(src, (int) newWidth, (int) newHeight, true);
        if (recycle && !src.isRecycled() && ret != src) src.recycle();
        return ret;
    }

    private static boolean isEmptyBitmap(final Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }
}
