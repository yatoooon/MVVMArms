package com.common.plugin

import Deploys.isRunAlone
import org.gradle.api.Plugin
import org.gradle.api.Project


class PluginApp : Plugin<Project> {
    override fun apply(project: Project) {
        project.configurePlugins(true)
        project.configureAndroid(true)
        if (!isRunAlone) {
            project.configureChannel()
        }
        project.configureDependencies()
    }
}