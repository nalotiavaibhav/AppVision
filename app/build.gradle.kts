plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.compose.compiler)
    id("dagger.hilt.android.plugin")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.example.alarmtrial"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.alarmtrial"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
        }
        register("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.example.alarmtrial"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.appcompat)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation (libs.androidx.navigation.compose)
    implementation (libs.coil.compose)
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    
    implementation (libs.androidx.lifecycle.runtime.compose)
    implementation (libs.androidx.runtime.livedata)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    kapt (libs.androidx.room.compiler)
    ksp (libs.androidx.room.room.compiler)

    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

    // To use Kotlin Symbol Processing (KSP)
    ksp(libs.androidx.room.compiler)

    implementation (libs.kotlinx.coroutines.core)

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    // optional - Test helpers
    testImplementation(libs.androidx.room.testing)

    // optional - Paging 3 Integration
    implementation(libs.androidx.room.paging)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx.v262)
    // Annotation processor
    ksp(libs.androidx.lifecycle.compiler)







    implementation("androidx.compose.ui:ui:${rootProject.extra.get("composeVersion")}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra.get("composeVersion")}")

    implementation(libs.androidx.lifecycle.service)
    implementation(libs.androidx.appcompat)
    implementation("com.google.android.material:material:1.9.0")


    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra.get("composeVersion")}")
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra.get("composeVersion")}")



    // materiel3
    implementation("androidx.compose.material3:material3:1.1.0")
    // materiel-icon
    implementation("androidx.compose.material:material-icons-extended:1.4.3")
    // systemUiController
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    // Hilt
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-compiler:2.47")

    // Zhuinden flow-combinetuple
    implementation("com.github.Zhuinden:flow-combinetuple-kt:1.1.1")
    // Swipe
    implementation("me.saket.swipe:swipe:1.1.1")
    // sdp
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    // ssp
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    // gson
    implementation("com.google.code.gson:gson:2.10.1")
    // accompanist-permissions
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")
    // work-manager
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("androidx.hilt:hilt-work:1.0.0")
    // Desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

}