#ifndef SUBLAUNCHER_UTILS_H
#define SUBLAUNCHER_UTILS_H

#include <jni.h>
#include <stdbool.h>

/**
 * Native utility functions for SubLauncher
 */

// System information
const char* get_device_arch();
int get_android_sdk_version();
bool is_64bit_device();

// Memory utilities
long get_total_memory();
long get_available_memory();

// String utilities
char* jstring_to_char(JNIEnv* env, jstring jstr);
jstring char_to_jstring(JNIEnv* env, const char* str);

// File utilities
bool file_exists(const char* path);
bool create_directory(const char* path);

#endif // SUBLAUNCHER_UTILS_H
