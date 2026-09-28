plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }

android {
    namespace = "com.bradleyvirtualsolutions.checkers"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.bradleyvirtualsolutions.checkers"
        minSdk = 29
        targetSdk = 32
        versionCode = 1
        versionName = "1.0.0"
        buildConfigField("String", "BRADLEY_WEB_URL", "\"https://webxr-checkers.vercel.app\"")
        buildConfigField("String", "META_APP_ID", "\"${project.findProperty("META_APP_ID") ?: "1285271641346248"}\"")
    }
    buildFeatures { buildConfig = true }
}

dependencies { implementation("androidx.activity:activity-ktx:1.9.3") }
