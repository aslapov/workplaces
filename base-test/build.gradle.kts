plugins {
    kotlin(GradlePluginId.KOTLIN_JVM)
}

dependencies {
    implementation(DomainDependency.KOTLIN)

    api(TestDependency.KOTEST_RUNNER)
    api(TestDependency.KOTEST_ASSERTIONS)
    api(TestDependency.KOTEST_DATASET)
    api(TestDependency.COROUTINES)
    api(TestDependency.MOCKK)
}
