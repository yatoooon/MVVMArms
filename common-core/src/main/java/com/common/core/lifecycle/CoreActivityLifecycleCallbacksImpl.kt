package com.common.core.lifecycle

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.common.core.base.BaseActivity
import com.common.res.R
import com.common.res.immersionbar.BindImmersionBar
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
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
                        .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
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
        //全局设置toolbar 和 title
        val toolbar = activity.findViewById<View>(R.id.res_toolbar)
        if (toolbar != null) {
            val tvTitle = toolbar.findViewById<TextView>(R.id.res_tv_title)
            //找到 Toolbar 的标题栏并设置标题名
            if (tvTitle != null && activity.title != null && activity.title != activity.getString(R.string.app_name)) {
                tvTitle.text = activity.title
            }
            val ivBack = toolbar.findViewById<ImageView>(R.id.res_iv_back)
            ivBack?.setOnClickListener { v: View? -> activity.onBackPressed() }
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