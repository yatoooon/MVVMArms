package com.common.core.lifecycle

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.text.TextUtils
import com.common.core.base.BaseActivity
import com.common.res.R
import com.common.res.immersionbar.BindImmersionBar
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar
import timber.log.Timber

//添加生命周期的打印信息
class CoreActivityLifecycleCallbacksImpl : ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Timber.i("%s - onActivityCreated", activity)
    }

    override fun onActivityStarted(activity: Activity) {
        Timber.i("%s - onActivityStarted", activity)
        //全局设置状态栏
        if (activity is BaseActivity<*>) {
            when (activity.getImmersionBarType()) {
                BindImmersionBar.IMMERSIONBAR -> {
                    ImmersionBar.with(activity)
                        .statusBarDarkFont(activity.isStatusBarDarkFont())
                        .statusBarColor(R.color.res_white)
                        .navigationBarColor(R.color.res_white)
                        .init()
                }
                BindImmersionBar.FULLSCREEN -> {
                    ImmersionBar.with(activity)
                        .fullScreen(true)
                        .hideBar(BarHide.FLAG_HIDE_BAR)
                        .navigationBarColor(R.color.res_white)
                        .init()
                }
                BindImmersionBar.DEFAULT -> {
                    ImmersionBar.with(activity)
                        .fitsSystemWindows(true)
                        .autoDarkModeEnable(true)
                        .statusBarColor(R.color.res_white)
                        .navigationBarColor(R.color.res_white)
                        .autoDarkModeEnable(true, 0.2f)
                        .init()
                }
            }
        }
        //全局设置titleBar的title
        val titleBar = activity.findViewById<TitleBar>(R.id.res_titleBar)
        if (titleBar != null) {
            //找到 Toolbar 的标题栏并设置标题名
            if (!TextUtils.isEmpty(activity.intent.getStringExtra("title"))) {
                titleBar.title = activity.intent.getStringExtra("title")
            }
        }
    }

    override fun onActivityResumed(activity: Activity) {
        Timber.i("%s - onActivityResumed", activity)
    }

    override fun onActivityPaused(activity: Activity) {
        Timber.i("%s - onActivityPaused", activity)
    }

    override fun onActivityStopped(activity: Activity) {
        Timber.i("%s - onActivityStopped", activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Timber.i("%s - onActivitySaveInstanceState", activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        Timber.i("%s - onActivityDestroyed", activity)
    }
}