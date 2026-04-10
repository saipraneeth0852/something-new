import java.util.Properties

fun getLocalProperty(key: String, defaultValue: String = ""): String {
    val properties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        properties.load(localPropertiesFile.inputStream())
    }
    return properties.getProperty(key) ?: defaultValue
}

fun getSigningProperty(key: String, defaultValue: String = ""): String {
    // Try environment variables first, then local.properties
    return System.getenv(key) ?: getLocalProperty(key, defaultValue)
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.statushub.india"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.statushub.india"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        buildConfigField("String", "PEXELS_API_KEY", "\"${getLocalProperty("PEXELS_API_KEY", "")}\"")

        manifestPlaceholders["adMobAppId"] = getLocalProperty("ADMOB_APP_ID", "")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val storeFile = getSigningProperty("RELEASE_STORE_FILE", "")
            if (storeFile.isNotEmpty()) {
                this.storeFile = file(storeFile)
                this.storePassword = getSigningProperty("RELEASE_STORE_PASSWORD", "")
                this.keyAlias = getSigningProperty("RELEASE_KEY_ALIAS", "")
                this.keyPassword = getSigningProperty("RELEASE_KEY_PASSWORD", "")
            }
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
            // Use AdMob test IDs for debug builds
            manifestPlaceholders["adMobAppId"] = "ca-app-pub-3940256099942544~3347511713"
            buildConfigField("String", "ADMOB_APP_ID", "\"ca-app-pub-3940256099942544~3347511713\"")
            buildConfigField("String", "BANNER_AD_UNIT_ID", "\"ca-app-pub-3940256099942544/6300978111\"")
            buildConfigField("String", "INTERSTITIAL_AD_UNIT_ID", "\"ca-app-pub-3940256099942544/1033173712\"")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            manifestPlaceholders["adMobAppId"] = getLocalProperty("ADMOB_APP_ID", "")
            buildConfigField("String", "ADMOB_APP_ID", "\"${getLocalProperty("ADMOB_APP_ID", "")}\"")
            buildConfigField("String", "BANNER_AD_UNIT_ID", "\"${getLocalProperty("BANNER_AD_UNIT_ID", "")}\"")
            buildConfigField("String", "INTERSTITIAL_AD_UNIT_ID", "\"${getLocalProperty("INTERSTITIAL_AD_UNIT_ID", "")}\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    lint {
        xmlReport = true
        checkDependencies = true
        abortOnError = false
        warningsAsErrors = false
        lintConfig = file("lint.xml")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.coil.compose)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.navigation.compose)
    implementation(libs.play.services.ads)
    implementation(libs.work.runtime.ktx)
    implementation(libs.androidx.material.icons.extended)
    implementation("androidx.compose.ui:ui-text-google-fonts:1.7.6")

    // Firebase BoM (manages versions for all Firebase dependencies)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
}