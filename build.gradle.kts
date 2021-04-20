plugins {

    // pre-load plugins
    id(GradlePluginId.ANDROID_APPLICATION) version GradlePluginVersions.ANDROID_GRADLE apply false
    id(GradlePluginId.SAFE_ARGS) version GradlePluginVersions.SAFE_ARGS apply false
    kotlin(GradlePluginId.KOTLIN_ANDROID) version GradlePluginVersions.KOTLIN apply false

    // apply plugins
    id(GradlePluginId.DETEKT) version GradlePluginVersions.DETEKT
}

// all projects = root project + sub projects
allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
    apply { from("$rootDir/gradle/version.gradle.kts") }
}

subprojects {
    apply(plugin = GradlePluginId.DETEKT)
    detekt {
        config = rootProject.files("detekt/config.yml")
        baseline = rootProject.file("detekt/baseline.xml")
        autoCorrect = true
        parallel = true
        reports {
            xml {
                enabled = true
                destination = rootProject.file("build/reports/detekt.xml")
            }
        }
    }
    dependencies {
        detektPlugins(DetektDependency.DETEKT_FORMATTING)
    }
}
