package com.arms.template.mvvm.fragment

import android.os.Bundle
import com.arms.core.base.mvvm.BaseVMFragment
import dagger.hilt.android.AndroidEntryPoint
import com.arms.template.databinding.TemplateFragmentTemplateBinding
import com.arms.template.mvvm.vm.TemplateViewModel
import com.arms.template.R

@AndroidEntryPoint
class TemplateFragment : BaseVMFragment<TemplateFragmentTemplateBinding, TemplateViewModel>() {
    companion object {
        fun newInstance(): TemplateFragment {
            val args = Bundle()
            val fragment = TemplateFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.template_fragment_template
    }

    override fun initData() {

    }
}