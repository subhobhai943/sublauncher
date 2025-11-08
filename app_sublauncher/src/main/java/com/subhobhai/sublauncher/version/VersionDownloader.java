package com.subhobhai.sublauncher.version;

import android.os.Handler;
import android.os.Looper;

import com.subhobhai.sublauncher.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * VersionDownloader: Downloads and installs all files for a Minecraft version selected by the user.
 */
public class VersionDownloader {

    public interface Callback {
        void onProgress(String step, int progress, int total);
        void onSuccess();
        void onError(Exception e);
    }

    public static void downloadVersion(final String versionId, final String manifestUrl, final File baseDir, final Callback cb) {
        new Thread(() -> {
            try {
                cb.onProgress("Fetching version manifest...", 0, 1);
                String json = DownloadTask.downloadToString(manifestUrl);
                JSONObject manifest = new JSONObject(json);

                // Download main client jar
                JSONObject downloads = manifest.getJSONObject("downloads");
                JSONObject client = downloads.getJSONObject("client");
                String clientUrl = client.getString("url");
                File jarOut = new File(baseDir, "versions/" + versionId + "/" + versionId + ".jar");
                jarOut.getParentFile().mkdirs();
                cb.onProgress("Downloading client JAR", 1, 3);
                DownloadTask.download(clientUrl, jarOut);

                // Download libraries
                JSONArray libraries = manifest.getJSONArray("libraries");
                List<String> libUrls = new ArrayList<>();
                List<File> libOuts = new ArrayList<>();
                for (int i = 0; i < libraries.length(); i++) {
                    JSONObject lib = libraries.getJSONObject(i);
                    if (!lib.has("downloads")) continue;
                    JSONObject dl = lib.getJSONObject("downloads");
                    if (!dl.has("artifact")) continue;
                    JSONObject artifact = dl.getJSONObject("artifact");
                    String libUrl = artifact.getString("url");
                    String path = artifact.getString("path");
                    File outFile = new File(baseDir, "libraries/" + path);
                    outFile.getParentFile().mkdirs();
                    libUrls.add(libUrl);
                    libOuts.add(outFile);
                }
                for (int i = 0; i < libUrls.size(); i++) {
                    cb.onProgress("Library " + (i+1) + "/" + libUrls.size(), i, libUrls.size());
                    DownloadTask.download(libUrls.get(i), libOuts.get(i));
                }

                // Download assets index
                if (!manifest.has("assetIndex")) throw new JSONException("No assetIndex field");
                JSONObject assetIndex = manifest.getJSONObject("assetIndex");
                String assetIdxUrl = assetIndex.getString("url");
                String assetId = assetIndex.getString("id");
                File idxFile = new File(baseDir, "assets/indexes/" + assetId + ".json");
                idxFile.getParentFile().mkdirs();
                cb.onProgress("Asset index", 0, 1);
                DownloadTask.download(assetIdxUrl, idxFile);

                // Downloading assets.json: now download the assets (optional, can be split)
                String assetsJson = DownloadTask.readFile(idxFile);
                JSONObject assetsRoot = new JSONObject(assetsJson).getJSONObject("objects");
                List<String> assetUrls = new ArrayList<>();
                List<File> assetOuts = new ArrayList<>();
                for (String key : assetsRoot.keySet()) {
                    JSONObject val = assetsRoot.getJSONObject(key);
                    String hash = val.getString("hash");
                    String subdir = hash.substring(0, 2);
                    String url = "https://resources.download.minecraft.net/" + subdir + "/" + hash;
                    File assetFile = new File(baseDir, "assets/objects/" + subdir + "/" + hash);
                    assetFile.getParentFile().mkdirs();
                    assetUrls.add(url);
                    assetOuts.add(assetFile);
                }
                for (int i = 0; i < assetUrls.size(); i++) {
                    if (i % 150 == 0)
                        cb.onProgress("Asset " + (i+1) + "/" + assetUrls.size(), i, assetUrls.size());
                    DownloadTask.download(assetUrls.get(i), assetOuts.get(i));
                }

                new Handler(Looper.getMainLooper()).post(cb::onSuccess);
            } catch (Exception e) {
                Logger.e("VersionDownloader", "Failed to download version", e);
                new Handler(Looper.getMainLooper()).post(() -> cb.onError(e));
            }

        }).start();
    }
}
