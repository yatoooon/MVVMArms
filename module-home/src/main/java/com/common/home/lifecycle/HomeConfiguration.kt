package com.common.home.lifecycle

import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.common.core.base.delegate.BaseApplicationLifecycle
import com.common.core.config.CoreConfigModule
import com.common.core.di.module.AppModule

class HomeConfiguration : CoreConfigModule() {
    override fun applyOptions(context: Context, builder: AppModule.Builder) {
    }

    override fun injectAppLifecycle(
        context: Context,
        lifecycles: MutableList<BaseApplicationLifecycle>
    ) {
        lifecycles.add(HomeLifecyclesImpl())
    }

    override fun injectActivityLifecycle(
        context: Context,
        lifecycles: MutableList<ActivityLifecycleCallbacks>
    ) {
        lifecycles.add(HomeActivityLifecycleCallbacksImpl())
    }

    override fun injectFragmentLifecycle(
        context: Context,
        lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>
    ) {

    }
}