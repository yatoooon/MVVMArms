package com.common.web.lifecycle

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.basis.core.base.delegate.BaseApplicationLifecycle
import com.basis.core.config.CoreConfigModule
import com.basis.core.di.module.AppModule

class WebConfiguration : CoreConfigModule() {
    override fun applyOptions(context: Context, builder: AppModule.Builder) {
    }


    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<BaseApplicationLifecycle>) {
        // AppLifecycles 的所有方法都会在基类 Application 的对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        // 可以根据不同的逻辑添加多个实现类
        lifecycles.add(WebLifecyclesImpl())
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>) {
    }

    override fun injectFragmentLifecycle(context: Context, lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>) {

    }

}
