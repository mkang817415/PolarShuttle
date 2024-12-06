// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt.gradle) apply false
    alias(libs.plugins.kotlin.serialization) apply false

    alias(libs.plugins.google.gms.google.services) apply false

}
