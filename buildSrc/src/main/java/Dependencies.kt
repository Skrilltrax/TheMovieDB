const val composeVersion = "1.0.0-alpha04"
const val hiltVersion = "2.29.1-alpha"
const val hiltAndroidXVersion = "1.0.0-alpha02"
const val kotlinVersion = "1.4.10"

object Plugins {
    const val agp = "com.android.tools.build:gradle:4.2.0-alpha13"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
}

object Dependencies {
    const val lifecycleVersion = "2.3.0-beta01"
    const val roomVersion = "2.3.0-alpha02"

    object Kotlin {
        object Coroutines {
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinVersion"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinVersion"
        }

        const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
        const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    }

    object AndroidX {
        object Compose {
            const val ui = "androidx.compose.ui:ui:$composeVersion"
            const val material = "androidx.compose.material:material:$composeVersion"
        }

        const val activity_ktx = "androidx.activity:activity-ktx:1.2.0-beta01"
        const val annotation = "androidx.annotation:annotation:1.2.0-alpha01"
        const val appcompat = "androidx.appcompat:appcompat:1.3.0-alpha02"
        const val constraint_layout = "androidx.constraintlayout:constraintlayout:2.0.1"
        const val core_ktx = "androidx.core:core-ktx:1.5.0-alpha03"
        const val fragment_ktx = "androidx.fragment:fragment-ktx:1.3.0-beta01"
        const val hilt = "com.google.dagger:hilt-android:$hiltVersion"
        const val hilt_android_compiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
        const val hilt_compiler = "androidx.hilt:hilt-compiler:$hiltAndroidXVersion"
        const val hilt_lifecycle_viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:$hiltAndroidXVersion"
        const val lifecycle_common = "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
        const val lifecycle_livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
        const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        const val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        const val material = "com.google.android.material:material:1.3.0-alpha02"
        const val preference = "androidx.preference:preference:1.1.1"
        const val recycler_view = "androidx.recyclerview:recyclerview:1.2.0-alpha05"
        const val room_compiler = "androidx.room:room-compiler:$roomVersion"
        const val room_ktx = "androidx.room:room-ktx:$roomVersion"
        const val ui_tooling = "androidx.ui:ui-tooling:$composeVersion"
        const val viewpager2 = "androidx.viewpager2:viewpager2:1.1.0-alpha01"
    }

    object ThirdParty {
        const val glide = "com.github.bumptech.glide:glide:4.11.0"
        const val glide_compiler = "com.github.bumptech.glide:glide:4.11.0"
        const val lottie = "com.airbnb.android:lottie:3.4.1"
        const val material_rating_bar = "me.zhanghai.android.materialratingbar:library:1.4.0"
        const val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:4.7.2"
        const val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:2.8.1"
        const val timber = "com.jakewharton.timber:timber:4.7.1"
        const val timberkt = "com.github.ajalt:timberkt:1.5.1"
        const val whatthestack = "com.github.haroldadmin:WhatTheStack:0.0.4"
    }

    object Testing {
        const val junit = "junit:junit:4.13"
        const val kotlin_test_junit = "org.jetbrains.kotlin:kotlin-test-junit:1.3.72"

        object AndroidX {
            const val runner = "androidx.test:runner:1.3.0-rc01"
            const val rules = "androidx.test:rules:1.3.0-rc01"
            const val junit = "androidx.test.ext:junit:1.1.2-rc01"
            const val espresso_core = "androidx.test.espresso:espresso-core:3.3.0-rc01"
            const val espresso_intents = "androidx.test.espresso:espresso-intents:3.3.0-rc01"
        }
    }
}
