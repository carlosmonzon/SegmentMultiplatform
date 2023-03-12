pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven ("https://s01.oss.sonatype.org/content/repositories/releases/")
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "com.android") {
                useModule("com.android.tools.build:gradle:7.2.1")
            }
        }
    }
}
rootProject.name = "SegmentMultiplatform"
include("processor")
include("annotation")
