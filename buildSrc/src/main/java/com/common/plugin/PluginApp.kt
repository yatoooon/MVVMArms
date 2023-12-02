package com.common.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project


class PluginApp : Plugin<Project> {
    override fun apply(project: Project) {
        project.configurePlugins(isAppModule = true)
        project.configureAndroid(isAppModule = true)
        project.configureChannel()
        project.configureDependencies(isAppModule = true)
    }
}