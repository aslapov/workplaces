private object TestVersions {
    const val KOTEST = "4.6.0"
    const val COROUTINES = "1.5.0"
    const val MOCKK = "1.10.6"
}

object TestDependency {
    const val KOTEST_RUNNER = "io.kotest:kotest-runner-junit5-jvm:${TestVersions.KOTEST}"
    const val KOTEST_ASSERTIONS = "io.kotest:kotest-assertions-core-jvm:${TestVersions.KOTEST}"
    const val KOTEST_DATASET = "io.kotest:kotest-framework-datatest:${TestVersions.KOTEST}"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${TestVersions.COROUTINES}"
    const val MOCKK = "io.mockk:mockk:${TestVersions.MOCKK}"
}
