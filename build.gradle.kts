import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.KonanTarget

plugins {
    id("com.android.library")
    kotlin("multiplatform") version Versions.kotlinVersion
    kotlin("plugin.serialization") version Versions.kotlinVersion
    id("com.google.devtools.ksp") version Versions.KSP.ksp
    id("co.touchlab.faktory.kmmbridge") version "0.3.1"
    `maven-publish`
}



kmmbridge {
//  is mandatory for this flow. Without that, files will not be published anywhere
//  (there are other publishing options available).
    githubReleaseArtifacts()

//  Similar to GitTagVersionManager, but calls the GitHub api to create a Git release.
//  Only usable with GitHub, but preferred to GitTagVersionManager if you are using GitHub.
    githubReleaseVersions()
    spm(project.rootDir.path) // issue if not clearing a path: path may not be null or empty string. path=''
    versionPrefix.set("0.1")
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
    android {
        publishLibraryVariants("release", "debug")
    }

    val frameworkBaseName = "SegmentMultiplatform"

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
        val commonMain by getting {
            dependencies {
                implementation(project(":annotation"))
            }
            // generated code by PropertyProcessor
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("androidx.startup:startup-runtime:${Versions.Android.androidxStartup}")
                // improvement: segment kotlin version is available
                implementation("com.segment.analytics.android:analytics:${Versions.Android.segment}")
            }
        }

        val androidTest by getting

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


//https://slack-chats.kotlinlang.org/t/5117962/hi-all-is-it-possible-to-setup-ksp-to-generate-classes-only-
// generated ksp code to "build/generated/ksp/metadata/commonMain/kotlin" at compile time
tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().all {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

dependencies {
    implementation(project(":annotation"))
    add("kspCommonMainMetadata", project(":processor"))
}
