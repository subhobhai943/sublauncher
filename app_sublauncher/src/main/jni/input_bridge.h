#ifndef SUBLAUNCHER_INPUT_BRIDGE_H
#define SUBLAUNCHER_INPUT_BRIDGE_H

#include <jni.h>

/**
 * Map standard string keycodes ("W", "SPACE", etc.) to LWJGL Minecraft keycodes.
 */
static inline int map_keycode(const char* keycode) {
    if (!keycode) return 0;
    if (strcmp(keycode, "W") == 0) return 17;
    if (strcmp(keycode, "A") == 0) return 30;
    if (strcmp(keycode, "S") == 0) return 31;
    if (strcmp(keycode, "D") == 0) return 32;
    if (strcmp(keycode, "SPACE") == 0) return 57;
    if (strcmp(keycode, "LSHIFT") == 0) return 42;
    if (strcmp(keycode, "E") == 0) return 18;
    if (strcmp(keycode, "UP") == 0) return 200;   // Arrow keys
    if (strcmp(keycode, "LEFT") == 0) return 203;
    if (strcmp(keycode, "DOWN") == 0) return 208;
    if (strcmp(keycode, "RIGHT") == 0) return 205;
    return 0; // default/unhandled
}

void input_bridge_key_down(int lwjgl_keycode);
void input_bridge_key_up(int lwjgl_keycode);

#endif // SUBLAUNCHER_INPUT_BRIDGE_H
