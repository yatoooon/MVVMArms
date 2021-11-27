package com.common.template.mvvm.activity

import com.common.core.base.mvvm.BaseVMActivity
import com.common.template.R
import com.common.template.databinding.TemplateActivityBinding
import com.common.template.mvvm.vm.RepositoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TemplateActivity : BaseVMActivity<TemplateActivityBinding, RepositoryViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.template_activity
    }

    override fun initData() {

    }
}