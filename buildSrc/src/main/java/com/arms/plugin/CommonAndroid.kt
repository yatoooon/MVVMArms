package com.arms.plugin

import Deps
import Versions
import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.internal.dsl.SigningConfig
import isRunAlone
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

/**
 * desc :android{} 配置
 * author：panyy
 * date：2021/04/22
 */
internal fun Project.configureAndroid(isAppModule: Boolean) {
    val extension =
        if (isAppModule || isRunAlone)
            extensions.getByType<AppExtension>()
        else
            extensions.getByType<LibraryExtension>()


    extension.run {

        buildToolsVersion(Versions.buildTool)
        compileSdkVersion(Versions.compileSdk)

        defaultConfig {
            versionCode = 1
            versionName  = "1.0.0"
            applicationId  = "com.arms.sample"
            resValue("string","app_name","MVVMArms")

            minSdkVersion(Versions.minSdk)
            targetSdkVersion(Versions.targetSdk)
            testInstrumentationRunner = Deps.androidJUnitRunner
            multiDexEnabled = true
            ndk {
                // 设置支持的SO库架构
                abiFilters(
//                    "armeabi",
//                    "x86",
                    "armeabi-v7a"
//                    "x86_64"
//                    "arm64-v8a"
                )
            }

            javaCompileOptions {
                annotationProcessorOptions {
                    argument("AROUTER_MODULE_NAME", project.name)
                }
            }

        }

        dexOptions {
            javaMaxHeapSize =  "4g"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }

        if (isAppModule || isRunAlone) {
            extensions.getByType<BaseAppModuleExtension>().buildFeatures {
                dataBinding = true
            }
        } else {
            extensions.getByType<LibraryExtension>().buildFeatures {
                dataBinding = true
            }
        }

        resourcePrefix(project.name + "_")


        buildTypes {
            @Suppress("MISSING_DEPENDENCY_CLASS")
            val signingConfig = SigningConfig("sample").apply {
                storeFile = File("${project.rootDir}/buildSrc/sample.jks")
                storePassword = "123456"
                keyAlias = "sample"
                keyPassword = "123456"
            }
            getByName("debug") {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
                setSigningConfig(signingConfig)
            }
            getByName("release") {
                isMinifyEnabled = false
                if (isAppModule || isRunAlone) {
                    isShrinkResources = false
                }
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
                setSigningConfig(signingConfig)
            }
        }

        packagingOptions {
            exclude("META-INF/NOTICE.txt")
            // ...
        }
    }
}