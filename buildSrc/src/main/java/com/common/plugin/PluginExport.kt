package com.common.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project


class PluginExport : Plugin<Project> {
    override fun apply(project: Project) {
        project.configurePlugins(isExportModule = true)
        project.configureAndroid(isExportModule = true)
        project.configureDependencies(isExportModule = true)
    }
}