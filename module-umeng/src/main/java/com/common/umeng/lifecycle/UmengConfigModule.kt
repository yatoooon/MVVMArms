package com.common.umeng.lifecycle

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.common.core.base.delegate.BaseApplicationLifecycle
import com.common.core.config.CoreConfigModule
import com.common.core.di.module.AppModule

/**
 * 自定义全局配置
 *
 *
 */
class UmengConfigModule : CoreConfigModule() {

    override fun applyOptions(context: Context, builder: AppModule.Builder) {

    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<BaseApplicationLifecycle>) {
        lifecycles.add(UmengLifecyclesImpl())
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>) {
    }

    override fun injectFragmentLifecycle(context: Context, lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>) {

    }
}