package com.subhobhai.sublauncher;

import com.subhobhai.sublauncher.utils.Logger;

/**
 * Bridge between Java and native C/C++ code
 * Loads the native library and provides access to native methods
 */
public class NativeBridge {
    
    private static boolean isLoaded = false;
    
    static {
        try {
            System.loadLibrary("sublauncher");
            isLoaded = true;
            Logger.i("NativeBridge", "Native library loaded successfully");
        } catch (UnsatisfiedLinkError e) {
            Logger.e("NativeBridge", "Failed to load native library", e);
            isLoaded = false;
        }
    }
    
    /**
     * Check if native library was loaded successfully
     */
    public static boolean isNativeLibraryLoaded() {
        return isLoaded;
    }
    
    // ===== Native method declarations =====
    
    /**
     * Get the device CPU architecture (arm64-v8a, armeabi-v7a, x86, x86_64)
     */
    public static native String getDeviceArchitecture();
    
    /**
     * Get Android SDK version number
     */
    public static native int getAndroidVersion();
    
    /**
     * Check if the device is 64-bit
     */
    public static native boolean is64BitDevice();
    
    /**
     * Get total device memory in bytes
     */
    public static native long getTotalMemory();
    
    /**
     * Get available device memory in bytes
     */
    public static native long getAvailableMemory();
    
    /**
     * Check if a file exists at the given path
     */
    public static native boolean fileExists(String path);
    
    /**
     * Create a directory at the given path (including parent directories)
     */
    public static native boolean createDirectory(String path);
    
    // ===== Helper methods =====
    
    /**
     * Get total memory in MB
     */
    public static long getTotalMemoryMB() {
        if (!isLoaded) return 0;
        return getTotalMemory() / (1024 * 1024);
    }
    
    /**
     * Get available memory in MB
     */
    public static long getAvailableMemoryMB() {
        if (!isLoaded) return 0;
        return getAvailableMemory() / (1024 * 1024);
    }
    
    /**
     * Get device information as a formatted string
     */
    public static String getDeviceInfo() {
        if (!isLoaded) {
            return "Native library not loaded";
        }
        
        StringBuilder info = new StringBuilder();
        info.append("Architecture: ").append(getDeviceArchitecture()).append("\n");
        info.append("Android SDK: ").append(getAndroidVersion()).append("\n");
        info.append("64-bit: ").append(is64BitDevice() ? "Yes" : "No").append("\n");
        info.append("Total Memory: ").append(getTotalMemoryMB()).append(" MB\n");
        info.append("Available Memory: ").append(getAvailableMemoryMB()).append(" MB");
        
        return info.toString();
    }
}
