package com.subhobhai.sublauncher.models;

import com.subhobhai.sublauncher.utils.Constants;

/**
 * Represents a Minecraft launcher profile
 */
public class LauncherProfile {
    
    private String name;
    private String versionId;
    private String gameDirectory;
    private String javaArgs;
    private int ramAllocation; // in MB
    private boolean useCustomResolution;
    private int resolutionWidth;
    private int resolutionHeight;
    private long created;
    private long lastUsed;
    
    public LauncherProfile() {
        this.name = "Default";
        this.versionId = "latest-release";
        this.gameDirectory = Constants.getMinecraftDir();
        this.javaArgs = "";
        this.ramAllocation = Constants.DEFAULT_RAM_ALLOCATION;
        this.useCustomResolution = false;
        this.resolutionWidth = 854;
        this.resolutionHeight = 480;
        this.created = System.currentTimeMillis();
        this.lastUsed = System.currentTimeMillis();
    }
    
    public LauncherProfile(String name, String versionId) {
        this();
        this.name = name;
        this.versionId = versionId;
    }
    
    // Getters
    public String getName() { return name; }
    public String getVersionId() { return versionId; }
    public String getGameDirectory() { return gameDirectory; }
    public String getJavaArgs() { return javaArgs; }
    public int getRamAllocation() { return ramAllocation; }
    public boolean isUseCustomResolution() { return useCustomResolution; }
    public int getResolutionWidth() { return resolutionWidth; }
    public int getResolutionHeight() { return resolutionHeight; }
    public long getCreated() { return created; }
    public long getLastUsed() { return lastUsed; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setVersionId(String versionId) { this.versionId = versionId; }
    public void setGameDirectory(String gameDirectory) { this.gameDirectory = gameDirectory; }
    public void setJavaArgs(String javaArgs) { this.javaArgs = javaArgs; }
    public void setRamAllocation(int ramAllocation) { 
        this.ramAllocation = Math.max(Constants.MIN_RAM_ALLOCATION,
                Math.min(ramAllocation, Constants.MAX_RAM_ALLOCATION));
    }
    public void setUseCustomResolution(boolean useCustomResolution) { 
        this.useCustomResolution = useCustomResolution; 
    }
    public void setResolutionWidth(int resolutionWidth) { this.resolutionWidth = resolutionWidth; }
    public void setResolutionHeight(int resolutionHeight) { this.resolutionHeight = resolutionHeight; }
    public void setCreated(long created) { this.created = created; }
    public void setLastUsed(long lastUsed) { this.lastUsed = lastUsed; }
    
    public void updateLastUsed() {
        this.lastUsed = System.currentTimeMillis();
    }
    
    @Override
    public String toString() {
        return "LauncherProfile{" +
                "name='" + name + '\'' +
                ", versionId='" + versionId + '\'' +
                ", ramAllocation=" + ramAllocation +
                " MB}";
    }
}
