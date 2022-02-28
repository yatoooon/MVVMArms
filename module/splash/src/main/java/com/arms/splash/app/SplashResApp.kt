package com.arms.splash.app

import com.arms.core.base.BaseApplication
import com.arms.core.lifecycle.CoreLifecyclesImpl
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class SplashApp : BaseApplication(){
    override fun onCreate() {
        CoreLifecyclesImpl.loadPlugin = true
        super.onCreate()
    }
}