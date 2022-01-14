package com.common.template.mvvm.activity

import android.os.Bundle
import android.view.View
import com.common.core.base.mvvm.BaseVMActivity
import dagger.hilt.android.AndroidEntryPoint
import com.common.template.databinding.TemplateActivityTemplateBinding
import com.common.template.mvvm.vm.TemplateViewModel
import com.common.template.R
import com.tencent.shadow.dynamic.host.EnterCallback
import com.tencent.shadow.sample.introduce_shadow_lib.InitApplication

@AndroidEntryPoint
class TemplateActivity : BaseVMActivity<TemplateActivityTemplateBinding, TemplateViewModel>() {
    val FROM_ID_START_ACTIVITY = 1001
    val FROM_ID_CALL_SERVICE = 1002
    override fun getLayoutId(): Int {
        return R.layout.template_activity_template
    }

    override fun initData() {

        val pluginManager = InitApplication.getPluginManager()
        //调用activity
        pluginManager.enter(
            activity,
            FROM_ID_START_ACTIVITY.toLong(),
            Bundle(),
            object : EnterCallback {
                override fun onShowLoadingView(view: View) {
                }

                override fun onCloseLoadingView() {
                }

                override fun onEnterComplete() {
                }
            })
        //调用service
        pluginManager.enter(
            activity,
            FROM_ID_CALL_SERVICE.toLong(),
            null,
            null
        )
    }
}