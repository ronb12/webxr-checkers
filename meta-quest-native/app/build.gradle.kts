plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }

android {
    namespace = "com.bradleyvirtualsolutions.checkers"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.bradleyvirtualsolutions.checkers"
        minSdk = 29
        targetSdk = 32
        versionCode = 2
        versionName = "1.1.0"
        buildConfigField("String", "BRADLEY_WEB_URL", "\"https://webxr-checkers.vercel.app\"")
        buildConfigField("String", "META_APP_ID", "\"${project.findProperty("META_APP_ID") ?: "1285271641346248"}\"")
    }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { buildConfig = true }
}

dependencies {
    implementation("androidx.activity:activity-ktx:1.9.3")
    implementation("com.meta.horizon.platform.sdk:core-kotlin:0.2.2")
    implementation("com.meta.horizon.platform.sdk:group-presence-kotlin:0.2.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
}
