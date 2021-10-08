package com.common.core.lifecycle

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.coder.zzq.smartshow.core.SmartShow
import com.common.core.base.delegate.BaseApplicationLifecycle
import com.common.res.BuildConfig
import com.common.res.layout.RefreshLottieHeader
import com.hjq.toast.ToastUtils
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class CoreLifecyclesImpl : BaseApplicationLifecycle {

    override fun attachBaseContext(base: Context) {

    }

    override fun onCreate(application: Application) {
        initLogger()
        initARouter(application)
        //初始化MMKV
        MMKV.initialize(application)
        //初始化ARouter
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        //初始化Koin
        startKoin {
            androidLogger()
            androidContext(application)

        }
        //初始化SmartShow
        SmartShow.init(application)
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
        //初始化Update
        //初始化FileDownloader
        val config = FileDownloadUrlConnection.Configuration().apply {
            connectTimeout(20000)
            readTimeout(20000)
        }
        FileDownloader.setupOnApplicationOnCreate(application)
            .connectionCreator(FileDownloadUrlConnection.Creator(config)).commit()
        ToastUtils.init(application)
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