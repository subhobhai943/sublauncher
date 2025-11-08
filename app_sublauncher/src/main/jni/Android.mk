# SubLauncher Native Build Configuration
# Based on PojavLauncher's native components

LOCAL_PATH := $(call my-dir)

# Build SubLauncher Native Library
include $(CLEAR_VARS)

LOCAL_MODULE := sublauncher
LOCAL_CFLAGS := -Wall -Wextra -O2
LOCAL_LDLIBS := -llog -landroid -lEGL -lGLESv2

# Source files
LOCAL_SRC_FILES := \
    sublauncher_bridge.c \
    utils.c \
    log_helper.c

include $(BUILD_SHARED_LIBRARY)

# Note: Full native components from PojavLauncher will be added later
# These include:
# - awt_bridge.c (Java AWT to Android bridge)
# - egl_bridge.c (OpenGL ES context management)
# - input_bridge_v3.c (Keyboard/mouse input handling)
# - jre_launcher.c (JRE initialization)
# - GL4ES library (OpenGL to OpenGL ES translation)
# - LWJGL3 native bindings
