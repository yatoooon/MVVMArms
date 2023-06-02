package com.common.plugin


import com.tencent.vasdolly.plugin.extension.RebuildChannelConfigExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import java.io.File


internal fun Project.configureChannel() {

    val extension = extensions.getByType<RebuildChannelConfigExtension>()

    extension.run {

        channelFile = file(File("${project.rootDir}/buildSrc/app_channel.txt"))
        //指定的apk生成渠道包,文件名中如果有base将被替换为渠道名，否则渠道名将作为前缀
        baseApk = File(project.rootDir, "buildSrc/apk/release/app-release.apk")
        //默认为new File(project.buildDir, "rebuildChannel")
        outputDir = File(project.rootDir, "buildSrc/apk/rebuildChannels")
        //快速模式：生成渠道包时不进行校验（速度可以提升10倍以上，默认为false）
        fastMode = false
        //低内存模式（仅针对V2签名，默认为false）：只把签名块、中央目录和EOCD读取到内存，不把最大头的内容块读取到内存，在手机上合成APK时，可以使用该模式
        lowMemory = false

    }
}