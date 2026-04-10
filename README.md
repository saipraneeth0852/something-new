# StatusHub India

A beautiful, production-ready Android app for sharing status images, wallpapers, and quotes — built with **Jetpack Compose**, **Hilt**, **Room**, and **Firebase**.

## 🚀 Features

- **Status Maker** — Create beautiful status cards with 8 gradient templates, customizable quotes, and one-tap WhatsApp sharing
- **Wallpapers** — Browse & download HD wallpapers from Pexels API with infinite scroll
- **Daily Quotes** — Curated quotes in Hindi, English, and Telugu stored locally via Room
- **AdMob Integration** — Banner + Interstitial ads with test IDs for debug builds
- **Daily Notifications** — Scheduled via WorkManager with proper Android 13+ permission handling
- **Crash Reporting** — Firebase Crashlytics for production monitoring
- **Analytics** — Firebase Analytics for user insights

## 🛠 Tech Stack

| Category | Technology |
|---|---|
| **UI** | Jetpack Compose, Material3 |
| **Architecture** | MVVM + Repository |
| **DI** | Hilt (Dagger) |
| **Networking** | Retrofit + OkHttp |
| **Image Loading** | Coil Compose |
| **Local DB** | Room |
| **Navigation** | Compose Navigation |
| **Background Work** | WorkManager |
| **Ads** | Google AdMob |
| **Monitoring** | Firebase Crashlytics + Analytics |
| **Build** | Gradle 8.13, Kotlin 2.0.21 |
| **Min SDK** | 24 (Android 7.0) |
| **Target SDK** | 36 |

## 🏗 Project Structure

```
app/src/main/java/com/statushub/india/
├── data/
│   ├── local/          # Room database, DAOs, entities
│   ├── remote/         # Retrofit API interfaces
│   └── repository/     # Data repositories
├── di/                 # Hilt dependency injection modules
├── presentation/       # UI layer (screens, ViewModels, components)
│   ├── home/
│   ├── navigation/
│   ├── quotes/
│   ├── splash/
│   ├── status_maker/
│   └── wallpapers/
├── ui/theme/           # Compose theme, colors, typography
├── util/               # Configuration & utility classes
└── worker/             # WorkManager workers for background tasks
```

## 🔑 Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/your-username/statushub-india.git
cd statushub-india
```

### 2. Add API Keys
Create or edit `local.properties` in the project root:
```properties
sdk.dir=/path/to/your/android/sdk

# Pexels API (get from https://www.pexels.com/api/)
PEXELS_API_KEY=your_pexels_api_key_here

# AdMob (use test IDs for development)
ADMOB_APP_ID=ca-app-pub-3940256099942544~3347511713
BANNER_AD_UNIT_ID=ca-app-pub-3940256099942544/6300978111
INTERSTITIAL_AD_UNIT_ID=ca-app-pub-3940256099942544/1033173712
```

### 3. Firebase Setup
1. Create a Firebase project at [console.firebase.google.com](https://console.firebase.google.com)
2. Add Android apps with package names:
   - `com.statushub.india` (release)
   - `com.statushub.india.debug` (debug)
3. Download `google-services.json` and place it in `app/`
4. Enable **Crashlytics** and **Analytics** in the Firebase console

### 4. Build & Run
```bash
./gradlew assembleDebug
```
Or open in Android Studio and click **Run**.

## 🔐 Release Build

To build a signed release APK, set these environment variables or add them to `local.properties`:

```properties
RELEASE_STORE_FILE=/path/to/keystore.jks
RELEASE_STORE_PASSWORD=your_store_password
RELEASE_KEY_ALIAS=your_key_alias
RELEASE_KEY_PASSWORD=your_key_password
```

Then run:
```bash
./gradlew assembleRelease
```

## 🧪 Testing

```bash
# Run unit tests
./gradlew testDebugUnitTest

# Run instrumented tests (requires emulator/device)
./gradlew connectedAndroidTest

# Run lint
./gradlew lintDebug
```

## 📦 CI/CD

This project uses GitHub Actions for continuous integration. See `.github/workflows/android-ci.yml`.

**Required GitHub Secrets:**
- `PEXELS_API_KEY`
- `ADMOB_APP_ID`
- `BANNER_AD_UNIT_ID`
- `INTERSTITIAL_AD_UNIT_ID`
- `RELEASE_KEYSTORE_BASE64` (Base64-encoded keystore)
- `RELEASE_STORE_PASSWORD`
- `RELEASE_KEY_ALIAS`
- `RELEASE_KEY_PASSWORD`
- `GOOGLE_SERVICES_JSON` (Contents of google-services.json)

## 📜 License

Copyright © 2026 StatusHub India. All rights reserved.
# something-new
