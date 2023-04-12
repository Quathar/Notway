plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.HILT_GRADLE)
    id(Plugins.KOTLIN_KAPT)
}

android {
    namespace  = "com.iothar.android"
    compileSdk = Apps.COMPILE_SDK

    defaultConfig {
        applicationId             = Apps.APPLICATION_ID
        minSdk                    = Apps.MIN_SDK
        targetSdk                 = Apps.TARGET_SDK
        versionCode               = Apps.VERSION_CODE
        versionName               = Apps.VERSION_NAME
        testInstrumentationRunner = Apps.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = Apps.JAVA_COMPATIBILITY_VERSION
        targetCompatibility = Apps.JAVA_COMPATIBILITY_VERSION
    }

    kotlinOptions {
        jvmTarget = Apps.JVM_TARGET
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":data"))
    implementation(Libs.ANDROIDX_CORE_KTX)
    implementation(Libs.ANDROIDX_APPCOMPAT)
    implementation(Libs.MATERIAL_ANDROID)
    implementation(Libs.ANDROIDX_CONSTRAINTLAYOUT)
    implementation(Libs.ANDROIDX_ACTIVITY)
    implementation(Libs.ANDROIDX_VIEWMODEL)
    implementation(Libs.ANDROIDX_LIFECYCLE_RUNTIME_KTX)
    implementation(Libs.ANDROIDX_ROOM_KTX)
    implementation(Libs.HILT_ANDROID)
    kapt(Libs.HILT_ANDROID_COMPILER)
    testImplementation(Libs.JUNIT)
    androidTestImplementation(Libs.ANDROIDX_TEST_EXT_JUNIT)
    androidTestImplementation(Libs.ANDROIDX_TEST_ESPRESSO)
}