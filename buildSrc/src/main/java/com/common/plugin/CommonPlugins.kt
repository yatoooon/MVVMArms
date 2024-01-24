package com.common.plugin

import org.gradle.api.Project


internal fun Project.configurePlugins(
    isAppModule: Boolean = false,
    isLibModule: Boolean = false,
) {
    when {
        isAppModule -> {
            plugins.apply("com.android.application")
            plugins.apply("com.tencent.vasdolly")
        }

        isLibModule -> {
            plugins.apply("com.android.library")
            plugins.apply("communication.export")
        }
    }
    plugins.apply("kotlin-android")
    plugins.apply("kotlin-kapt")
    plugins.apply("kotlin-parcelize")
    plugins.apply("dagger.hilt.android.plugin")
    plugins.apply("io.github.wurensen.android-aspectjx")
    val options = mutableMapOf<String, String>().apply {
        @Suppress("MISSING_DEPENDENCY_CLASS") put("from", "${project.rootDir}/build_android.gradle")
    }
    apply(options)


}