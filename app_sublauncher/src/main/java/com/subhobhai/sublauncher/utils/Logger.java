package com.subhobhai.sublauncher.utils;

import android.util.Log;

/**
 * Centralized logging utility for SubLauncher
 */
public class Logger {
    
    private static final String TAG = "SubLauncher";
    private static final boolean DEBUG_ENABLED = true;
    
    public static void d(String message) {
        if (DEBUG_ENABLED) {
            Log.d(TAG, message);
        }
    }
    
    public static void d(String tag, String message) {
        if (DEBUG_ENABLED) {
            Log.d(tag, message);
        }
    }
    
    public static void i(String message) {
        Log.i(TAG, message);
    }
    
    public static void i(String tag, String message) {
        Log.i(tag, message);
    }
    
    public static void w(String message) {
        Log.w(TAG, message);
    }
    
    public static void w(String tag, String message) {
        Log.w(tag, message);
    }
    
    public static void w(String message, Throwable throwable) {
        Log.w(TAG, message, throwable);
    }
    
    public static void e(String message) {
        Log.e(TAG, message);
    }
    
    public static void e(String tag, String message) {
        Log.e(tag, message);
    }
    
    public static void e(String message, Throwable throwable) {
        Log.e(TAG, message, throwable);
    }
    
    public static void e(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);
    }
    
    public static void printStackTrace(Exception e) {
        if (DEBUG_ENABLED) {
            e.printStackTrace();
        }
        Log.e(TAG, "Exception occurred", e);
    }
}
