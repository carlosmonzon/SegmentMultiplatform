import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.KonanTarget

plugins {
    id("com.android.library")
    kotlin("multiplatform") version "1.7.10"
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
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

    fun nativeTargetConfig(): KotlinNativeTarget.() -> Unit = {
        val nativeFrameworkPaths = listOf(
            "Segment"
        ).map {
            projectDir.resolve("$cInteropSegmentPath/$it.xcframework/${konanTarget.archVariant}")
        }

        binaries {
            getTest("DEBUG").apply {
                linkerOpts(nativeFrameworkPaths.map { "-F$it" })
                linkerOpts("-ObjC")
            }
        }

        compilations.getByName("main") {
            cinterops.create("Segment") {
                /**
                 * This path can be omitted if your .def file has the same name as cinterop and is placed in the src/nativeInterop/cinterop/ directory.
                 */
                //defFile("src/nativeInterop/cinterop/Segment.def")
                compilerOpts(nativeFrameworkPaths.map { "-F$it" })
                extraOpts = listOf("-compiler-option", "-DNS_FORMAT_ARGUMENT(A)=", "-verbose")
            }
        }
    }
    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64 // available to KT 1.5.30
        else -> ::iosX64
    }

    iosTarget("ios", nativeTargetConfig())

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
                implementation( "com.segment.analytics.kotlin:android:1.7.0")
            }
        }

        val androidTest by getting

        val jsMain by getting
        val jsTest by getting
        val iosMain by getting
        val iosTest by getting
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
