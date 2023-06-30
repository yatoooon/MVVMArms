package com.common.plugin


import com.tencent.vasdolly.plugin.extension.RebuildChannelConfigExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.regex.Pattern


internal fun Project.configureChannel() {

    val extension = extensions.getByType<RebuildChannelConfigExtension>()

    extension.run {

        val apkPattern = Pattern.compile(".*\\.apk")
        val rootDir = project.rootDir
        val releaseDir = rootDir.resolve("buildSrc/apk/release")

        channelFile = file(File("${project.rootDir}/buildSrc/app_channel.txt"))
        baseApk = releaseDir.listFiles()?.find { file ->
            Files.isRegularFile(Paths.get(file.toURI())) && apkPattern.matcher(file.name).matches()
        }
        outputDir = File(project.rootDir, "buildSrc/apk/rebuildChannels")
        fastMode = false
        lowMemory = false

    }
}