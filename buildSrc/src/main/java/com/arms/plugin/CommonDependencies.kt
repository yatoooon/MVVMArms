package com.arms.plugin

import Deps
import com.android.build.gradle.api.AndroidBasePlugin
import Deploys.isRunAlone
import Deploys.isRunPlugin
import org.gradle.api.Project

/**
 * desc :公共依赖
 * author：panyy
 * date：2021/04/22
 */
internal fun Project.configureDependencies() = dependencies.apply {
    add("implementation", (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar")))))

    if (isRunPlugin) {
        add("implementation", project(":basis:res"))
        add("compileOnly", "com.tencent.shadow.core:runtime:local-abf3916a-SNAPSHOT")
        add("compileOnly", project(":basis:export"))
        add("compileOnly", Deps.arouterApi)
        add("compileOnly", Deps.hiltAndroid)
        add("compileOnly", Deps.androidHiltLifecycleViewmodel)
    } else {
        add("implementation", project(":basis:export"))
        add("implementation", Deps.arouterApi)
        add("implementation", Deps.hiltAndroid)
        add("implementation", Deps.androidHiltLifecycleViewmodel)
    }
    add("kapt", Deps.arouterCompiler)
    add("kapt", Deps.hiltAndroidCompiler)
    add("kapt", Deps.androidHiltCompiler)
}

internal fun Project.containsAndroidPlugin(): Boolean {
    return project.plugins.toList().any { plugin -> plugin is AndroidBasePlugin }
}