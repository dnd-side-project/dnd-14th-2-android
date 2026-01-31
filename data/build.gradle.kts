plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.smtm.pickle.data"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":domain"))

    // Room
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // Retrofit
    implementation(libs.bundles.retrofit)

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // DataStore
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Logging
    implementation(libs.timber)

    testImplementation(libs.bundles.testing)
}
