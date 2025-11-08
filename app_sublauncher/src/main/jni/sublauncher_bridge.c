#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include "log_helper.h"
#include "utils.h"

/**
 * SubLauncher Native Bridge
 * Main JNI interface between Java and native code
 */

// JNI method to get device architecture
JNIEXPORT jstring JNICALL
Java_com_subhobhai_sublauncher_NativeBridge_getDeviceArchitecture(JNIEnv *env, jclass clazz) {
    const char* arch = get_device_arch();
    LOGI("Device architecture: %s", arch);
    return (*env)->NewStringUTF(env, arch);
}

// JNI method to get Android SDK version
JNIEXPORT jint JNICALL
Java_com_subhobhai_sublauncher_NativeBridge_getAndroidVersion(JNIEnv *env, jclass clazz) {
    int version = get_android_sdk_version();
    LOGI("Android SDK version: %d", version);
    return version;
}

// JNI method to check if device is 64-bit
JNIEXPORT jboolean JNICALL
Java_com_subhobhai_sublauncher_NativeBridge_is64BitDevice(JNIEnv *env, jclass clazz) {
    bool is64 = is_64bit_device();
    LOGI("Device is %s", is64 ? "64-bit" : "32-bit");
    return (jboolean)is64;
}

// JNI method to get total memory
JNIEXPORT jlong JNICALL
Java_com_subhobhai_sublauncher_NativeBridge_getTotalMemory(JNIEnv *env, jclass clazz) {
    long total = get_total_memory();
    LOGI("Total memory: %ld bytes", total);
    return (jlong)total;
}

// JNI method to get available memory
JNIEXPORT jlong JNICALL
Java_com_subhobhai_sublauncher_NativeBridge_getAvailableMemory(JNIEnv *env, jclass clazz) {
    long available = get_available_memory();
    LOGD("Available memory: %ld bytes", available);
    return (jlong)available;
}

// JNI method to check if file exists
JNIEXPORT jboolean JNICALL
Java_com_subhobhai_sublauncher_NativeBridge_fileExists(JNIEnv *env, jclass clazz, jstring path) {
    char* native_path = jstring_to_char(env, path);
    if (native_path == NULL) {
        return JNI_FALSE;
    }
    
    bool exists = file_exists(native_path);
    free(native_path);
    
    return (jboolean)exists;
}

// JNI method to create directory
JNIEXPORT jboolean JNICALL
Java_com_subhobhai_sublauncher_NativeBridge_createDirectory(JNIEnv *env, jclass clazz, jstring path) {
    char* native_path = jstring_to_char(env, path);
    if (native_path == NULL) {
        return JNI_FALSE;
    }
    
    bool created = create_directory(native_path);
    free(native_path);
    
    return (jboolean)created;
}

// JNI_OnLoad - Called when native library is loaded
JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    LOGI("SubLauncher native library loaded successfully");
    return JNI_VERSION_1_6;
}
