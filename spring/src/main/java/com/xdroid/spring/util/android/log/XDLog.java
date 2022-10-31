package com.xdroid.spring.util.android.log;

import android.util.Log;

import androidx.annotation.IntDef;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


/**
 * 日志输出
 */

public class XDLog {
    private static String TAG = "TSHLog";

    public static final int RELEASE = 1;
    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    @IntDef({RELEASE, VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT})
    @Target({ElementType.FIELD})
    @interface XdLogType {

    }

    //日志输出到什么级别，再网上的级别就不输出了
    @XdLogType
    private static int logLevel = ERROR;


    public static <T> void d(String tag, T... args) {
        if (logLevel >= DEBUG) {
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(tag);
            for (T info : args) {
                sb.append(info);
            }
            sb.append("\n");
            Log.d(TAG, sb.toString());
        }
    }

    public static <T> void e(String tag, T... args) {
        if (logLevel >= ERROR) {
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(tag);
            for (T info : args) {
                sb.append(info);
            }
            sb.append("\n");
            Log.e(TAG, sb.toString());
        }
    }


    public static <T> void i(String tag, T... args) {
        if (logLevel >= INFO) {
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(tag);
            for (T info : args) {
                sb.append(info);
            }
            sb.append("\n");
            Log.i(TAG, sb.toString());
        }
    }


}

