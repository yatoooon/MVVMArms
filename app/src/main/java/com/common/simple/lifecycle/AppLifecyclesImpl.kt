package com.common.simple.lifecycle

import android.app.Application
import android.content.Context
import com.common.core.base.delegate.BaseApplicationLifecycle

class AppLifecyclesImpl : BaseApplicationLifecycle {

    override fun attachBaseContext(base: Context) {
    }

    override fun onCreate(application: Application) {
    }

    override fun onTerminate(application: Application) {
    }

}