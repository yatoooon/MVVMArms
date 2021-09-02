package com.common.res.config

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.common.core.base.delegate.BaseApplicationLifecycle
import com.common.core.config.CoreConfigModule
import com.common.core.di.module.ConfigModule
import com.common.res.glide.GlideImageLoaderStrategy

/**
 * 自定义全局配置
 *
 *
 */
class ResConfigModule : CoreConfigModule() {

    override fun applyOptions(context: Context, builder: ConfigModule.Builder) {
        builder.baseUrl(Constants.BASE_URL)
                .imageLoaderStrategy(GlideImageLoaderStrategy())
    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<BaseApplicationLifecycle>) {
        lifecycles.add(ResLifecyclesImpl())
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>) {
        lifecycles.add(ResActivityLifecycleCallbacksImpl())
    }

    override fun injectFragmentLifecycle(context: Context, lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>) {

    }
}