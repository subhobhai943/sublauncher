package com.subhobhai.sublauncher;

import com.subhobhai.sublauncher.utils.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * Java wrapper for JRE Launcher native code
 * Handles JVM initialization and Minecraft launching
 */
public class JRELauncher {
    
    private static final String TAG = "JRELauncher";
    
    // Status codes matching native definitions
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_ERROR_NO_JRE = -1;
    public static final int STATUS_ERROR_JVM_CREATE = -2;
    public static final int STATUS_ERROR_CLASS_NOT_FOUND = -3;
    public static final int STATUS_ERROR_METHOD_NOT_FOUND = -4;
    public static final int STATUS_ERROR_INVOKE_FAILED = -5;
    
    static {
        // Native library should already be loaded by NativeBridge
        if (!NativeBridge.isNativeLibraryLoaded()) {
            Logger.e(TAG, "Native library not loaded - JRE functions unavailable");
        }
    }
    
    // ===== Native method declarations =====
    
    /**
     * Initialize JVM with options
     * @param javaHome Path to JRE directory
     * @param classPath Java classpath
     * @param jvmArgs Array of JVM arguments (e.g., ["-Xmx2G", "-Xms1G"])
     * @return Status code (STATUS_SUCCESS or error code)
     */
    private static native int initJVM(String javaHome, String classPath, String[] jvmArgs);
    
    /**
     * Check if JVM is initialized
     * @return true if JVM is running
     */
    public static native boolean isJVMInitialized();
    
    /**
     * Get last error message from native code
     * @return Error message string
     */
    public static native String getLastError();
    
    // ===== Helper methods =====
    
    /**
     * Initialize JVM with default options
     * @param javaHome Path to JRE installation
     * @param classPath Minecraft classpath
     * @param ramMB RAM allocation in MB
     * @return true if successful
     */
    public static boolean initializeJVM(String javaHome, String classPath, int ramMB) {
        Logger.i(TAG, "Initializing JVM...");
        Logger.i(TAG, "Java Home: " + javaHome);
        Logger.i(TAG, "RAM: " + ramMB + " MB");
        
        if (!NativeBridge.isNativeLibraryLoaded()) {
            Logger.e(TAG, "Native library not loaded");
            return false;
        }
        
        // Build JVM arguments
        List<String> args = new ArrayList<>();
        args.add("-Xmx" + ramMB + "M");  // Max heap
        args.add("-Xms" + ramMB + "M");  // Min heap
        args.add("-XX:+UseG1GC");         // Use G1 garbage collector
        args.add("-XX:+UnlockExperimentalVMOptions");
        args.add("-Djava.library.path=" + javaHome + "/lib");
        
        String[] jvmArgs = args.toArray(new String[0]);
        
        int result = initJVM(javaHome, classPath, jvmArgs);
        
        if (result != STATUS_SUCCESS) {
            String error = getLastError();
            Logger.e(TAG, "JVM initialization failed: " + error);
            return false;
        }
        
        Logger.i(TAG, "JVM initialized successfully");
        return true;
    }
    
    /**
     * Launch Minecraft with specified version
     * @param mainClass Main class to launch
     * @param gameArgs Game arguments
     * @return true if launch was successful
     */
    public static boolean launchMinecraft(String mainClass, String[] gameArgs) {
        Logger.i(TAG, "Launching Minecraft...");
        Logger.i(TAG, "Main class: " + mainClass);
        
        if (!isJVMInitialized()) {
            Logger.e(TAG, "JVM not initialized");
            return false;
        }
        
        // TODO: Call native launch method when implemented
        Logger.w(TAG, "Minecraft launch not yet fully implemented");
        
        return false;
    }
    
    /**
     * Get status message for status code
     */
    public static String getStatusMessage(int statusCode) {
        switch (statusCode) {
            case STATUS_SUCCESS:
                return "Success";
            case STATUS_ERROR_NO_JRE:
                return "JRE not found";
            case STATUS_ERROR_JVM_CREATE:
                return "Failed to create JVM";
            case STATUS_ERROR_CLASS_NOT_FOUND:
                return "Main class not found";
            case STATUS_ERROR_METHOD_NOT_FOUND:
                return "Main method not found";
            case STATUS_ERROR_INVOKE_FAILED:
                return "Failed to invoke main method";
            default:
                return "Unknown error (" + statusCode + ")";
        }
    }
}
