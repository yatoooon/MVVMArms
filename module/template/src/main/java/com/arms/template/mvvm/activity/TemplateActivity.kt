package com.arms.template.mvvm.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.arms.core.base.mvvm.BaseVMActivity
import com.arms.export.arouter.RouterHub
import dagger.hilt.android.AndroidEntryPoint
import com.arms.template.databinding.TemplateActivityTemplateBinding
import com.arms.template.mvvm.vm.TemplateViewModel
import com.arms.template.R

@Route(path = RouterHub.PUBLIC_TEMPLATE_ACTIVITY)
@AndroidEntryPoint
class TemplateActivity : BaseVMActivity<TemplateActivityTemplateBinding, TemplateViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.template_activity_template
    }

    override fun initData() {
    }
}