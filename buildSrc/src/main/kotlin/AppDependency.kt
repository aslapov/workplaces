private object AppVersions {
    // UI
    const val CORE_KTX = "1.3.0"
    const val APPCOMPAT = "1.2.0-rc01"
    const val BROWSER = "1.0.0"
    const val CONSTRAINT_LAYOUT = "2.0.0-beta4"
    const val LIFECYCLE = "2.2.0"
    const val MATERIAL_UI = "1.2.0-alpha04"
    const val RMR_VIEW_BINDING = "4.1.2-2"

    const val NAVIGATION = "2.3.0"
}

object AppDependency {
    // UI
    const val CORE_KTX = "androidx.core:core-ktx:${AppVersions.CORE_KTX}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${AppVersions.APPCOMPAT}"
    const val BROWSER = "androidx.browser:browser:${AppVersions.BROWSER}"
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${AppVersions.CONSTRAINT_LAYOUT}"
    const val LIFECYCLE = "androidx.lifecycle:lifecycle-extensions:${AppVersions.LIFECYCLE}"
    const val MATERIAL_UI = "com.google.android.material:material:${AppVersions.MATERIAL_UI}"
    const val RMR_VIEW_BINDING =
        "com.redmadrobot.extensions:viewbinding-ktx:${AppVersions.RMR_VIEW_BINDING}"

    // NAVIGATION
    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment:${AppVersions.NAVIGATION}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui:${AppVersions.NAVIGATION}"
    const val NAVIGATION_FRAGMENT_KTX =
        "androidx.navigation:navigation-fragment-ktx:${AppVersions.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${AppVersions.NAVIGATION}"

    //DAGGER
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${CoreVersions.DAGGER}"
}
