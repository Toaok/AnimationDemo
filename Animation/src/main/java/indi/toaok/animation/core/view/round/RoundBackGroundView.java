package indi.toaok.animation.core.view.round;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author Toaok
 * @version 1.0  2019/7/26.
 */
public class RoundBackGroundView extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    private SurfaceHolder mSurfaceHolder;

    private Canvas mCanvas;

    private boolean isDrawing;

    private Thread mLocaleThread;

    public RoundBackGroundView(Context context) {
        this(context, null);
    }

    public RoundBackGroundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundBackGroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        onResume();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
    }

    @Override
    public void run() {
        while (isDrawing) {
            update();
            drawing();
        }
    }

    private void drawing() {

        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            if (mCanvas != null) {
                drawBackgroud(mCanvas);
            }
            //TODO
        } finally {
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }


    protected void drawBackgroud(Canvas canvas) {

    }


    protected void update() {

    }

    public void setDrawing(boolean drawing) {
        isDrawing = drawing;
    }

    public void onResume() {
        if (mLocaleThread == null) {
            mLocaleThread = new Thread(this);
            mLocaleThread.start();
        }
        isDrawing = true;
    }

    public void onPause() {
        isDrawing = false;
    }

    public void onStop() {
        isDrawing = false;
        mLocaleThread = null;
    }
}
