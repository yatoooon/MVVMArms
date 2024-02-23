package com.common.plugin

import Deps
import com.android.build.gradle.api.AndroidBasePlugin
import org.gradle.api.Project


internal fun Project.configureDependencies(
    isAppModule: Boolean = false,
    isLibModule: Boolean = false,
) = dependencies.apply {
    add("implementation", (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar")))))
    add("testImplementation", Deps.junit)
    when {
        isAppModule -> {
            add("androidTestImplementation", Deps.extJunit)
            add("androidTestImplementation", Deps.espressoCore)
            add("implementation", Deps.codelocator)
            add("implementation", project(":common:core"))
            //一键生成的module放这里
            add("implementation", project(":module:home"))
        }

        isLibModule -> {
            add("implementation", project(":common:core"))
            add("compileOnly", project(":common:export"))
        }
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