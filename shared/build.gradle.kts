plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
//    id("dev.icerock.mobile.multiplatform-resources")
    kotlin("plugin.serialization") version "1.9.21"
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
//            export("dev.icerock.moko:resources:0.23.0")
//            export("dev.icerock.moko:graphics:0.9.0") // toUIColor here
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                val voyagerVersion = "1.0.0-rc07"
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
                // Used for the basic navigation
                implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
                // Allows us to use tab navigation for the bottom bar
                implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
                // Support for transition animations
                implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
                kotlin("org.jetbrains.kotlin:kotlin-multiplatform-mobile:1.2.1")
//                api("dev.icerock.moko:resources:0.23.0")
//                api("dev.icerock.moko:resources-compose:0.23.0")
            } }

        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
//multiplatformResources {
//    multiplatformResourcesPackage = "com.myapplication.cmm.sharingresources" // required
//    multiplatformResourcesClassName = "SharedRes" // optional, default MR
//}
