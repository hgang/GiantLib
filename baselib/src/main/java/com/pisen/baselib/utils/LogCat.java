package com.pisen.baselib.utils;


import android.util.Log;
import java.util.Locale;


/**
 * log  输出辅助工具类
 * @author chenkun
 * @date   2015/12/10 11:10
 */

public class LogCat {
    public static String TAG = "aBaseLib";
    public static void setIsPrintLog(boolean isPrintLog) {
        LogCat.isPrintLog = isPrintLog;
    }
    // 发布正式版本时需置为 false
    private static boolean isPrintLog = true;

    public LogCat() {

    }

    public static void setTag(String tag) {
        d("Changing log tag to %s", new Object[]{tag});
        TAG = tag;
    }

    public static void v(String format, Object... args) {
        if (!isPrintLog) {
            return;
        }
        Log.v(TAG, buildMessage(format, args));
    }

    public static void d(String format, Object... args) {
        if (!isPrintLog) {
            return;
        }
        Log.d(TAG, buildMessage(format, args));
    }

    public static void i(String format, Object... args) {
        if (!isPrintLog) {
            return;
        }
        Log.i(TAG, buildMessage(format, args));
    }

    public static void e(String format, Object... args) {
        if (!isPrintLog) {
            return;
        }
        Log.e(TAG, buildMessage(format, args));
    }

    public static void e(Throwable err, String format, Object... args) {
        if (!isPrintLog) {
            return;
        }
        Log.e(TAG, buildMessage(format, args), err);
    }

    public static void wtf(String format, Object... args) {
        if (!isPrintLog) {
            return;
        }
        Log.wtf(TAG, buildMessage(format, args));
    }

    public static void wtf(Throwable err, String format, Object... args) {
        if (!isPrintLog) {
            return;
        }
        Log.wtf(TAG, buildMessage(format, args), err);
    }

    private static String buildMessage(String format, Object... args) {
        String msg = null;
        try {
            if(format == null){
                msg = "null log";
            }else{
                msg = args.length == 0 ? format : String.format(Locale.US, format, args);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "logcat error!";
        }
        StackTraceElement[] trace = (new Throwable()).fillInStackTrace().getStackTrace();
        String caller = "<unknown>";

        for (int i = 2; i < trace.length; ++i) {
            Class clazz = trace[i].getClass();
            if (!clazz.equals(LogCat.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf(46) + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf(36) + 1);
                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }

        return String.format(Locale.US, "[%d] %s: %s", new Object[]{Long.valueOf(Thread.currentThread().getId()), caller, msg});
    }
}

