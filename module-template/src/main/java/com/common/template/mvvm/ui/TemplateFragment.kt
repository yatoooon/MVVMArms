package com.common.template.mvvm.ui

import android.os.Bundle
import com.common.core.base.mvvm.BaseVMFragment
import com.common.template.R
import com.common.template.databinding.TemplateFragmentBinding
import com.common.template.mvvm.vm.TemplateViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment模板示例

 */
@AndroidEntryPoint
class TemplateFragment : BaseVMFragment<TemplateFragmentBinding, TemplateViewModel>() {


    override fun getLayoutId(): Int {
        return R.layout.template_fragment
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}