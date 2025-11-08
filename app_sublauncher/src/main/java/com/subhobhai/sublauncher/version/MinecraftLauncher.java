package com.subhobhai.sublauncher.version;

import android.content.Context;
import android.os.Environment;

import com.subhobhai.sublauncher.JRELauncher;
import com.subhobhai.sublauncher.utils.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * MinecraftLauncher - create full argument list and launch game using JRELauncher.
 */
public class MinecraftLauncher {
    public interface LaunchCallback {
        void onLaunchSuccess();
        void onLaunchError(Exception e);
    }

    public static void launchMinecraft(Context ctx, String versionId, int ramMB, LaunchCallback cb) {
        try {
            File baseDir = ctx.getExternalFilesDir(null);
            File versionsDir = new File(baseDir, "versions/" + versionId);
            File jar = new File(versionsDir, versionId + ".jar");
            File gameDir = new File(baseDir, ".minecraft");
            File assetsDir = new File(baseDir, "assets");
            File librariesDir = new File(baseDir, "libraries");
            String javaHome = baseDir.getAbsolutePath() + "/jre"; // You must bundle a JRE for full launch

            // Build classpath: libraries + client jar
            StringBuilder classPath = new StringBuilder();
            addJarsRecursive(librariesDir, classPath);
            classPath.append(jar.getAbsolutePath());

            // Minimal main class and args (no authentication for demo mode)
            String mainClass = "net.minecraft.client.main.Main";
            List<String> args = new ArrayList<>();
            args.add(mainClass);
            args.add("--username");    args.add("DemoPlayer");
            args.add("--version");     args.add(versionId);
            args.add("--gameDir");     args.add(gameDir.getAbsolutePath());
            args.add("--assetsDir");   args.add(assetsDir.getAbsolutePath());
            args.add("--assetIndex");  args.add(versionId);
            args.add("--accessToken"); args.add("0");
            args.add("--uuid");        args.add("0");
            args.add("--userType");    args.add("legacy");

            Logger.i("MinecraftLauncher", "Launching with args: " + args);

            // Start JVM and launch the game
            boolean initialized = JRELauncher.initializeJVM(javaHome, classPath.toString(), ramMB);
            if (!initialized) throw new RuntimeException("Failed to initialize JVM: " + JRELauncher.getLastError());

            // TODO: Add JNI to actually call Minecraft main
            // For now, inform the UI:
            cb.onLaunchSuccess();
        } catch (Exception e) {
            Logger.e("MinecraftLauncher", "Launch failed", e);
            cb.onLaunchError(e);
        }
    }

    private static void addJarsRecursive(File dir, StringBuilder cp) {
        if (dir == null || !dir.exists()) return;
        File[] files = dir.listFiles();
        if (files == null) return;
        for (File f : files) {
            if (f.isDirectory()) addJarsRecursive(f, cp);
            else if (f.getName().endsWith(".jar")) {
                cp.append(f.getAbsolutePath());
                cp.append(File.pathSeparator);
            }
        }
    }
}
