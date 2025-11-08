#include <jni.h>
#include "log_helper.h"

/**
 * awt_bridge.c
 * Emulates AWT window behavior on Android, handling window events
 * e.g. focus, resize, surface, input, etc
 */

// JNI: notify focus change
JNIEXPORT void JNICALL
Java_com_subhobhai_sublauncher_AWTBridge_onFocusChanged(JNIEnv* env, jclass clazz, jboolean focused) {
    LOGI("[awt_bridge] Window focus changed: %s", focused ? "yes" : "no");
    // TODO: Implement window focus handling
}

// JNI: notify resize event
JNIEXPORT void JNICALL
Java_com_subhobhai_sublauncher_AWTBridge_onResize(JNIEnv* env, jclass clazz, jint width, jint height) {
    LOGI("[awt_bridge] Window resized: %dx%d", width, height);
    // TODO: Forward resize event to JVM if needed
}

// JNI: notify close event
JNIEXPORT void JNICALL
Java_com_subhobhai_sublauncher_AWTBridge_onClose(JNIEnv* env, jclass clazz) {
    LOGI("[awt_bridge] Window close requested");
    // TODO: Implement window close event
}
