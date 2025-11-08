package com.subhobhai.sublauncher.utils;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * Utility class for file operations
 */
public class FileUtils {
    
    /**
     * Copy a file from source to destination
     */
    public static void copyFile(File source, File dest) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } finally {
            if (sourceChannel != null) sourceChannel.close();
            if (destChannel != null) destChannel.close();
        }
    }
    
    /**
     * Read entire file content as String
     */
    public static String readFileAsString(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } finally {
            if (reader != null) reader.close();
        }
        
        return content.toString();
    }
    
    /**
     * Write string content to file
     */
    public static void writeStringToFile(File file, String content) throws IOException {
        FileWriter writer = null;
        
        try {
            writer = new FileWriter(file);
            writer.write(content);
        } finally {
            if (writer != null) writer.close();
        }
    }
    
    /**
     * Delete a directory recursively
     */
    public static boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
        return directory.delete();
    }
    
    /**
     * Get file size in human-readable format
     */
    public static String getReadableFileSize(long size) {
        if (size <= 0) return "0 B";
        final String[] units = new String[] {"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return String.format("%.1f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
    }
    
    /**
     * Ensure a directory exists, create if it doesn't
     */
    public static boolean ensureDirectoryExists(File directory) {
        if (!directory.exists()) {
            return directory.mkdirs();
        }
        return directory.isDirectory();
    }
}
