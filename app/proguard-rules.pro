# ==========================================
# StatusHub India - ProGuard Rules
# ==========================================

# --- General ---
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses

# --- Kotlin ---
-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.** { *; }
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class **$Companion {
    public static ** INSTANCE;
}
-keepclassmembers class ** {
    @dagger.hilt.android.internal.WithInstallIn *;
}

# --- Kotlin Coroutines ---
-keepclassmembers class kotlinx.coroutines.** { *; }
-keep class kotlinx.coroutines.internal.MainDispatcherFactory { *; }
-keep class kotlinx.coroutines.CoroutineExceptionHandler { *; }

# --- Dagger/Hilt ---
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ComponentSupplier { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponentManager { *; }
-keep class * extends dagger.hilt.internal.EntryPoint { *; }
-keepnames class * extends dagger.hilt.android.internal.managers.HiltWrapper_ActivityHolderModuleEntryPoint { *; }
-keep class dagger.hilt.android.internal.lifecycle.HiltViewModelFactory$$InternalFactory { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }
-dontwarn javax.annotation.**
-dontwarn dagger.spi.**
-dontwarn dagger.internal.codegen.**
-dontwarn dagger.hilt.internal.util.**
-dontwarn dagger.hilt.internal.codegen.**

# --- Retrofit & Gson ---
-keepattributes Signature
-keepattributes Exceptions
-keepattributes RuntimeVisibleAnnotations
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# Keep your model classes (update package name when changing namespace)
-keep class com.statushub.india.data.remote.** { *; }
-keep class com.statushub.india.data.local.** { *; }

# --- OkHttp ---
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# --- Room ---
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**
-keep class androidx.room.** { *; }

# --- Coil ---
-keep class coil.** { *; }
-keep class coil3.** { *; }

# --- Compose ---
-keep class androidx.compose.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn androidx.compose.**

# --- Material3 ---
-keep class com.google.android.material.** { *; }

# --- WorkManager ---
-keep class androidx.work.** { *; }

# --- Google Play Services / AdMob ---
-keep public class com.google.android.gms.ads.** { public *; }
-keep class com.google.android.gms.ads.internal.** { *; }
-dontwarn com.google.android.gms.ads.**
-keep class com.google.android.gms.ads.nonagon.** { *; }

# --- Keep BuildConfig ---
-keep class com.statushub.india.BuildConfig { *; }

# --- Keep Application class ---
-keep class com.statushub.india.StatusHubApp { *; }
-keep class com.statushub.india.MainActivity { *; }

# --- Keep ViewModels ---
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keep class * extends androidx.lifecycle.AndroidViewModel { *; }
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# --- Keep Hilt ViewModel classes ---
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * { *; }
-keepclassmembers class * {
    @dagger.hilt.android.lifecycle.HiltViewModel <init>(...);
}

# --- Enum classes ---
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# --- Parcelable ---
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# --- Serializable ---
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# --- WebView (if used in future) ---
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
