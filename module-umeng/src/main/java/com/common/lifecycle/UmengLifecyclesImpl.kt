package com.common.lifecycle

import android.app.Application
import android.content.Context
import com.common.core.base.delegate.BaseApplicationLifecycle
import com.common.umeng.UmengClient
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure


class UmengLifecyclesImpl : BaseApplicationLifecycle {

    override fun attachBaseContext(base: Context) {

    }

    override fun onCreate(application: Application) {
        //初始化友盟SDK
        UMConfigure.setLogEnabled(true)
        UmengClient.init(application)
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)

    }

    override fun onTerminate(application: Application) {

    }

}