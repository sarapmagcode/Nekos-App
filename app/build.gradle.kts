plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.nekosapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nekosapp"
        minSdk = 29
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    val lifecycle_version = "2.8.5"
    val fragment_version = "1.8.3"
    val moshi_version = "1.15.1"
    val retrofit_version = "2.11.0"
    val coil_version = "2.7.0"
    val palette_version = "1.0.0"
    val loading_anim_version = "1.0.0"
    val nav_version = "2.7.0"
    val photoview_version = "2.0.0"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:$moshi_version")
    // Retrofit with Moshi Converter
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit_version")
    // Coil
    implementation("io.coil-kt:coil:$coil_version")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // Fragments
    implementation("androidx.fragment:fragment-ktx:$fragment_version")

    // Palette
    implementation("androidx.palette:palette:$palette_version")

    // Loading Animation (Custom)
    implementation("com.github.Marvel999:Android-Loading-Animation:$loading_anim_version")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // PhotoView (for pinch zoom)
    implementation("com.github.chrisbanes:PhotoView:$photoview_version")
}