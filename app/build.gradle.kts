plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "dev.yash.dynamicweatherapp"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "dev.yash.dynamicweatherapp"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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

    dependencies {
        // Jetpack Compose BOM (Bill of Materials)
        val composeBom = platform("androidx.compose:compose-bom:2026.06.00")
        implementation(composeBom)
        androidTestImplementation(composeBom)

        // Core Compose & UI Elements
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.material3:material3")
        implementation("androidx.compose.ui:ui-tooling-preview")

        // Navigation & ViewModel integration for Compose
        implementation("androidx.navigation:navigation-compose:2.8.0")
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

        // Kotlin Coroutines for asynchronous operations
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

        // Retrofit (Network requests & JSON Parsing)
        implementation("com.squareup.retrofit2:retrofit:2.11.0")
        implementation("com.squareup.retrofit2:converter-gson:2.11.0")

        // Google Play Services (GPS & Location)
        implementation("com.google.android.gms:play-services-location:21.3.0")

        // WorkManager (15-minute background sync)
        implementation("androidx.work:work-runtime-ktx:2.11.0")
    }
}