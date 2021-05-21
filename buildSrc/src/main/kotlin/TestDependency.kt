private object TestVersions {
    const val JUNIT = "4.13.2"
    const val MOCKITO = "2.2.0"
    const val ASSERTJ = "3.19.0"
    const val ANDROIDX = "2.1.0"
    const val KOTEST = "4.6.0"
    const val COROUTINES = "1.5.0"
}

object TestDependency {
    const val JUNIT = "junit:junit:${TestVersions.JUNIT}"
    const val MOCKITO = "com.nhaarman.mockitokotlin2:mockito-kotlin:${TestVersions.MOCKITO}"
    const val ASSERTJ = "org.assertj:assertj-core:${TestVersions.ASSERTJ}"
    const val ANDROIDX = "androidx.arch.core:core-testing:${TestVersions.ANDROIDX}"
    const val KOTEST = "io.kotest:kotest-runner-junit5-jvm:${TestVersions.KOTEST}"
    const val KOTEST_ASSERTS = "io.kotest:kotest-assertions-core-jvm:${TestVersions.KOTEST}"
    const val KOTEST_DATASET = "io.kotest:kotest-framework-datatest:${TestVersions.KOTEST}"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${TestVersions.COROUTINES}"
}
