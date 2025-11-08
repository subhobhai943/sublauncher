package com.subhobhai.sublauncher.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Utility class for handling Android runtime permissions
 */
public class PermissionUtils {
    
    public static final int REQUEST_STORAGE_PERMISSION = 1001;
    public static final int REQUEST_ALL_FILES_ACCESS = 1002;
    
    /**
     * Check if storage permission is granted
     */
    public static boolean hasStoragePermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+ requires MANAGE_EXTERNAL_STORAGE
            return android.os.Environment.isExternalStorageManager();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6-10 require READ/WRITE_EXTERNAL_STORAGE
            return ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true; // Pre-Android 6 doesn't need runtime permission
    }
    
    /**
     * Request storage permission
     */
    public static void requestStoragePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Request MANAGE_EXTERNAL_STORAGE for Android 11+
            try {
                android.content.Intent intent = new android.content.Intent(
                        android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                android.net.Uri uri = android.net.Uri.fromParts("package", 
                        activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivityForResult(intent, REQUEST_ALL_FILES_ACCESS);
            } catch (Exception e) {
                Logger.e("Failed to request storage permission", e);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Request READ/WRITE_EXTERNAL_STORAGE for Android 6-10
            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    REQUEST_STORAGE_PERMISSION);
        }
    }
    
    /**
     * Check if the permission request was granted
     */
    public static boolean isPermissionGranted(int[] grantResults) {
        if (grantResults.length > 0) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
