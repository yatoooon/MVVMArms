package com.common.plugin

import Deploys.isRunAlone
import org.gradle.api.Plugin
import org.gradle.api.Project


class PluginLib : Plugin<Project> {
    override fun apply(project: Project) {
        project.configurePlugins(isLibModule = true, isRunAlone = isRunAlone)
        project.configureAndroid(isLibModule = true,isRunAlone = isRunAlone)
        project.configureDependencies(isLibModule = true,isRunAlone = isRunAlone)
    }
}