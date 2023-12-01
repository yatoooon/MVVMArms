package com.common.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project


class PluginExport : Plugin<Project> {
    override fun apply(project: Project) {
        project.configurePlugins(false)
        project.configureAndroid(false)
        project.configureDependencies(true)
    }
}