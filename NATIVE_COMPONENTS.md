# Native Components Documentation

SubLauncher uses native C/C++ code via JNI (Java Native Interface) for performance-critical operations and Android system integration.

## Overview

The native layer is essential for:
- **JRE Integration**: Running Java Runtime Environment on Android
- **Graphics Rendering**: OpenGL ES bridging for Minecraft rendering
- **Input Handling**: Touch/keyboard/mouse event processing
- **System Access**: Low-level Android system operations
- **Performance**: Critical operations that need to run fast

## File Structure

```
app_sublauncher/src/main/jni/
├── Android.mk              # NDK build configuration
├── Application.mk          # NDK application settings
├── sublauncher_bridge.c    # Main JNI bridge (Java ↔ C)
├── utils.c / utils.h       # Utility functions
└── log_helper.c / log_helper.h  # Native logging
```

## Components

### 1. sublauncher_bridge.c
**Purpose**: Main bridge between Java and C code

**Functions**:
- `getDeviceArchitecture()` - Returns CPU architecture (arm64-v8a, x86, etc.)
- `getAndroidVersion()` - Returns Android SDK version
- `is64BitDevice()` - Checks if device is 64-bit
- `getTotalMemory()` - Returns total device RAM
- `getAvailableMemory()` - Returns available RAM
- `fileExists()` - Check if file exists
- `createDirectory()` - Create directories

**Java Interface**: `NativeBridge.java`

### 2. utils.c / utils.h
**Purpose**: Native utility functions

**Key Functions**:
- Device information (architecture, SDK version)
- Memory management helpers
- String conversion (Java ↔ C)
- File system operations

### 3. log_helper.c / log_helper.h
**Purpose**: Native logging to Android Logcat

**Macros**:
```c
LOGD("Debug message");   // Debug
LOGI("Info message");    // Info
LOGW("Warning message"); // Warning
LOGE("Error message");   // Error
```

## Building Native Code

### Prerequisites

1. **Android NDK** (r25 or later)
   - Install via Android Studio SDK Manager
   - Or download from: https://developer.android.com/ndk/downloads

2. **Set NDK Path**:
   ```bash
   export ANDROID_NDK_HOME=/path/to/ndk
   ```

### Build Commands

**Using Gradle** (Recommended):
```bash
./gradlew :app_sublauncher:assembleDebug
```
This automatically compiles native code and includes it in the APK.

**Using ndk-build** (Manual):
```bash
cd app_sublauncher/src/main/jni
ndk-build
```
Output: `../libs/[arch]/libsublauncher.so`

### Supported Architectures

- **arm64-v8a** - 64-bit ARM (most modern Android devices)
- **armeabi-v7a** - 32-bit ARM (older devices)
- **x86_64** - 64-bit Intel (emulators, some tablets)
- **x86** - 32-bit Intel (older emulators)

## Using Native Functions in Java

### Example Usage

```java
import com.subhobhai.sublauncher.NativeBridge;

public class Example {
    public void checkDevice() {
        // Check if native library loaded
        if (!NativeBridge.isNativeLibraryLoaded()) {
            Log.e("Example", "Native library not loaded!");
            return;
        }
        
        // Get device info
        String arch = NativeBridge.getDeviceArchitecture();
        int sdkVersion = NativeBridge.getAndroidVersion();
        boolean is64bit = NativeBridge.is64BitDevice();
        
        // Memory information
        long totalMemMB = NativeBridge.getTotalMemoryMB();
        long availMemMB = NativeBridge.getAvailableMemoryMB();
        
        // File operations
        boolean exists = NativeBridge.fileExists("/path/to/file");
        boolean created = NativeBridge.createDirectory("/path/to/dir");
        
        // Get formatted device info
        String deviceInfo = NativeBridge.getDeviceInfo();
        Log.i("Example", deviceInfo);
    }
}
```

## JNI Method Naming Convention

JNI methods follow this pattern:
```
Java_<package>_<class>_<method>
```

Example:
```c
// Java: com.subhobhai.sublauncher.NativeBridge.getDeviceArchitecture()
// C function name:
Java_com_subhobhai_sublauncher_NativeBridge_getDeviceArchitecture
```

## Debugging Native Code

### View Native Logs

```bash
adb logcat | grep "SubLauncher-Native"
```

### Debug with LLDB

1. In Android Studio: Run > Debug (not Run)
2. Set breakpoints in `.c` files
3. App will pause at native breakpoints

### Common Issues

**Issue**: `UnsatisfiedLinkError: No implementation found`
- **Cause**: Native library not compiled or wrong architecture
- **Fix**: Run `./gradlew :app_sublauncher:assembleDebug` to rebuild

**Issue**: Native library not loading
- **Cause**: Missing NDK or build failed
- **Fix**: Check `build/intermediates/cmake/` for error logs

**Issue**: Crashes in native code
- **Cause**: Null pointer, memory issues, or JNI errors
- **Fix**: Check logcat for native crash dumps

## Future Native Components

These will be added in later steps:

### From PojavLauncher:
1. **jre_launcher.c** - Launch Java Runtime Environment
2. **input_bridge_v3.c** - Advanced input handling
3. **egl_bridge.c** - OpenGL ES context management
4. **awt_bridge.c** - Java AWT to Android bridge
5. **GL4ES** - OpenGL to OpenGL ES translation
6. **LWJGL3** - Lightweight Java Game Library

### Additional Components:
- Graphics renderers (virgl, zink)
- JVM hooks for method interception
- CPU affinity management
- Custom environment setup

## Performance Considerations

- **JNI Overhead**: Minimize Java ↔ C calls in tight loops
- **Memory**: Free allocated memory in C code
- **Threads**: Native threads need JNI environment attachment
- **Exceptions**: Check for Java exceptions after JNI calls

## Resources

- [Android JNI Tips](https://developer.android.com/training/articles/perf-jni)
- [NDK Documentation](https://developer.android.com/ndk/guides)
- [JNI Specification](https://docs.oracle.com/javase/8/docs/technotes/guides/jni/)
- [PojavLauncher Native Code](https://github.com/PojavLauncherTeam/PojavLauncher/tree/v3_openjdk/app_pojavlauncher/src/main/jni)

## Contributing

When adding native code:
1. Update `Android.mk` with new source files
2. Add corresponding Java native method declarations
3. Document functions in header files
4. Test on multiple architectures
5. Check for memory leaks with Valgrind (on emulator)

---

**Note**: This is a simplified version of PojavLauncher's native layer. Full Minecraft launching requires additional components listed in "Future Native Components".
