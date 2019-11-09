package com.example.roy.recycleviewtest.util;

import android.util.Log;

/**
 * Logcat日志工具
 */
public class LogUtil {
    private static final boolean debug = true; // TODO:正式上线为false
    private static final String TAG = "松鼠鳜鱼";

    public static void v(String tag, String msg) {
        if (debug) {
            Log.v(tag, msg);
        }
    }

    public static void v(String msg) {
        if (debug) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (debug) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        if (debug) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (debug) {
            Log.i(tag, msg);
        }

    }

    public static void i(String msg) {
        if (debug) {
            Log.i(TAG, msg);
        }

    }

    public static void w(String tag, String msg) {
        if (debug) {
            Log.w(tag, msg);
        }
    }

    public static void w(String msg) {
        if (debug) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (debug) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        if (debug) {
            Log.e(TAG, msg);
        }
    }
}
