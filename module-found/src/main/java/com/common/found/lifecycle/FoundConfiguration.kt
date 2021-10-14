package com.common.found.lifecycle

import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.common.core.base.delegate.BaseApplicationLifecycle
import com.common.core.config.CoreConfigModule
import com.common.core.di.module.AppModule

class FoundConfiguration : CoreConfigModule() {
    override fun applyOptions(context: Context, builder: AppModule.Builder) {
    }

    override fun injectAppLifecycle(
        context: Context,
        lifecycles: MutableList<BaseApplicationLifecycle>
    ) {
        lifecycles.add(FoundLifecyclesImpl())
    }

    override fun injectActivityLifecycle(
        context: Context,
        lifecycles: MutableList<ActivityLifecycleCallbacks>
    ) {
        lifecycles.add(FoundActivityLifecycleCallbacksImpl())
    }

    override fun injectFragmentLifecycle(
        context: Context,
        lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>
    ) {

    }
}