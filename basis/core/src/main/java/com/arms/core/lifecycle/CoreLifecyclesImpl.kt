package com.arms.core.lifecycle

import android.app.Application
import android.app.Application.getProcessName
import android.content.Context
import android.os.Build
import android.webkit.WebView
import com.alibaba.android.arouter.launcher.ARouter
import com.coder.zzq.smartshow.core.SmartShow
import com.arms.core.base.delegate.BaseApplicationLifecycle
import com.arms.core.other.CrashHandler
import com.arms.common.BuildConfig
import com.arms.umeng.UmengClient
import com.hjq.toast.ToastUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import com.tencent.shadow.sample.introduce_shadow_lib.InitApplication
import com.umeng.analytics.MobclickAgent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class CoreLifecyclesImpl : BaseApplicationLifecycle {

    override fun attachBaseContext(base: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WebView.setDataDirectorySuffix(getProcessName())
        }
    }

    override fun onCreate(application: Application) {
        initLogger()
        initARouter(application)
        //初始化MMKV
        MMKV.initialize(application)
        //初始化Koin
        startKoin {
            androidLogger()
            androidContext(application)
        }
        //初始化SmartShow
        SmartShow.init(application)



        ToastUtils.init(application)


        // Bugly 异常捕捉
        CrashReport.initCrashReport(application, "BUGLY_ID", BuildConfig.DEBUG)

        // 本地异常捕捉
        CrashHandler.register(application)

        //初始化友盟SDK
        UmengClient.init(application,true)
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)

        InitApplication.onApplicationCreate(application)

    }

    override fun onTerminate(application: Application) {

    }


    /**
     * 初始化打印日志
     */
    private fun initLogger() {
        var formatStrategy = PrettyFormatStrategy.newBuilder()
            .tag(BuildConfig.LIBRARY_PACKAGE_NAME)
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (BuildConfig.DEBUG) {
                    Logger.log(priority, tag, message, t)
                }
            }

            override fun createStackElementTag(element: StackTraceElement): String {
                val tag = "(" + element.fileName + ":" + element.lineNumber + ")"
                // 日志 TAG 长度限制已经在 Android 8.0 被移除
                return if (tag.length <= 23 || Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    tag
                } else tag.substring(0, 23)
            }
        })
    }


    /**
     * 初始化ARouter
     */
    private fun initARouter(application: Application) {
        Timber.d("isDebug:${BuildConfig.DEBUG}")
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }

        ARouter.init(application)
    }
}