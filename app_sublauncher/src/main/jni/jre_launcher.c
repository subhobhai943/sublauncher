#include "jre_launcher.h"
#include "log_helper.h"
#include "utils.h"
#include <stdlib.h>
#include <string.h>
#include <dlfcn.h>

/**
 * JRE Launcher Implementation
 * Handles JVM initialization and Minecraft launching
 */

// Global JVM instance
static JavaVM* g_jvm = NULL;
static char* g_java_home = NULL;
static char g_last_error[512] = {0};

// Output callbacks
static void (*g_stdout_callback)(const char*) = NULL;
static void (*g_stderr_callback)(const char*) = NULL;

/**
 * Set last error message
 */
static void set_error(const char* format, ...) {
    va_list args;
    va_start(args, format);
    vsnprintf(g_last_error, sizeof(g_last_error), format, args);
    va_end(args);
    LOGE("%s", g_last_error);
}

/**
 * Initialize JVM with custom options
 */
int jre_init_jvm(const char* java_home, 
                 const char* class_path,
                 char** jvm_args, 
                 int jvm_args_count) {
    
    LOGI("Initializing JVM...");
    LOGI("Java Home: %s", java_home);
    LOGI("Classpath: %s", class_path);
    
    if (g_jvm != NULL) {
        LOGW("JVM already initialized");
        return JRE_STATUS_SUCCESS;
    }
    
    if (java_home == NULL || class_path == NULL) {
        set_error("Java home or classpath is NULL");
        return JRE_STATUS_ERROR_NO_JRE;
    }
    
    // Set JAVA_HOME
    jre_set_java_home(java_home);
    
    // TODO: Full JVM initialization
    // This requires:
    // 1. Loading libjvm.so from JRE
    // 2. Creating JavaVMInitArgs structure
    // 3. Setting up classpath and other options
    // 4. Calling JNI_CreateJavaVM
    
    LOGI("JVM initialization stub - Full implementation requires JRE libraries");
    
    // Placeholder for now
    g_jvm = NULL; // Will be set when actual JVM is created
    
    return JRE_STATUS_SUCCESS;
}

/**
 * Launch Minecraft with specified arguments
 */
int jre_launch_minecraft(const char* main_class,
                         char** game_args,
                         int game_args_count) {
    
    LOGI("Launching Minecraft...");
    LOGI("Main class: %s", main_class);
    
    if (g_jvm == NULL) {
        set_error("JVM not initialized");
        return JRE_STATUS_ERROR_JVM_CREATE;
    }
    
    JNIEnv* env = jre_get_env();
    if (env == NULL) {
        set_error("Failed to get JNI environment");
        return JRE_STATUS_ERROR_JVM_CREATE;
    }
    
    // TODO: Full Minecraft launch
    // This requires:
    // 1. Finding the main class
    // 2. Finding the main method
    // 3. Converting game_args to Java String array
    // 4. Invoking the main method
    
    LOGI("Minecraft launch stub - Full implementation requires JVM setup");
    
    return JRE_STATUS_SUCCESS;
}

/**
 * Get the current JVM instance
 */
JavaVM* jre_get_jvm() {
    return g_jvm;
}

/**
 * Get JNI environment for current thread
 */
JNIEnv* jre_get_env() {
    if (g_jvm == NULL) {
        return NULL;
    }
    
    JNIEnv* env = NULL;
    int status = (*g_jvm)->GetEnv(g_jvm, (void**)&env, JNI_VERSION_1_6);
    
    if (status == JNI_EDETACHED) {
        // Thread not attached, attach it
        if ((*g_jvm)->AttachCurrentThread(g_jvm, &env, NULL) != 0) {
            LOGE("Failed to attach current thread");
            return NULL;
        }
    } else if (status != JNI_OK) {
        LOGE("Failed to get JNI environment: %d", status);
        return NULL;
    }
    
    return env;
}

/**
 * Set JRE home directory
 */
void jre_set_java_home(const char* path) {
    if (g_java_home != NULL) {
        free(g_java_home);
    }
    g_java_home = strdup(path);
    LOGI("JAVA_HOME set to: %s", g_java_home);
}

/**
 * Get JRE home directory
 */
const char* jre_get_java_home() {
    return g_java_home;
}

/**
 * Check if JVM is initialized
 */
bool jre_is_initialized() {
    return g_jvm != NULL;
}

/**
 * Destroy JVM instance
 */
int jre_destroy_jvm() {
    if (g_jvm == NULL) {
        return JRE_STATUS_SUCCESS;
    }
    
    LOGI("Destroying JVM...");
    (*g_jvm)->DestroyJavaVM(g_jvm);
    g_jvm = NULL;
    
    return JRE_STATUS_SUCCESS;
}

/**
 * Setup environment variables for JRE
 */
void jre_setup_environment(const char* game_dir,
                           const char* assets_dir,
                           const char* natives_dir) {
    
    LOGI("Setting up JRE environment...");
    LOGI("Game dir: %s", game_dir);
    LOGI("Assets dir: %s", assets_dir);
    LOGI("Natives dir: %s", natives_dir);
    
    // Set environment variables
    setenv("MINECRAFT_GAME_DIR", game_dir, 1);
    setenv("MINECRAFT_ASSETS_DIR", assets_dir, 1);
    setenv("MINECRAFT_NATIVES_DIR", natives_dir, 1);
    
    // Set library path for native libraries
    if (natives_dir != NULL) {
        char ld_path[1024];
        const char* existing = getenv("LD_LIBRARY_PATH");
        if (existing != NULL) {
            snprintf(ld_path, sizeof(ld_path), "%s:%s", natives_dir, existing);
        } else {
            snprintf(ld_path, sizeof(ld_path), "%s", natives_dir);
        }
        setenv("LD_LIBRARY_PATH", ld_path, 1);
        LOGI("LD_LIBRARY_PATH: %s", ld_path);
    }
}

/**
 * Get error message for last JRE error
 */
const char* jre_get_last_error() {
    return g_last_error;
}

/**
 * Set callback for JVM output redirection
 */
void jre_set_output_callbacks(void (*stdout_callback)(const char*),
                               void (*stderr_callback)(const char*)) {
    g_stdout_callback = stdout_callback;
    g_stderr_callback = stderr_callback;
    LOGI("Output callbacks registered");
}

// JNI exports for Java to call

JNIEXPORT jint JNICALL
Java_com_subhobhai_sublauncher_JRELauncher_initJVM(JNIEnv* env, jclass clazz,
                                                    jstring javaHome,
                                                    jstring classPath,
                                                    jobjectArray jvmArgs) {
    const char* java_home = (*env)->GetStringUTFChars(env, javaHome, NULL);
    const char* class_path = (*env)->GetStringUTFChars(env, classPath, NULL);
    
    // Convert Java String array to C string array
    int args_count = 0;
    char** args = NULL;
    
    if (jvmArgs != NULL) {
        args_count = (*env)->GetArrayLength(env, jvmArgs);
        args = (char**)malloc(sizeof(char*) * args_count);
        
        for (int i = 0; i < args_count; i++) {
            jstring arg = (jstring)(*env)->GetObjectArrayElement(env, jvmArgs, i);
            args[i] = (char*)(*env)->GetStringUTFChars(env, arg, NULL);
        }
    }
    
    int result = jre_init_jvm(java_home, class_path, args, args_count);
    
    // Release strings
    (*env)->ReleaseStringUTFChars(env, javaHome, java_home);
    (*env)->ReleaseStringUTFChars(env, classPath, class_path);
    
    if (args != NULL) {
        for (int i = 0; i < args_count; i++) {
            jstring arg = (jstring)(*env)->GetObjectArrayElement(env, jvmArgs, i);
            (*env)->ReleaseStringUTFChars(env, arg, args[i]);
        }
        free(args);
    }
    
    return result;
}

JNIEXPORT jboolean JNICALL
Java_com_subhobhai_sublauncher_JRELauncher_isJVMInitialized(JNIEnv* env, jclass clazz) {
    return jre_is_initialized();
}

JNIEXPORT jstring JNICALL
Java_com_subhobhai_sublauncher_JRELauncher_getLastError(JNIEnv* env, jclass clazz) {
    return (*env)->NewStringUTF(env, jre_get_last_error());
}
