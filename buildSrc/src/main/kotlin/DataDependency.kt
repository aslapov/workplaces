private object DataVersions {
    const val CORE = "1.3.0"
    const val OKHTTP_LOGGING = "3.1.0"
    const val CRYPTO = "1.1.0-alpha03"
}

object DataDependency {
    const val CORE = "androidx.core:core-ktx:${DataVersions.CORE}"
    const val OKHTTP_LOGGING = "com.github.ihsanbal:LoggingInterceptor:${DataVersions.OKHTTP_LOGGING}"
    const val SECURITY_CRYPTO = "androidx.security:security-crypto:${DataVersions.CRYPTO}"

    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${CoreVersions.DAGGER}"
    const val MOSHI_COMPILER = "com.squareup.moshi:moshi-kotlin-codegen:${CoreVersions.MOSHI}"
}
