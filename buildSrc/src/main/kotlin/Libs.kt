import kotlin.String

/**
 * Generated by https://github.com/jmfayard/buildSrcVersions
 *
 * Update this file with
 *   `$ ./gradlew buildSrcVersions`
 */
object Libs {
  /**
   * https://developer.android.com/jetpack/androidx
   */
  const val appcompat: String = "androidx.appcompat:appcompat:" + Versions.appcompat

  /**
   * http://tools.android.com
   */
  const val constraintlayout: String = "androidx.constraintlayout:constraintlayout:" +
      Versions.constraintlayout

  /**
   * https://developer.android.com/jetpack/androidx
   */
  const val core_ktx: String = "androidx.core:core-ktx:" + Versions.core_ktx

  const val databinding_adapters: String = "androidx.databinding:databinding-adapters:" +
      Versions.androidx_databinding

  /**
   * https://developer.android.com/studio
   */
  const val databinding_common: String = "androidx.databinding:databinding-common:" +
      Versions.androidx_databinding

  /**
   * https://developer.android.com/studio
   */
  const val databinding_compiler: String = "androidx.databinding:databinding-compiler:" +
      Versions.androidx_databinding

  const val databinding_runtime: String = "androidx.databinding:databinding-runtime:" +
      Versions.androidx_databinding

  /**
   * https://developer.android.com/topic/libraries/architecture/index.html
   */
  const val lifecycle_extensions: String = "androidx.lifecycle:lifecycle-extensions:" +
      Versions.lifecycle_extensions

  /**
   * https://developer.android.com/topic/libraries/architecture/index.html
   */
  const val lifecycle_viewmodel_savedstate: String =
      "androidx.lifecycle:lifecycle-viewmodel-savedstate:" + Versions.lifecycle_viewmodel_savedstate

  /**
   * https://developer.android.com/topic/libraries/architecture/index.html
   */
  const val navigation_fragment_ktx: String = "androidx.navigation:navigation-fragment-ktx:" +
      Versions.androidx_navigation

  /**
   * https://developer.android.com/topic/libraries/architecture/index.html
   */
  const val navigation_safe_args_gradle_plugin: String =
      "androidx.navigation:navigation-safe-args-gradle-plugin:" + Versions.androidx_navigation

  /**
   * https://developer.android.com/topic/libraries/architecture/index.html
   */
  const val navigation_ui: String = "androidx.navigation:navigation-ui:" +
      Versions.androidx_navigation

  /**
   * https://developer.android.com/topic/libraries/architecture/index.html
   */
  const val room_compiler: String = "androidx.room:room-compiler:" + Versions.androidx_room

  /**
   * https://developer.android.com/topic/libraries/architecture/index.html
   */
  const val room_ktx: String = "androidx.room:room-ktx:" + Versions.androidx_room

  /**
   * https://developer.android.com/topic/libraries/architecture/index.html
   */
  const val room_runtime: String = "androidx.room:room-runtime:" + Versions.androidx_room

  /**
   * https://developer.android.com/testing
   */
  const val espresso_core: String = "androidx.test.espresso:espresso-core:" + Versions.espresso_core

  /**
   * https://developer.android.com/testing
   */
  const val androidx_test_runner: String = "androidx.test:runner:" + Versions.androidx_test_runner

  /**
   * https://developer.android.com/jetpack/androidx
   */
  const val viewpager2: String = "androidx.viewpager2:viewpager2:" + Versions.viewpager2

  /**
   * https://github.com/airbnb/lottie-android
   */
  const val lottie: String = "com.airbnb.android:lottie:" + Versions.lottie

  /**
   * https://developer.android.com/studio
   */
  const val aapt2: String = "com.android.tools.build:aapt2:" + Versions.aapt2

  /**
   * https://developer.android.com/studio
   */
  const val com_android_tools_build_gradle: String = "com.android.tools.build:gradle:" +
      Versions.com_android_tools_build_gradle

  /**
   * https://developer.android.com/studio
   */
  const val lint_gradle: String = "com.android.tools.lint:lint-gradle:" + Versions.lint_gradle

  /**
   * https://github.com/google/desugar_jdk_libs
   */
  const val desugar_jdk_libs: String = "com.android.tools:desugar_jdk_libs:" +
      Versions.desugar_jdk_libs

  /**
   * http://r8.googlesource.com/r8
   */
  const val desugar_jdk_libs_configuration: String =
      "com.android.tools:desugar_jdk_libs_configuration:" + Versions.desugar_jdk_libs_configuration

  /**
   * https://github.com/facebook/stetho
   */
  const val stetho_okhttp3: String = "com.facebook.stetho:stetho-okhttp3:" +
      Versions.com_facebook_stetho

  /**
   * https://github.com/facebook/stetho
   */
  const val stetho: String = "com.facebook.stetho:stetho:" + Versions.com_facebook_stetho

  /**
   * https://github.com/bumptech/glide
   */
  const val com_github_bumptech_glide_compiler: String = "com.github.bumptech.glide:compiler:" +
      Versions.com_github_bumptech_glide

  /**
   * https://github.com/bumptech/glide
   */
  const val glide: String = "com.github.bumptech.glide:glide:" + Versions.com_github_bumptech_glide

  /**
   * http://developer.android.com/tools/extras/support-library.html
   */
  const val material: String = "com.google.android.material:material:" + Versions.material

  /**
   * https://github.com/google/gson
   */
  const val gson: String = "com.google.code.gson:gson:" + Versions.gson

  /**
   * https://github.com/JakeWharton/timber
   */
  const val timber: String = "com.jakewharton.timber:timber:" + Versions.timber

  /**
   * http://github.com/square/leakcanary/
   */
  const val leakcanary_android: String = "com.squareup.leakcanary:leakcanary-android:" +
      Versions.leakcanary_android

  /**
   * https://github.com/square/okhttp
   */
  const val logging_interceptor: String = "com.squareup.okhttp3:logging-interceptor:" +
      Versions.logging_interceptor

  /**
   * https://github.com/square/retrofit/
   */
  const val converter_gson: String = "com.squareup.retrofit2:converter-gson:" +
      Versions.com_squareup_retrofit2

  /**
   * https://github.com/square/retrofit/
   */
  const val retrofit: String = "com.squareup.retrofit2:retrofit:" + Versions.com_squareup_retrofit2

  const val de_fayard_buildsrcversions_gradle_plugin: String =
      "de.fayard.buildSrcVersions:de.fayard.buildSrcVersions.gradle.plugin:" +
      Versions.de_fayard_buildsrcversions_gradle_plugin

  /**
   * http://junit.org
   */
  const val junit: String = "junit:junit:" + Versions.junit

  /**
   * https://github.com/zhanghai/MaterialRatingBar
   */
  const val library: String = "me.zhanghai.android.materialratingbar:library:" + Versions.library

  /**
   * https://github.com/JetBrains/anko
   */
  const val anko_commons: String = "org.jetbrains.anko:anko-commons:" + Versions.anko_commons

  /**
   * https://kotlinlang.org/
   */
  const val kotlin_android_extensions_runtime: String =
      "org.jetbrains.kotlin:kotlin-android-extensions-runtime:" + Versions.org_jetbrains_kotlin

  /**
   * https://kotlinlang.org/
   */
  const val kotlin_android_extensions: String = "org.jetbrains.kotlin:kotlin-android-extensions:" +
      Versions.org_jetbrains_kotlin

  /**
   * https://kotlinlang.org/
   */
  const val kotlin_annotation_processing_gradle: String =
      "org.jetbrains.kotlin:kotlin-annotation-processing-gradle:" + Versions.org_jetbrains_kotlin

  /**
   * https://kotlinlang.org/
   */
  const val kotlin_gradle_plugin: String = "org.jetbrains.kotlin:kotlin-gradle-plugin:" +
      Versions.org_jetbrains_kotlin

  /**
   * https://kotlinlang.org/
   */
  const val kotlin_stdlib_jdk7: String = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:" +
      Versions.org_jetbrains_kotlin

  const val koin_android: String = "org.koin:koin-android:" + Versions.org_koin

  const val koin_androidx_scope: String = "org.koin:koin-androidx-scope:" + Versions.org_koin

  const val koin_androidx_viewmodel: String = "org.koin:koin-androidx-viewmodel:" +
      Versions.org_koin
}
