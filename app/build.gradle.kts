import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.util.*

plugins {
    id(GradlePluginId.ANDROID_APPLICATION)
    id(GradlePluginId.SAFE_ARGS)
    kotlin(GradlePluginId.KOTLIN_ANDROID)
    kotlin(GradlePluginId.KAPT)
}

android {

    useLibrary("android.test.runner")
    useLibrary("android.test.base")
    useLibrary("android.test.mock")

    testBuildType = BuildTypes.DEBUG

    buildFeatures {
        viewBinding = true
    }

    lintOptions {
        isCheckDependencies = true
        isIgnoreWarnings = true
        xmlReport = true
        xmlOutput = file("${project.rootDir}/build/reports/android-lint.xml")
        htmlReport = true
        htmlOutput = file("${project.rootDir}/build/reports/android-lint.html")
        baselineFile = file("${project.rootDir}/lint/baseline.xml")
        lintConfig = file("${project.rootDir}/lint/lint.xml")
    }

    defaultConfig {
        // FIXME - переименовать на свое
        applicationId = "com.redmadrobot.base"

        minSdkVersion(AndroidConfigVersions.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfigVersions.TARGET_SDK_VERSION)
        compileSdkVersion(AndroidConfigVersions.COMPILE_SDK_VERSION)

        versionCode = extra.get("version_code") as Int
        versionName = extra.get("version_name") as String

        resConfigs("ru")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName(BuildTypes.DEBUG) {
            val debugStoreFile = file("../cert/debug.keystore")
            val debugPropsFile = file("../cert/debug.properties")
            val debugProps = Properties().apply {
                load(debugPropsFile.inputStream())
            }

            storeFile = debugStoreFile
            keyAlias = debugProps.getProperty(SigningProperties.ALIAS_PROP)
            keyPassword = debugProps.getProperty(SigningProperties.KEY_PASSWORD_PROP)
            storePassword = debugProps.getProperty(SigningProperties.STORE_PASSWORD_PROP)
        }

        register(BuildTypes.RELEASE) {
            val releaseStoreFile = file("../cert/release.keystore")
            val releasePropsFile = file("../cert/release.properties")
            val releaseProps = Properties()
            if (file(releasePropsFile).exists()) {
                releaseProps.load(releasePropsFile.inputStream())
            }

            storeFile = releaseStoreFile
            keyAlias = releaseProps.getProperty(SigningProperties.ALIAS_PROP)
            keyPassword = releaseProps.getProperty(SigningProperties.KEY_PASSWORD_PROP)
            storePassword = releaseProps.getProperty(SigningProperties.STORE_PASSWORD_PROP)
        }
    }

    buildTypes {

        val proguardFiles = rootProject.fileTree("proguard").files +
                getDefaultProguardFile("proguard-android-optimize.txt")


        getByName(BuildTypes.DEBUG) {
            applicationIdSuffix = ".debug"

            isDebuggable = true

            isMinifyEnabled = false
            isShrinkResources = false

            proguardFiles(*proguardFiles.toTypedArray())
            signingConfig = signingConfigs.findByName("debug")
        }

        getByName(BuildTypes.RELEASE) {
            applicationIdSuffix = ""

            isDebuggable = false

            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(*proguardFiles.toTypedArray())
            signingConfig = signingConfigs.findByName("release")
        }
    }

    applicationVariants.all {
        val applicationId = mergedFlavor.applicationId
        val applicationIdSuffix = buildType.applicationIdSuffix

        outputs
            .map { it as BaseVariantOutputImpl }
            .forEach { output ->
                output.outputFileName =
                    "$applicationId$applicationIdSuffix-$versionName($versionCode).apk"
            }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    sourceSets.forEach { it.java.srcDir("src/${it.name}/kotlin") }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(AppDependency.CORE_KTX)
    implementation(AppDependency.APPCOMPAT)
    implementation(AppDependency.CONSTRAINT_LAYOUT)
    implementation(AppDependency.LIFECYCLE)
    implementation(AppDependency.MATERIAL_UI)
    implementation(AppDependency.BROWSER)
    implementation(AppDependency.RMR_VIEW_BINDING)

    implementation(AppDependency.NAVIGATION_FRAGMENT)
    implementation(AppDependency.NAVIGATION_FRAGMENT_KTX)
    implementation(AppDependency.NAVIGATION_UI)
    implementation(AppDependency.NAVIGATION_UI_KTX)

    kapt(AppDependency.DAGGER_COMPILER)

    testImplementation(TestDependency.JUNIT)
    testImplementation(TestDependency.MOCKITO)
    testImplementation(TestDependency.ASSERTJ)
    testImplementation(TestDependency.ANDROIDX)
}
