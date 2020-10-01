plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

dependencies {
    // TODO: Remove this Android Studio canary 08 fix
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")
}
