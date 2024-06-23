plugins {
    // Apply the Kotlin Android plugin
    id("com.android.application")
    id("kotlin-android")
}



android {
    compileSdkVersion(34)
    buildToolsVersion("34.0.0")
    namespace = "com.example.smsparserapp"

    defaultConfig {
        applicationId = "com.example.smsparserapp"
        minSdkVersion(24)
        targetSdkVersion(34)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        // Enable Jetpack Compose
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packagingOptions {
        // Exclude certain files from packaging
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    // Add AndroidX dependencies
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.ui:ui:1.1.0-alpha06")
    implementation("androidx.compose.ui:ui-graphics:1.1.0-alpha06")
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.0-alpha06")
    implementation("com.google.android.material:material:1.5.0-alpha01")
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.material3.android)

    // Add testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // MPAndroidChart dependency
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}
