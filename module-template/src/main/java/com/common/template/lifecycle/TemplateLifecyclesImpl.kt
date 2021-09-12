package com.common.template.lifecycle

import android.app.Application
import android.content.Context
import com.common.core.base.delegate.BaseApplicationLifecycle

class TemplateLifecyclesImpl : BaseApplicationLifecycle {

    override fun attachBaseContext(base: Context) {
    }

    override fun onCreate(application: Application) {

    }

    override fun onTerminate(application: Application) {
    }

}