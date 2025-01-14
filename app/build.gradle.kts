plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.looply"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.looply"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    viewBinding {
        enable = true
    }

    buildFeatures {
        dataBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Core & AppCompat
    implementation("androidx.core:core:1.13.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    // Material Components
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.fragment:fragment-ktx:1.5.7")

    // Firebase & UI
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.firebaseui:firebase-ui-auth:8.0.1")

    // YouTube Player
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.0.0")

    // RecyclerView & Other
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.browser:browser:1.4.0")
    implementation(libs.firebase.database)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Splashscreen
    implementation("androidx.core:core-splashscreen:1.0.1")
}
