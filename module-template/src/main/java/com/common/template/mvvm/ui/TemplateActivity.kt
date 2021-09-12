package com.common.template.mvvm.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.common.core.base.mvvm.BaseVMActivity
import com.common.res.router.RouterHub
import com.common.template.R
import com.common.template.databinding.TemplateActivityBinding
import com.common.template.mvvm.vm.TemplateViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity模板示例
 *
 */
@Route(path = RouterHub.PUBLIC_TEMPLATE)
@AndroidEntryPoint
class TemplateActivity : BaseVMActivity<TemplateActivityBinding, TemplateViewModel>() {


    override fun getLayoutId(): Int {
        return R.layout.template_activity
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}