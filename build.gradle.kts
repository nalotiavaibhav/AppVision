buildscript {
    val kotlinVersion by rootProject.extra { "1.9.22" }
    val composeVersion by rootProject.extra { "1.4.3" }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.47")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
    repositories {
        mavenCentral()
        google()
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    id("com.android.library") version "8.2.2" apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.devtools.ksp) apply false
    id("com.android.test") version "8.2.2" apply false
    alias(libs.plugins.compose.compiler) apply false
}
