package com.common.core.lifecycle

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.common.res.R
import timber.log.Timber

//添加生命周期的打印信息
class CoreActivityLifecycleCallbacksImpl : ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Timber.i("%s - onActivityCreated", activity)
    }

    override fun onActivityStarted(activity: Activity) {
        Timber.i("%s - onActivityStarted", activity)
        //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
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