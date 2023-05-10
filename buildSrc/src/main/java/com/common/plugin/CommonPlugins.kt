package com.common.plugin

import Deploys.isRunAlone
import org.gradle.api.Project


internal fun Project.configurePlugins(isAppModule: Boolean) {
    if (isAppModule || isRunAlone) {
        plugins.apply("com.android.application")
    } else {
        plugins.apply("com.android.library")
    }
    plugins.apply("kotlin-android")
    plugins.apply("kotlin-kapt")
    plugins.apply("kotlin-parcelize")
    plugins.apply("dagger.hilt.android.plugin")
    plugins.apply("io.github.wurensen.android-aspectjx")
    var options = mutableMapOf<String, String>().apply {
        @Suppress("MISSING_DEPENDENCY_CLASS") put("from", "${project.rootDir}/build_app.gradle")
    }
    apply(options)


}