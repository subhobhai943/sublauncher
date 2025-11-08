# Building SubLauncher

This guide will walk you through the process of building SubLauncher from source.

## Prerequisites

### Required Software

1. **Android Studio** (Arctic Fox or newer)
   - Download from: https://developer.android.com/studio
   - Includes Android SDK and necessary build tools

2. **Java Development Kit (JDK) 8 or higher**
   - Download from: https://adoptium.net/ or https://www.oracle.com/java/technologies/downloads/

3. **Git**
   - Download from: https://git-scm.com/downloads

4. **Android SDK Components**
   - Android SDK Platform 34 (Android 14)
   - Android SDK Build-Tools 34.0.0
   - Android NDK 25.2.9519653 (for native components)

### System Requirements

- **OS**: Windows 10/11, macOS 10.14+, or Linux (Ubuntu 18.04+ recommended)
- **RAM**: At least 8GB (16GB recommended)
- **Disk Space**: At least 10GB free space
- **Internet Connection**: Required for downloading dependencies

## Quick Start (Basic Build)

This will build the basic launcher shell without full Minecraft launching functionality:

### 1. Clone the Repository

```bash
git clone https://github.com/subhobhai943/sublauncher.git
cd sublauncher
```

### 2. Open in Android Studio

1. Launch Android Studio
2. Select **File > Open**
3. Navigate to the cloned `sublauncher` directory
4. Click **OK**
5. Wait for Gradle sync to complete

### 3. Build the Project

**Option A: Using Android Studio**
1. Select **Build > Make Project** or press `Ctrl+F9` (Windows/Linux) / `Cmd+F9` (macOS)
2. Select **Build > Build Bundle(s) / APK(s) > Build APK(s)**
3. APK will be generated in `app_sublauncher/build/outputs/apk/debug/`

**Option B: Using Command Line**

```bash
# On Linux/macOS
./gradlew :app_sublauncher:assembleDebug

# On Windows
gradlew.bat :app_sublauncher:assembleDebug
```

The compiled APK will be located at:
```
app_sublauncher/build/outputs/apk/debug/app_sublauncher-debug.apk
```

## Advanced Build (Full Functionality)

To build SubLauncher with complete Minecraft launching capabilities, you'll need additional components from PojavLauncher:

### Additional Requirements

1. **Pre-built JRE for Android**
   - Download from [PojavLauncher CI builds](https://github.com/PojavLauncherTeam/android-openjdk-build-multiarch/actions)
   - Extract `jre8-pojav` artifact to `app_sublauncher/src/main/assets/components/jre/`

2. **LWJGL3 Custom Build**
   - Build instructions: https://github.com/PojavLauncherTeam/lwjgl3
   - Place built libraries in `app_sublauncher/src/main/jniLibs/`

3. **Native Libraries**
   - GL4ES, Mesa 3D, virglrenderer
   - These require NDK compilation from PojavLauncher source

### Step-by-Step Advanced Build

1. **Set up NDK in Android Studio**
   ```
   Tools > SDK Manager > SDK Tools > NDK (Side by side)
   ```
   Install version 25.2.9519653

2. **Add Native Components**
   ```bash
   # Create JNI directory structure
   mkdir -p app_sublauncher/src/main/jni
   
   # Copy native code from PojavLauncher
   # (This requires cloning PojavLauncher separately)
   ```

3. **Configure Native Build**
   - Ensure `app_sublauncher/build.gradle` has:
   ```gradle
   externalNativeBuild {
       ndkBuild {
           path file('src/main/jni/Android.mk')
       }
   }
   ```

4. **Build with Native Components**
   ```bash
   ./gradlew :app_sublauncher:assembleDebug
   ```

## Troubleshooting

### Common Issues

**Issue: Gradle sync failed**
- Solution: Check your internet connection and try "File > Invalidate Caches / Restart"

**Issue: SDK not found**
- Solution: Set `ANDROID_HOME` environment variable:
  ```bash
  # Linux/macOS
  export ANDROID_HOME=$HOME/Android/Sdk
  
  # Windows
  set ANDROID_HOME=C:\Users\YourUsername\AppData\Local\Android\Sdk
  ```

**Issue: NDK not found**
- Solution: Install NDK via SDK Manager or set `ANDROID_NDK_HOME`

**Issue: Out of memory during build**
- Solution: Increase Gradle memory in `gradle.properties`:
  ```
  org.gradle.jvmargs=-Xmx4096m
  ```

### Build Variants

- **debug**: Development build with debugging enabled
- **release**: Production build (requires signing key)

To build release version:
```bash
./gradlew :app_sublauncher:assembleRelease
```

## Testing

### On Physical Device

1. Enable Developer Options on your Android device
2. Enable USB Debugging
3. Connect device via USB
4. Run: `./gradlew :app_sublauncher:installDebug`

### On Emulator

1. Create an AVD in Android Studio (Tools > AVD Manager)
2. Start the emulator
3. Run the app from Android Studio

## Project Structure

```
sublauncher/
├── app_sublauncher/          # Main application module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/         # Java source files
│   │   │   ├── res/          # Resources (layouts, strings, etc.)
│   │   │   └── AndroidManifest.xml
│   └── build.gradle          # App-level build config
├── build.gradle              # Root build config
├── settings.gradle           # Project settings
└── README.md                 # Project documentation
```

## Next Steps

After successfully building:

1. **Test the UI**: Launch the app and verify all buttons work
2. **Add Game Logic**: Integrate Minecraft downloading and launching
3. **Test on Multiple Devices**: Ensure compatibility
4. **Optimize Performance**: Profile and improve runtime

## Contributing to Build System

If you improve the build process:

1. Document your changes
2. Update this guide
3. Submit a pull request

## Resources

- [Android Developer Guide](https://developer.android.com/guide)
- [Gradle Build Tool](https://gradle.org/)
- [PojavLauncher Wiki](https://pojavlauncherteam.github.io/)
- [SubLauncher Issues](https://github.com/subhobhai943/sublauncher/issues)

## Support

If you encounter build issues:

1. Check existing [issues](https://github.com/subhobhai943/sublauncher/issues)
2. Search PojavLauncher documentation
3. Open a new issue with:
   - Your OS and version
   - Android Studio version
   - Full error log
   - Steps to reproduce
