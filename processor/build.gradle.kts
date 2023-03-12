plugins {
    kotlin("multiplatform") version Versions.kotlinVersion
}

group = "org.monzon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":annotation"))
                implementation(Deps.kotlinPoet)
                implementation(Deps.kotlinPoetKsp)
                implementation(Deps.ksp)
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }
    }
}
