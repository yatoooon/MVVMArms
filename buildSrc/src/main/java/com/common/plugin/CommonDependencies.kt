package com.common.plugin

import Deps
import com.android.build.gradle.api.AndroidBasePlugin
import org.gradle.api.Project


internal fun Project.configureDependencies(isExport: Boolean) = dependencies.apply {
    add("implementation", (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar")))))
    add("testImplementation", Deps.junit)
    if (project.containsAndroidPlugin()) {
        add("androidTestImplementation", Deps.extJunit)
        add("androidTestImplementation", Deps.espressoCore)
        add("implementation", "com.bytedance.tools.codelocator:codelocator-core:2.0.3")
    }
    if (!isExport){
        add("implementation", project(":common:core"))
        add("implementation", project(":common:export"))
    }
    add("implementation", Deps.arouterApi)
    add("kapt", Deps.arouterCompiler)
    add("implementation", Deps.hiltAndroid)
    add("kapt", Deps.hiltAndroidCompiler)
    add("implementation", Deps.androidHiltLifecycleViewmodel)
    add("kapt", Deps.androidHiltCompiler)
    add("kapt", Deps.kotlinxMetadataJvm)
    add("implementation", Deps.room)
    add("kapt", Deps.roomCompiler)
}

internal fun Project.containsAndroidPlugin(): Boolean {
    return project.plugins.toList().any { plugin -> plugin is AndroidBasePlugin }
}