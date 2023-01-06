package com.common.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project


class PluginApp : Plugin<Project> {
    override fun apply(project: Project) {
        project.configurePlugins(true)
        project.configureAndroid(true)
        project.configureDependencies()
    }
}