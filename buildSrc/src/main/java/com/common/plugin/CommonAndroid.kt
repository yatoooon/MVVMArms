package com.common.plugin

import Deps
import Versions
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.internal.dsl.SigningConfig
import com.android.builder.internal.ClassFieldImpl
import isRunAlone
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

/**
 * desc :android{} 配置
 * author：panyy
 * date：2021/04/22
 */
internal fun Project.configureAndroid(isAppModule: Boolean) {
    val extension =
        if (isAppModule || isRunAlone)
            extensions.getByType<BaseAppModuleExtension>()
        else
            extensions.getByType<LibraryExtension>()

    extension.run {

        buildToolsVersion(Versions.buildTool)
        compileSdkVersion(Versions.compileSdk)

        defaultConfig {
            minSdkVersion(Versions.minSdk)
            targetSdkVersion(Versions.targetSdk)
            testInstrumentationRunner = Deps.androidJUnitRunner
            multiDexEnabled = true
            flavorDimensions("default")
            ndk {
                // 设置支持的SO库架构
                abiFilters(
//                    "armeabi",
//                    "x86",
                    "armeabi-v7a"
//                    "x86_64",
//                    "arm64-v8a"
                )
            }
            javaCompileOptions {
                annotationProcessorOptions {
                    argument("AROUTER_MODULE_NAME", project.name)
                }
            }


            // 构建配置字段
            addBuildConfigField(
                ClassFieldImpl(
                    "String",
                    "UM_KEY",
                    '\"' + Umeng.UMENG_APP_KEY + '\"'
                )
            )
            addBuildConfigField(ClassFieldImpl("String", "QQ_ID", '\"' + Umeng.QQ_APP_ID + '\"'))
            addBuildConfigField(
                ClassFieldImpl(
                    "String",
                    "QQ_SECRET",
                    '\"' + Umeng.QQ_APP_SECRET + '\"'
                )
            )
            addBuildConfigField(ClassFieldImpl("String", "WX_ID", '\"' + Umeng.WX_APP_ID + '\"'))
            addBuildConfigField(
                ClassFieldImpl(
                    "String",
                    "WX_SECRET",
                    '\"' + Umeng.WX_APP_SECRET + '\"'
                )
            )


            addManifestPlaceholders(
                mapOf<String, String>(
                    "UM_KEY" to Umeng.UMENG_APP_KEY,
                    "QQ_ID" to Umeng.QQ_APP_ID,
                    "QQ_SECRET" to Umeng.QQ_APP_SECRET,
                    "WX_ID" to Umeng.WX_APP_ID,
                    "WX_SECRET" to Umeng.WX_APP_SECRET
                )
            )

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
                this.dataBinding = true
            }
        } else {
            extensions.getByType<LibraryExtension>().buildFeatures {
                this.dataBinding = true
            }
        }

        sourceSets {
            getByName("main") {
                if (isRunAlone) {
                    @Suppress("MISSING_DEPENDENCY_CLASS")
                    var debugManifest =
                        File("${project.projectDir}/src/main/debug/AndroidManifest.xml")
                    if (debugManifest.exists()) {
                        manifest.srcFile("src/main/debug/AndroidManifest.xml")
                    } else {
                        manifest.srcFile("src/main/AndroidManifest.xml")
                    }
                } else {
                    manifest.srcFile("src/main/AndroidManifest.xml")
                }
            }
        }

        resourcePrefix(project.name.replace("module-", "") + "_")


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