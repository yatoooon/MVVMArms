package com.common.plugin

import Deploys.isRunAlone
import Deps
import Versions
import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File


internal fun Project.configureAndroid(isAppModule: Boolean) {
    val extension = if (isAppModule || isRunAlone) extensions.getByType<AppExtension>()
    else extensions.getByType<LibraryExtension>()

    extension.run {

        buildToolsVersion(Versions.buildTool)
        compileSdkVersion(Versions.compileSdk)

        defaultConfig {
            versionCode = 1
            versionName = "1.0.0"
            if (isAppModule) {
                applicationId = "com.arms.sample"
                resValue("string", "app_name", "MVVMArms")
            } else if (isRunAlone) {
                applicationId = "com.arms.sample." + project.name
                resValue("string", "app_name", project.name)
            } else {
                consumerProguardFile(File("${project.rootDir}/buildSrc/consumer-rules.pro"))
            }
            minSdk = Versions.minSdk
            targetSdk = Versions.targetSdk
            testInstrumentationRunner = Deps.androidJUnitRunner
            multiDexEnabled = true
            flavorDimensions("default")
            ndk {
                // 设置支持的SO库架构
                abiFilters.addAll(
                    mutableSetOf(
//                        "armeabi",
//                        "x86",
                        "armeabi-v7a",
//                        "x86_64",
//                        "arm64-v8a"
                    )
                )
            }

            javaCompileOptions {
                annotationProcessorOptions {
                    argument("AROUTER_MODULE_NAME", project.name)
                }
            }
        }


        namespace = "com.common." + project.name


        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }

        dataBinding {
            enable = true
        }

        sourceSets {
            getByName("main") {
                manifest.srcFile("src/main/AndroidManifest.xml")
            }
        }
        resourcePrefix(project.name.replace("module-", "") + "_")

        signingConfigs {
            getByName("debug") {
                storeFile = File("${project.rootDir}/buildSrc/sample.jks")
                storePassword = "123456"
                keyAlias = "sample"
                keyPassword = "123456"
            }
            create("release") {
                storeFile = File("${project.rootDir}/buildSrc/sample.jks")
                storePassword = "123456"
                keyAlias = "sample"
                keyPassword = "123456"
                enableV1Signing = true
                enableV2Signing = true
            }
        }

        buildTypes {
            getByName("debug") {
                isMinifyEnabled = false
                setSigningConfig(signingConfigs.getByName("debug"))
            }
            getByName("release") {
                isMinifyEnabled = true
                if (isAppModule || isRunAlone) {
                    isShrinkResources = true
                }
                setSigningConfig(signingConfigs.getByName("release"))
            }
        }

    }
}