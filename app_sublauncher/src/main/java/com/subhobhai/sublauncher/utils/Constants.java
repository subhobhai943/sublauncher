package com.subhobhai.sublauncher.utils;

import android.os.Environment;
import java.io.File;

/**
 * Global constants for SubLauncher
 */
public class Constants {
    
    // App Information
    public static final String APP_NAME = "SubLauncher";
    public static final String APP_TAG = "SubLauncher";
    
    // Directory Paths
    public static final String LAUNCHER_DIR = "sublauncher";
    public static File DIR_GAME_HOME;
    public static File DIR_GAME_NEW;
    public static File DIR_DATA;
    
    // Minecraft Constants
    public static final String MINECRAFT_VERSION_MANIFEST = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";
    public static final String MINECRAFT_RESOURCES = "https://resources.download.minecraft.net/";
    public static final String MINECRAFT_LIBRARIES = "https://libraries.minecraft.net/";
    
    // Memory Settings (MB)
    public static final int DEFAULT_RAM_ALLOCATION = 1024; // 1GB
    public static final int MIN_RAM_ALLOCATION = 512;      // 512MB
    public static final int MAX_RAM_ALLOCATION = 4096;     // 4GB
    
    // File Names
    public static final String PROFILE_FILE = "launcher_profiles.json";
    public static final String SETTINGS_FILE = "launcher_settings.json";
    public static final String ACCOUNTS_FILE = "accounts.json";
    
    // Preferences Keys
    public static final String PREF_NAME = "sublauncher_prefs";
    public static final String PREF_RAM_ALLOCATION = "ram_allocation";
    public static final String PREF_SELECTED_PROFILE = "selected_profile";
    public static final String PREF_RENDERER = "renderer_type";
    public static final String PREF_FIRST_LAUNCH = "first_launch";
    
    // Renderer Types
    public static final String RENDERER_GL4ES = "gl4es";
    public static final String RENDERER_VIRGL = "virgl";
    public static final String RENDERER_ANGLE = "angle";
    
    static {
        // Initialize directories
        initDirectories();
    }
    
    private static void initDirectories() {
        File baseDir = Environment.getExternalStorageDirectory();
        DIR_DATA = new File(baseDir, LAUNCHER_DIR);
        DIR_GAME_HOME = new File(DIR_DATA, ".minecraft");
        DIR_GAME_NEW = DIR_GAME_HOME;
        
        // Create directories if they don't exist
        if (!DIR_DATA.exists()) {
            DIR_DATA.mkdirs();
        }
        if (!DIR_GAME_HOME.exists()) {
            DIR_GAME_HOME.mkdirs();
        }
    }
    
    public static String getMinecraftDir() {
        return DIR_GAME_HOME.getAbsolutePath();
    }
    
    public static String getDataDir() {
        return DIR_DATA.getAbsolutePath();
    }
}
