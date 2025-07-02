
plugins {
    alias(libs.plugins.android.application) apply false
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.kotlin.serialization)
    kotlin("kapt")
}

android {
    namespace = "com.sa.feature_home"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Core module dependency
    implementation(project(":core"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    implementation(libs.google.dagger.hilt)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.paging.compose.android)
    kapt(libs.google.dagger.hilt.compiler)

    // Network
    implementation(libs.bundles.retrofit.group)
    api(libs.bundles.retrofit.group)

    // Paging
    implementation(libs.paging.common)
    implementation(libs.paging.runtime)
    implementation(libs.paging.common.ktx)

}