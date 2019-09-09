import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

val secretsFile = rootProject.file("secrets.properties")
val secretsProperties = Properties()
secretsProperties.load(FileInputStream(secretsFile))

android {
    compileSdkVersion(29)
    dataBinding.isEnabled = true
    defaultConfig {
        applicationId = "me.skrilltrax.themoviedb"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY", secretsProperties.getProperty("API_KEY"))

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Libs.kotlin_stdlib_jdk7)
    implementation(Libs.appcompat)
    implementation(Libs.core_ktx)
    implementation(Libs.constraintlayout)
    //Timber
    implementation(Libs.timber)
    //Retrofit
    implementation(Libs.retrofit)
    implementation(Libs.logging_interceptor)
    implementation(Libs.converter_gson)
    //Material Components
    implementation(Libs.material)
    //Navigation
    implementation(Libs.navigation_fragment_ktx)
    implementation(Libs.navigation_ui)
    //Lifecycle
    implementation(Libs.lifecycle_extensions)
    implementation(Libs.lifecycle_viewmodel_savedstate)
    //Gson
    implementation(Libs.gson)
    //Viewpager
    implementation(Libs.viewpager2)
    //Anko Commons
    implementation(Libs.anko_commons)
    //Glide
    implementation(Libs.glide)
    annotationProcessor(Libs.com_github_bumptech_glide_compiler)
    //Lottie
    implementation(Libs.lottie)
    //Rating Bar
    implementation(Libs.library)
    //Room
    implementation(Libs.room_runtime)
    kapt(Libs.room_compiler) // For Kotlin use kapt instead of annotationProcessor
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(Libs.room_ktx)


    testImplementation(Libs.junit)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.espresso_core)
}
