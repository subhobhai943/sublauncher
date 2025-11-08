#ifndef SUBLAUNCHER_JRE_LAUNCHER_H
#define SUBLAUNCHER_JRE_LAUNCHER_H

#include <jni.h>
#include <stdbool.h>

/**
 * JRE Launcher Header
 * Core functions for launching Java Runtime Environment and Minecraft
 */

// JRE initialization result codes
#define JRE_STATUS_SUCCESS 0
#define JRE_STATUS_ERROR_NO_JRE -1
#define JRE_STATUS_ERROR_JVM_CREATE -2
#define JRE_STATUS_ERROR_CLASS_NOT_FOUND -3
#define JRE_STATUS_ERROR_METHOD_NOT_FOUND -4
#define JRE_STATUS_ERROR_INVOKE_FAILED -5

/**
 * Initialize JVM with custom options
 * @param java_home Path to JRE installation
 * @param class_path Java classpath
 * @param jvm_args Additional JVM arguments (e.g., -Xmx2G)
 * @param jvm_args_count Number of JVM arguments
 * @return JRE_STATUS_SUCCESS on success, error code otherwise
 */
int jre_init_jvm(const char* java_home, 
                 const char* class_path,
                 char** jvm_args, 
                 int jvm_args_count);

/**
 * Launch Minecraft with specified arguments
 * @param main_class Main class to launch (e.g., net.minecraft.client.main.Main)
 * @param game_args Array of game arguments
 * @param game_args_count Number of game arguments
 * @return JRE_STATUS_SUCCESS on success, error code otherwise
 */
int jre_launch_minecraft(const char* main_class,
                         char** game_args,
                         int game_args_count);

/**
 * Get the current JVM instance
 * @return JavaVM pointer or NULL if not initialized
 */
JavaVM* jre_get_jvm();

/**
 * Get JNI environment for current thread
 * @return JNIEnv pointer or NULL if not attached
 */
JNIEnv* jre_get_env();

/**
 * Set JRE home directory
 * @param path Path to JRE directory
 */
void jre_set_java_home(const char* path);

/**
 * Get JRE home directory
 * @return Path to JRE directory or NULL
 */
const char* jre_get_java_home();

/**
 * Check if JVM is initialized
 * @return true if JVM is running, false otherwise
 */
bool jre_is_initialized();

/**
 * Destroy JVM instance
 * @return JRE_STATUS_SUCCESS on success
 */
int jre_destroy_jvm();

/**
 * Setup environment variables for JRE
 * @param game_dir Minecraft game directory
 * @param assets_dir Assets directory
 * @param natives_dir Native libraries directory
 */
void jre_setup_environment(const char* game_dir,
                           const char* assets_dir,
                           const char* natives_dir);

/**
 * Get error message for last JRE error
 * @return Error message string
 */
const char* jre_get_last_error();

/**
 * Set callback for JVM output redirection
 * @param stdout_callback Callback for stdout
 * @param stderr_callback Callback for stderr
 */
void jre_set_output_callbacks(void (*stdout_callback)(const char*),
                               void (*stderr_callback)(const char*));

#endif // SUBLAUNCHER_JRE_LAUNCHER_H
