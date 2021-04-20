private object TestVersions {
    const val JUNIT = "4.13"
    const val MOCKITO = "2.2.0"
    const val ASSERTJ = "3.12.2"
    const val ANDROIDX = "2.1.0"
}

object TestDependency {
    const val JUNIT = "junit:junit:${TestVersions.JUNIT}"
    const val MOCKITO = "com.nhaarman.mockitokotlin2:mockito-kotlin:${TestVersions.MOCKITO}"
    const val ASSERTJ = "org.assertj:assertj-core:${TestVersions.ASSERTJ}"
    const val ANDROIDX = "androidx.arch.core:core-testing:${TestVersions.ANDROIDX}"
}
