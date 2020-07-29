import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
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
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures.viewBinding = true
    kapt.correctErrorTypes = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72")
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.activity:activity-ktx:1.2.0-alpha07")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")

    //Timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.8.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.7.2")
    implementation("com.squareup.retrofit2:converter-gson:2.8.1")

    //Material Components
    implementation("com.google.android.material:material:1.1.0")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0-alpha06")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-alpha06")

    //Gson
    implementation("com.google.code.gson:gson:2.8.6")

    //Viewpager
    implementation("androidx.viewpager2:viewpager2:1.1.0-alpha01")

    //Anko Commons
    implementation("org.jetbrains.anko:anko-commons:0.10.8")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.11.0")
    kapt("com.github.bumptech.glide:compiler:4.11.0")

    //Lottie
    implementation("com.airbnb.android:lottie:3.4.1")

    //Rating Bar
    implementation("me.zhanghai.android.materialratingbar:library:1.4.0")

    //Koin
    implementation("org.koin:koin-android:2.1.3")
    implementation("org.koin:koin-androidx-scope:2.1.3")
    implementation("org.koin:koin-androidx-viewmodel:2.1.3")

    //Viewpager 2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    //room
    implementation("androidx.room:room-runtime:2.2.5")
    implementation("androidx.room:room-ktx:2.2.5")
    kapt("androidx.room:room-compiler:2.2.5")

    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
