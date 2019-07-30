import org.jetbrains.kotlin.config.KotlinCompilerVersion
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${KotlinCompilerVersion.VERSION}")
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.core:core-ktx:1.2.0-alpha02")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")

//Timber
    implementation("com.jakewharton.timber:timber:4.7.1")
//Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.6.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.14.2")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
//Material Components
    implementation("com.google.android.material:material:1.0.0")
//Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.1.0-beta02")
    implementation("androidx.navigation:navigation-ui:2.1.0-beta02")
//Lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:1.0.0-alpha02")
//Gson
    implementation("com.google.code.gson:gson:2.8.5")
//Viewpager`
    implementation("androidx.viewpager2:viewpager2:1.0.0-beta02")
//Anko Commons
    implementation("org.jetbrains.anko:anko-commons:0.10.8")
//Glide
    implementation("com.github.bumptech.glide:glide:4.9.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.9.0")
//Lottie
    implementation("com.airbnb.android:lottie:3.0.7")
//Rating Bar
    implementation("me.zhanghai.android.materialratingbar:library:1.3.2")
//Room
    implementation("androidx.room:room-runtime:2.2.0-alpha01")
    kapt("androidx.room:room-compiler:2.2.0-alpha01") // For Kotlin use kapt instead of annotationProcessor
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.2.0-alpha01")


    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
