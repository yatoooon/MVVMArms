package com.common.res

import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.BaseApplication
import com.common.core.base.ibase.IComponentApp
import com.common.res.config.Constants
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import timber.log.Timber

class ResCompoentApp : IComponentApp {

    override fun onCreate(baseApplication: BaseApplication) {
        initLogger()
        initARouter(baseApplication)
    }

    /**
     * 初始化打印日志
     */
    private fun initLogger() {
        var formatStrategy = PrettyFormatStrategy.newBuilder()
                .methodOffset(5)
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
    private fun initARouter(baseApplication: BaseApplication) {
        Timber.d("isDebug:${BuildConfig.DEBUG}")
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }

        ARouter.init(baseApplication)
    }

}