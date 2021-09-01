package com.common.res.config

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.delegate.BaseApplicationLifecycle
import com.common.res.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import timber.log.Timber

class ResLifecyclesImpl : BaseApplicationLifecycle {

    override fun attachBaseContext(base: Context) {

    }

    override fun onCreate(application: Application) {
        initLogger()
        initARouter(application)
    }

    override fun onTerminate(application: Application) {

    }


    /**
     * 初始化打印日志
     */
    private fun initLogger() {
        var formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(Constants.TAG)
                .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (BuildConfig.DEBUG) {
                    Logger.log(priority, tag, message, t)
                }
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