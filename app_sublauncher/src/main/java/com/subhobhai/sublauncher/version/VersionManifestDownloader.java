package com.subhobhai.sublauncher.version;

import android.os.Handler;
import android.os.Looper;

import com.subhobhai.sublauncher.version.MinecraftVersion;
import com.subhobhai.sublauncher.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * VersionManifestDownloader
 * Downloads & parses the Minecraft version manifest v2.
 */
public class VersionManifestDownloader {
    public static final String MANIFEST_URL = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";
    public static final String CACHE_FILENAME = "version_manifest_v2.json";

    public interface VersionListCallback {
        void onSuccess(List<MinecraftVersion> versions);
        void onError(Exception e);
    }

    public static void fetchVersions(final File cacheDir, final VersionListCallback callback) {
        new Thread(() -> {
            try {
                File cacheFile = new File(cacheDir, CACHE_FILENAME);
                String json;

                // Download if not cached
                if (!cacheFile.exists() || cacheFile.length() < 1000) {
                    json = downloadManifest(MANIFEST_URL, cacheFile);
                } else {
                    json = readFile(cacheFile);
                }
                List<MinecraftVersion> versions = parseManifest(json);
                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(versions));
            } catch (Exception e) {
                Logger.e("VersionManifestDownloader", "Failed to fetch versions", e);
                new Handler(Looper.getMainLooper()).post(() -> callback.onError(e));
            }
        }).start();
    }

    private static String downloadManifest(String urlStr, File outFile) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setRequestProperty("User-Agent", "SubLauncher/1.0");
        conn.connect();
        if (conn.getResponseCode() != 200) throw new Exception("HTTP " + conn.getResponseCode());
        try (InputStreamReader isr = new InputStreamReader(conn.getInputStream());
             BufferedReader br = new BufferedReader(isr);
             OutputStream os = new FileOutputStream(outFile)) {
            StringBuilder sb = new StringBuilder();
            int read;
            char[] buf = new char[4096];
            while ((read = br.read(buf)) > 0) {
                sb.append(buf, 0, read);
                os.write(new String(buf, 0, read).getBytes());
            }
            return sb.toString();
        }
    }

    private static String readFile(File f) throws Exception {
        try (FileInputStream fis = new FileInputStream(f);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append("\n");
            return sb.toString();
        }
    }

    // Parse the Minecraft manifest to version list
    private static List<MinecraftVersion> parseManifest(String json) throws JSONException {
        List<MinecraftVersion> out = new ArrayList<>();
        JSONObject root = new JSONObject(json);
        JSONArray versions = root.getJSONArray("versions");
        for (int i=0; i<versions.length(); i++) {
            JSONObject v = versions.getJSONObject(i);
            String id = v.getString("id");
            MinecraftVersion.Type type = MinecraftVersion.parseType(v.getString("type"));
            String url = v.getString("url");
            String releaseTime = v.optString("releaseTime", "");
            String time = v.optString("time", "");
            out.add(new MinecraftVersion(id, type, url, releaseTime, time));
        }
        return out;
    }
}
