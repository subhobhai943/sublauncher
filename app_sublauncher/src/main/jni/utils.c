#include <jni.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <sys/system_properties.h>
#include <sys/stat.h>
#include <unistd.h>
#include <errno.h>
#include "log_helper.h"

/**
 * utils.c - Expanded helper functions for SubLauncher
 */

// Returns architecture
const char* get_device_arch() {
#if defined(__aarch64__)
    return "arm64-v8a";
#elif defined(__arm__)
    return "armeabi-v7a";
#elif defined(__x86_64__)
    return "x86_64";
#elif defined(__i386__)
    return "x86";
#else
    return "unknown";
#endif
}

// Returns Android SDK version
int get_android_sdk_version() {
    char sdk_version[PROP_VALUE_MAX];
    int version = 0;
    if (__system_property_get("ro.build.version.sdk", sdk_version) > 0) {
        version = atoi(sdk_version);
    }
    return version;
}

// Device is 64-bit?
bool is_64bit_device() {
#if defined(__aarch64__) || defined(__x86_64__)
    return true;
#else
    return false;
#endif
}

// Get total system memory
long get_total_memory() {
    long pages = sysconf(_SC_PHYS_PAGES);
    long page_size = sysconf(_SC_PAGE_SIZE);
    return pages * page_size;
}

// Get available memory
long get_available_memory() {
    long pages = sysconf(_SC_AVPHYS_PAGES);
    long page_size = sysconf(_SC_PAGE_SIZE);
    return pages * page_size;
}

// Check file existence
bool file_exists(const char* path) {
    if (path == NULL) return false;
    return access(path, F_OK) == 0;
}
// Ensure a directory exists
bool create_directory(const char* path) {
    if (path == NULL) return false;
    char tmp[256];
    char* p = NULL;
    size_t len;
    snprintf(tmp, sizeof(tmp), "%s", path);
    len = strlen(tmp);
    if (tmp[len - 1] == '/') tmp[len - 1] = 0;
    for (p = tmp + 1; *p; p++) {
        if (*p == '/') {
            *p = 0;
            if (mkdir(tmp, S_IRWXU) != 0 && errno != EEXIST) {
                LOGE("Failed to create directory: %s", tmp);
                return false;
            }
            *p = '/';
        }
    }
    if (mkdir(tmp, S_IRWXU) != 0 && errno != EEXIST) {
        LOGE("Failed to create directory: %s", tmp);
        return false;
    }
    return true;
}
// Java String to C string
char* jstring_to_char(JNIEnv* env, jstring jstr) {
    if (!jstr) return NULL;
    const char* temp = (*env)->GetStringUTFChars(env, jstr, NULL);
    if (!temp) return NULL;
    char* result = strdup(temp);
    (*env)->ReleaseStringUTFChars(env, jstr, temp);
    return result;
}
// C string to Java String
jstring char_to_jstring(JNIEnv* env, const char* str) {
    if (!str) return NULL;
    return (*env)->NewStringUTF(env, str);
}
// Add more helpers as needed...