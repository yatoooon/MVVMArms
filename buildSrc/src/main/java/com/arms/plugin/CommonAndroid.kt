package com.arms.plugin

import Deps
import Versions
import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.internal.dsl.SigningConfig
import com.android.builder.core.DefaultDexOptions
import com.android.builder.core.DexOptions
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
        if (isAppModule || Deploys.isRunAlone)
            extensions.getByType<AppExtension>()
        else
            extensions.getByType<LibraryExtension>()


    extension.run {

        compileSdkVersion(Versions.compileSdk)

        defaultConfig {
            versionCode = 1
            versionName = "1.0.0"
            if (isAppModule || Deploys.isRunAlone || Deploys.isRunPlugin) {
                applicationId = "com.arms.sample"
                resValue("string", "app_name", "MVVMArms")
                if (Deploys.isRunPlugin) {
                    aaptOptions.additionalParameters(
                        "--package-id",
                        "0x7E",
                        "--allow-reserved-package-id"
                    )
                }
            }
            minSdk = Versions.minSdk
            targetSdk = Versions.targetSdk
            multiDexEnabled = true
            ndk {
                // 设置支持的SO库架构
                abiFilters.addAll(
                    arrayListOf("armeabi-v7a", "arm64-v8a")
                )
            }

            javaCompileOptions {
                annotationProcessorOptions {
                    argument("AROUTER_MODULE_NAME", project.name)
                }
            }


        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }

        if (isAppModule || Deploys.isRunAlone) {
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
//            @Suppress("MISSING_DEPENDENCY_CLASS")
//            val signingConfig = SigningConfig("sample").apply {
//                storeFile = File("${project.rootDir}/buildSrc/sample.jks")
//                storePassword = "123456"
//                keyAlias = "sample"
//                keyPassword = "123456"
//            }
            getByName("debug") {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
//                setSigningConfig(signingConfig)
            }
            getByName("release") {
                isMinifyEnabled = false
                if (isAppModule || Deploys.isRunAlone) {
                    isShrinkResources = false
                }
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
//                setSigningConfig(signingConfig)
            }
        }

        packagingOptions {
            excludes.add("META-INF/NOTICE.txt")
        }

        lintOptions {
            isCheckReleaseBuilds = false
            isAbortOnError = false
        }

    }
}