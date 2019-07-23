package indi.toaok.animation.utils;

import android.util.Log;

import indi.toaok.animation.BuildConfig;

/**
 * @author hpp
 * @version 1.0  2019/5/28.
 */
public class LogUtil {

    private static final String SUFFIX = "-->";

    private static final String TAG = "Toaok" + SUFFIX;

    public static void v(String message) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, TAG + message);
        }
    }

    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, TAG + message);
        }
    }

    public static void i(String message) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, TAG + message);
        }
    }

    public static void w(String message) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, TAG + message);
        }
    }

    public static void e(String message) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, TAG + message);
        }
    }

    public static void e(String message, Exception e) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, TAG + message, e);
        }
    }


    public static void v(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, tag+SUFFIX + message);
        }
    }

    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, tag+SUFFIX + message);
        }
    }

    public static void i(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, tag+SUFFIX + message);
        }
    }

    public static void w(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, tag+SUFFIX + message);
        }
    }

    public static void e(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, tag+SUFFIX + message);
        }
    }

    public static void e(String tag, String message, Exception e) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, tag+SUFFIX + message, e);
        }
    }


}
