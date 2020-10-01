import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
}

var apiKey = ""
val key: String? = System.getenv("TMDB_API_KEY")
if (!key.isNullOrEmpty()) {
    apiKey = System.getenv("TMDB_API_KEY")
    apiKey = "\"$apiKey\""
} else {
    val secretsFile = rootProject.file("secrets.properties")
    if (secretsFile.exists()) {
        val secretsProperties = Properties()
        secretsProperties.load(FileInputStream(secretsFile))
        apiKey = secretsProperties.getProperty("API_KEY")
    }
}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "me.skrilltrax.themoviedb"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY", apiKey)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.4"
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
    buildTypes {
        getByName("release") {
            buildConfigField("boolean", "IS_DEBUG", "false")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            buildConfigField("boolean", "IS_DEBUG", "true")
        }
    }

    buildFeatures.viewBinding = true
    kapt.correctErrorTypes = true
}

dependencies {
    compileOnly(Dependencies.AndroidX.annotation)
    implementation(Dependencies.AndroidX.activity_ktx)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.constraint_layout)
    implementation(Dependencies.AndroidX.core_ktx)
    implementation(Dependencies.AndroidX.fragment_ktx)
    implementation(Dependencies.AndroidX.lifecycle_common)
    implementation(Dependencies.AndroidX.lifecycle_livedata_ktx)
    implementation(Dependencies.AndroidX.lifecycle_viewmodel_ktx)
    implementation(Dependencies.AndroidX.material)
    implementation(Dependencies.AndroidX.preference)
    implementation(Dependencies.AndroidX.recycler_view)
    implementation(Dependencies.AndroidX.room_ktx)
    implementation(Dependencies.AndroidX.viewpager2)

    implementation(Dependencies.ThirdParty.glide)
    implementation(Dependencies.ThirdParty.koin_android)
    implementation(Dependencies.ThirdParty.koin_androidx_scope)
    implementation(Dependencies.ThirdParty.koin_androidx_viewmodel)
    implementation(Dependencies.ThirdParty.lottie)
    implementation(Dependencies.ThirdParty.material_rating_bar)
    implementation(Dependencies.ThirdParty.okhttp_logging_interceptor)
    implementation(Dependencies.ThirdParty.retrofit_converter_gson)
    implementation(Dependencies.ThirdParty.timber)
    implementation(Dependencies.ThirdParty.timberkt)
    implementation(Dependencies.ThirdParty.whatthestack)

    debugImplementation(Dependencies.ThirdParty.whatthestack)

    testImplementation(Dependencies.Testing.junit)

    androidTestImplementation(Dependencies.Testing.AndroidX.runner)
    androidTestImplementation(Dependencies.Testing.AndroidX.espresso_core)

    kapt(Dependencies.AndroidX.room_compiler)
    kapt(Dependencies.ThirdParty.glide_compiler)
}
