package com.common.simple.lifecycle

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.common.res.immersionbar.BindFullScreen
import com.common.res.immersionbar.BindImmersionBar
import com.common.simple.R
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar

class AppActivityLifecycleCallbacksImpl : ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {
        if (activity is com.common.core.base.BaseActivity<*>) {
            when (activity) {
                is BindImmersionBar -> {
                    ImmersionBar.with(activity)
                            .statusBarDarkFont(true)
                            .navigationBarColor(R.color.res_color_ffffff)
                            .init()
                }
                is BindFullScreen -> {
                    ImmersionBar.with(activity)
                            .fullScreen(true)
                            .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                            .navigationBarColor(R.color.res_color_ffffff)
                            .init()
                }
                else -> {
                    ImmersionBar.with(activity)
                            .fitsSystemWindows(true)
                            .autoDarkModeEnable(true)
                            .statusBarColor(R.color.res_color_ffffff)
                            .navigationBarColor(R.color.res_color_ffffff)
                            .init()
                }
            }
        }
    }
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
    
}