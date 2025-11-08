#include <jni.h>
#include <string.h>
#include "input_bridge.h"
#include "log_helper.h"

static void queue_key_event(int lwjgl_key, int isDown) {
    // TODO: Implement ring buffer or live queue to JVM input system
    LOGD("[input_bridge_v3] Key %d %s", lwjgl_key, isDown ? "DOWN" : "UP");
    // This can be expanded to call into JVM/input system in real engine
}

// JNI: send key down from Java (TouchController)
JNIEXPORT void JNICALL
Java_com_subhobhai_sublauncher_NativeBridge_sendKeyDown(JNIEnv* env, jclass clazz, jstring jkey) {
    const char* keycode = (*env)->GetStringUTFChars(env, jkey, NULL);
    int lwjglk = map_keycode(keycode);
    queue_key_event(lwjglk, 1);
    (*env)->ReleaseStringUTFChars(env, jkey, keycode);
}

// JNI: send key up from Java
JNIEXPORT void JNICALL
Java_com_subhobhai_sublauncher_NativeBridge_sendKeyUp(JNIEnv* env, jclass clazz, jstring jkey) {
    const char* keycode = (*env)->GetStringUTFChars(env, jkey, NULL);
    int lwjglk = map_keycode(keycode);
    queue_key_event(lwjglk, 0);
    (*env)->ReleaseStringUTFChars(env, jkey, keycode);
}

void input_bridge_key_down(int lwjgl_keycode) { queue_key_event(lwjgl_keycode, 1); }
void input_bridge_key_up(int lwjgl_keycode)   { queue_key_event(lwjgl_keycode, 0); }
