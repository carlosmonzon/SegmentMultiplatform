object Versions {
    const val kotlinVersion = "1.8.10"
    const val androidMinSdk = 21
    const val androidCompileSdk = 32
    const val androidTargetSdk = androidCompileSdk

    object Common {
        const val kotlinSerialization = "1.4.1"
    }

    object Android {
        const val segment = "4.10.4"
        const val androidxStartup = "1.1.1"
    }

    object KSP {
        const val kotlinPoet = "1.12.0"
        const val ksp = "1.8.10-1.0.9"
    }
}

object Deps {
    val kotlinPoet by lazy { "com.squareup:kotlinpoet:${Versions.KSP.kotlinPoet}" }
    val kotlinPoetKsp by lazy { "com.squareup:kotlinpoet-ksp:${Versions.KSP.kotlinPoet}" }
    val ksp by lazy { "com.google.devtools.ksp:symbol-processing-api:${Versions.KSP.ksp}" }

}