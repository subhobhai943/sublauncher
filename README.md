# SubLauncher

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green.svg" alt="Platform">
  <img src="https://img.shields.io/badge/License-LGPL%20v3-blue.svg" alt="License">
  <img src="https://img.shields.io/badge/Status-In%20Development-orange.svg" alt="Status">
</p>

**SubLauncher** is a Minecraft Java Edition launcher for Android devices, based on the excellent work of the [PojavLauncher](https://github.com/PojavLauncherTeam/PojavLauncher) team.

## 📋 About

SubLauncher allows you to play Minecraft: Java Edition on your Android device. It's built upon the foundation of PojavLauncher and aims to provide a seamless Minecraft experience on mobile.

## ✨ Features

- 🎮 Launch Minecraft Java Edition on Android
- 📱 Modern Material Design UI
- ⚙️ Profile management
- 🔧 Customizable settings
- 🎨 Mod support (Forge & Fabric)
- 🌍 Multiple Minecraft versions support

## 🚀 Getting Started

### Prerequisites

- Android device running Android 5.0 (API 21) or higher
- At least 2GB of RAM recommended
- Storage space for Minecraft installation

### Installation

1. Download the latest APK from [Releases](https://github.com/subhobhai943/sublauncher/releases)
2. Install the APK on your Android device
3. Grant necessary permissions (storage access)
4. Launch SubLauncher and set up your profile
5. Download Minecraft and start playing!

## 🛠️ Building from Source

### Requirements

- Android Studio Arctic Fox or newer
- JDK 8 or higher
- Android SDK with API 34
- NDK (for native components)
- Git

### Build Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/subhobhai943/sublauncher.git
   cd sublauncher
   ```

2. **Build the project:**
   ```bash
   ./gradlew :app_sublauncher:assembleDebug
   ```
   (Use `gradlew.bat` on Windows)

3. **Find the APK:**
   The built APK will be located in:
   ```
   app_sublauncher/build/outputs/apk/debug/
   ```

### Advanced Build

For a complete build including all native components, you'll need:

- Pre-built JRE for Android (from PojavLauncher CI)
- LWJGL3 custom build
- Native library dependencies

Refer to the original [PojavLauncher build guide](https://github.com/PojavLauncherTeam/PojavLauncher#building) for detailed instructions on building native components.

## 📖 Current Development Status

**SubLauncher is currently in early development.** The current version includes:

- ✅ Basic UI structure
- ✅ Material Design theme
- ✅ Project configuration
- ⏳ Minecraft launcher core (in progress)
- ⏳ JRE integration (in progress)
- ⏳ Native components (in progress)

## 🔧 Technology Stack

- **Language:** Java
- **UI Framework:** Material Design 3
- **Build System:** Gradle
- **Minimum SDK:** 21 (Android 5.0)
- **Target SDK:** 34 (Android 14)

## 🤝 Contributing

Contributions are welcome! If you'd like to contribute:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

SubLauncher is licensed under the [GNU Lesser General Public License v3.0](LICENSE).

This project is based on [PojavLauncher](https://github.com/PojavLauncherTeam/PojavLauncher), which is also licensed under LGPL v3.

## 🙏 Credits

### Based On
- [PojavLauncher](https://github.com/PojavLauncherTeam/PojavLauncher) - The original Minecraft Java Edition launcher for Android
- [Boardwalk](https://github.com/zhuowei/Boardwalk) - The project that started it all

### Dependencies
- [OpenJDK](https://openjdk.java.net/) - Java runtime
- [LWJGL3](https://www.lwjgl.org/) - Lightweight Java Game Library
- [GL4ES](https://github.com/ptitSeb/gl4es) - OpenGL to OpenGL ES translation
- [Mesa 3D](https://www.mesa3d.org/) - Graphics library
- [Material Components](https://material.io/develop/android) - UI components

### Special Thanks
- The PojavLauncher Team for their incredible work
- All contributors to the open-source projects this launcher depends on

## ⚠️ Important Notes

- This launcher requires a valid Minecraft Java Edition account
- SubLauncher is not affiliated with Mojang Studios or Microsoft
- Minecraft™ is a trademark of Mojang Synergies AB

## 📧 Contact

For questions or support:
- Open an [issue](https://github.com/subhobhai943/sublauncher/issues)
- Check the [discussions](https://github.com/subhobhai943/sublauncher/discussions)

## 🗺️ Roadmap

- [ ] Complete core launcher functionality
- [ ] Integrate JRE and native libraries
- [ ] Add profile management
- [ ] Implement mod loader support
- [ ] Add settings customization
- [ ] Optimize performance
- [ ] Add version selector
- [ ] Implement auto-updater

---

<p align="center">Made with ❤️ by Subhobhai</p>
<p align="center">Built upon the excellent work of the PojavLauncher Team</p>