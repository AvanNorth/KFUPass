plugins {
    id("com.android.application")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    kotlin("android")
}

android {
    namespace = "ru.pskda.kfupass.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "ru.pskda.kfupass.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    kapt {
        arguments { arg("room.schemaLocation", "$projectDir/schemas") }
    }
}

dependencies {
    val retrofit = "2.9.0"
    val okhttp = "4.9.3"
    val room = "2.4.2"

    implementation(project(":shared"))
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.0")
    implementation("androidx.compose.ui:ui:1.4.0")
    implementation("androidx.compose.ui:ui-tooling:1.4.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0")
    implementation("androidx.compose.foundation:foundation:1.4.0")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("androidx.activity:activity-compose:1.7.2")

    //Data
    implementation("androidx.room:room-runtime:$room")
    kapt("androidx.room:room-compiler:$room")

    //Network region
    implementation("com.squareup.retrofit2:retrofit:${retrofit}")
    implementation("com.squareup.retrofit2:converter-gson:${retrofit}")

    implementation("com.squareup.okhttp3:okhttp:${okhttp}")
    debugImplementation("com.squareup.okhttp3:logging-interceptor:${okhttp}")
    //endregion

    //Hilt
    implementation("com.google.dagger:hilt-android:2.46.1")
    kapt("com.google.dagger:hilt-android-compiler:2.46.1")

    //QR
    implementation("com.github.kenglxn.QRGen:android:3.0.1")
}