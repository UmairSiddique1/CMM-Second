plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                val voyagerVersion = "1.0.0-rc07"
                implementation(project(":shared"))
                implementation ("androidx.compose.material:material:1.5.4")
                implementation("androidx.compose.material3:material3:1.1.2")
                val camerax_version = "1.3.0"
                implementation ("androidx.camera:camera-camera2:$camerax_version")
                implementation ("androidx.camera:camera-lifecycle:$camerax_version")
                implementation ("androidx.camera:camera-view:1.4.0-alpha02")
                implementation ("io.coil-kt:coil-compose:2.5.0")
                implementation ("io.github.farimarwat:documentscanner:1.1")

                implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
                // Allows us to use tab navigation for the bottom bar
                implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
                // Support for transition animations
                implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "com.myapplication.MyApplication"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlin {
        jvmToolchain(18)
    }
}
