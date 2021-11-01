package com.common.template.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.common.core.base.mvvm.BaseVMFragment
import com.common.core.base.mvvm.BaseViewModel
import com.common.res.immersionbar.BindImmersionBar
import com.common.res.router.RouterHub
import com.common.template.R
import com.common.template.databinding.TemplateFragmentFoundBinding

@Route(path = RouterHub.PUBLIC_TEMPLATE_FRAGMENT_FOUND)
open class FoundFragment : BaseVMFragment<TemplateFragmentFoundBinding, BaseViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.template_fragment_found
    }

    override fun initData() {

    }

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }

    override fun getImmersionBarType(): Int {
        return BindImmersionBar.DEFAULTBAR
    }

}