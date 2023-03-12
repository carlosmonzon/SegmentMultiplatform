plugins {
    kotlin("multiplatform") version Versions.kotlinVersion
}

group = "org.monzon"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                kotlinOptions.jvmTarget = "1.8"
            }
        }
    }

    ios()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
        }
    }
}