package com.common.core.lifecycle

import android.app.Application
import android.content.Context
import android.os.Build
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.delegate.BaseApplicationLifecycle
import com.common.core.other.CrashHandler
import com.common.res.BuildConfig
import com.hjq.toast.Toaster
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.mmkv.MMKV
import com.tencent.vasdolly.helper.ChannelReaderUtil
import timber.log.Timber


class CoreLifecyclesImpl : BaseApplicationLifecycle {

    override fun attachBaseContext(base: Context) {

    }

    override fun onCreate(application: Application) {
        //打印日志
        initLogger()
        //初始化ARouter
        initARouter(application)
        //初始化MMKV
        MMKV.initialize(application)
        //初始化Toast
        Toaster.init(application)
        // 本地异常捕捉
        CrashHandler.register(application)
        //渠道
        val channel = ChannelReaderUtil.getChannel(application)
    }

    override fun onTerminate(application: Application) {

    }


    /**
     * 初始化打印日志
     */
    private fun initLogger() {
        var formatStrategy =
            PrettyFormatStrategy.newBuilder().tag(BuildConfig.LIBRARY_PACKAGE_NAME).build()

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