package com.common.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project


class PluginLib : Plugin<Project> {
    override fun apply(project: Project) {
        project.configurePlugins(isLibModule = true)
        project.configureAndroid(isLibModule = true)
        project.configureDependencies(isLibModule = true)
    }
}