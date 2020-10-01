// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Plugins.agp)
        classpath(Plugins.kotlin)
        classpath(Plugins.hilt)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("com.diffplug.gradle.spotless") version "3.26.1"
    id("com.github.ben-manes.versions") version "0.31.0"
}

spotless {
    kotlin {
        target("**/*.kt")
        ktlint()
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint()
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://www.jitpack.io")
    }
}
