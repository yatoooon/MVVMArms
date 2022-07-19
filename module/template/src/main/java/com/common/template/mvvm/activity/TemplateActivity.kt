package com.common.template.mvvm.activity

import com.common.core.base.mvvm.BaseVMActivity
import dagger.hilt.android.AndroidEntryPoint
import com.common.template.databinding.TemplateActivityTemplateBinding
import com.common.template.mvvm.vm.TemplateViewModel
import com.common.template.R

@AndroidEntryPoint
class TemplateActivity : BaseVMActivity<TemplateActivityTemplateBinding, TemplateViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.template_activity_template
    }

    override fun initData() {

    }
}