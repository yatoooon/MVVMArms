package com.arms.template.lifecycle

import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.arms.core.base.delegate.BaseApplicationLifecycle
import com.arms.core.config.CoreConfigModule
import com.arms.core.di.module.AppModule

class TemplateConfiguration : CoreConfigModule() {
    override fun applyOptions(context: Context, builder: AppModule.Builder) {
    }

    override fun injectAppLifecycle(
        context: Context,
        lifecycles: MutableList<BaseApplicationLifecycle>
    ) {
        lifecycles.add(TemplateLifecyclesImpl())
    }

    override fun injectActivityLifecycle(
        context: Context,
        lifecycles: MutableList<ActivityLifecycleCallbacks>
    ) {
        lifecycles.add(TemplateActivityLifecycleCallbacksImpl())
    }

    override fun injectFragmentLifecycle(
        context: Context,
        lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>
    ) {

    }
}