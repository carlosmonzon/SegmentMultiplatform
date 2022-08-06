import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.konan.target.KonanTarget

plugins {
    id("com.android.library")
    kotlin("multiplatform") version Versions.kotlinVersion
    kotlin("plugin.serialization") version Versions.kotlinVersion
//    kotlin("native.cocoapods") version "1.7.10"

//    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
    id("io.github.luca992.multiplatform-swiftpackage") version "2.0.5-arm64" // arm64 issue
}

group = "org.monzon"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}


val cInteropSegmentPath = "src/nativeInterop/cinterop/Segment"

// used to match the xcframework folder name
val KonanTarget.archVariant: String
    get() = if (this is KonanTarget.IOS_X64 || this is KonanTarget.IOS_SIMULATOR_ARM64) {
        "ios-arm64_i386_x86_64-simulator"
    } else {
        "ios-arm64_armv7"
    }

kotlin {
    android()
    js(BOTH) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }

    val frameworkBaseName = "SegmentMultiplatform"
    val xcf = XCFramework()

    fun nativeTargetConfig(): KotlinNativeTarget.() -> Unit = {
        val nativeFrameworkPaths = listOf(
            "Segment"
        ).map {
            projectDir.resolve("$cInteropSegmentPath/$it.xcframework/${konanTarget.archVariant}")
        }

        compilations.getByName("main") {
            val Segment by cinterops.creating {
                /**
                 * This path can be omitted if your .def file has the same name as cinterop and is placed in the src/nativeInterop/cinterop/ directory.
                 */
                //defFile("src/nativeInterop/cinterop/Segment.def")
                nativeFrameworkPaths.map {
                    compilerOpts("-F$it")
                    linkerOpts("-rpath", "$it")
                }
            }
        }

        binaries {
            framework {
                baseName = frameworkBaseName
                isStatic = true // needed when Segment is consumed via SPM in the iOS app
                xcf.add(this)
            }
            getTest("DEBUG").apply {
                nativeFrameworkPaths.map {
                    linkerOpts("-F$it")
                    linkerOpts("-rpath", "$it")
                }
                linkerOpts("-ObjC")
            }
        }
    }

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64 // available to KT 1.5.30
        else -> ::iosSimulatorArm64
    }


//    iosTarget("ios", nativeTargetConfig())
    ios(configure = nativeTargetConfig())
    iosSimulatorArm64(configure = nativeTargetConfig())

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                //TODO: move to Dependencies.kt
                implementation("com.segment.analytics.kotlin:android:1.7.0")
            }
        }

        val androidTest by getting

        val jsMain by getting
        val jsTest by getting


        val iosMain by getting
        val iosSimulatorArm64Main by getting
        iosSimulatorArm64Main.dependsOn(iosMain)

        val iosTest by sourceSets.getting
        val iosSimulatorArm64Test by sourceSets.getting
        iosSimulatorArm64Test.dependsOn(iosTest)


    }

}

android {
    compileSdk = Versions.androidCompileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    namespace = "com.monzon.analytics"
}
multiplatformSwiftPackage {
    packageName("MultiplatformAnalytics")
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("13") }
    }
}
