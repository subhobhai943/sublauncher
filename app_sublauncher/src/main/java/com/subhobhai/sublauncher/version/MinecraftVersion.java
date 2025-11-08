package com.subhobhai.sublauncher.version;

import java.util.Date;

/**
 * MinecraftVersion - Data holder for a Minecraft Java Edition version
 */
public class MinecraftVersion {
    public enum Type {
        RELEASE, SNAPSHOT, OLD_ALPHA, OLD_BETA, OTHER
    }

    public String id;            // e.g. "1.19.4"
    public Type type;            // RELEASE/SNAPSHOT
    public String url;           // Version-specific manifest
    public String releaseTime;   // RFC3339 date string
    public String time;          // RFC3339 date string

    public MinecraftVersion(String id, Type type, String url, String releaseTime, String time) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.releaseTime = releaseTime;
        this.time = time;
    }
    public String toString() {
        return id + " [" + type + "]";
    }

    // Parse string type
    public static Type parseType(String input) {
        if (input == null) return Type.OTHER;
        switch (input.toLowerCase()) {
            case "release": return Type.RELEASE;
            case "snapshot": return Type.SNAPSHOT;
            case "old_alpha": return Type.OLD_ALPHA;
            case "old_beta": return Type.OLD_BETA;
            default: return Type.OTHER;
        }
    }
}
