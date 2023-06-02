package com.common.core.lifecycle

import android.app.Application
import android.content.Context
import android.os.Build
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.delegate.BaseApplicationLifecycle
import com.common.core.other.CrashHandler
import com.common.res.BuildConfig
import com.common.res.layout.RefreshLottieHeader
import com.common.umeng.UmengClient
import com.hjq.toast.Toaster
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import com.tencent.vasdolly.helper.ChannelReaderUtil
import com.umeng.analytics.MobclickAgent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber


class CoreLifecyclesImpl :
    BaseApplicationLifecycle {

    override fun attachBaseContext(base: Context) {

    }

    override fun onCreate(application: Application) {
        //打印日志
        initLogger()
        //初始化ARouter
        initARouter(application)
        //初始化MMKV
        MMKV.initialize(application)
        //初始化Koin
        startKoin {
            androidLogger()
            androidContext(application)
        }
        //初始化SmartRefreshLayout,设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer { _, layout -> //全局设置（优先级最低）
            layout.setEnableAutoLoadMore(true)
            layout.setEnableOverScrollDrag(false)
            layout.setEnableOverScrollBounce(true)
            layout.setEnableLoadMoreWhenContentNotFull(true)
            layout.setEnableScrollContentWhenRefreshed(true)
            layout.setFooterMaxDragRate(2.0f)
            layout.setFooterHeight(20f)
            layout.setRefreshHeader(RefreshLottieHeader(application))
            layout.setRefreshFooter(ClassicsFooter(application).apply {
                setFinishDuration(0)
            })
        }

        //初始化Toast
        Toaster.init(application)

        // Bugly 异常捕捉
        CrashReport.initCrashReport(application, "BUGLY_ID", BuildConfig.DEBUG)

        // 本地异常捕捉
        CrashHandler.register(application)

        //初始化友盟SDK
        UmengClient.init(application,true)
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)

        //渠道
        val channel = ChannelReaderUtil.getChannel(application)

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