// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
    dependencies {
        classpath(Libs.com_android_tools_build_gradle)
        classpath(Libs.kotlin_gradle_plugin)
        classpath(Libs.navigation_safe_args_gradle_plugin)
    }
}

plugins {
    id("de.fayard.buildSrcVersions") version "0.4.2"
    id("com.diffplug.gradle.spotless") version "3.26.1"
}

spotless {
    kotlin {
        target("**/*.kt")
        ktlint()
    }
    kotlinGradle {
        target("*.gradle.kts", "additionalScripts/*.gradle.kts")
        ktlint()
    }
}

buildSrcVersions {
    indent = "    "
    renameLibs = "Libs"
    renameVersions = "Versions"
    rejectedVersionKeywords("cr", "m", "preview", "eap")
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://www.jitpack.io")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

/*task<Delete>("clean"){
    delete(rootProject.buildDir)
}*/
