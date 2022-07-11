package com.common.template.mvvm.fragment

import android.os.Bundle
import com.common.core.base.mvvm.BaseVMFragment
import dagger.hilt.android.AndroidEntryPoint
import com.common.template.databinding.TemplateFragmentTemplateBinding
import com.common.template.R
import com.common.template.mvvm.vm.TemplateViewModel

@AndroidEntryPoint
class TemplateFragment :
    BaseVMFragment<TemplateFragmentTemplateBinding, TemplateViewModel>() {
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
        viewModel.testfragment.value = "bbbbbbbbbbbbbb"
        viewModel.test.value = "dhwajkdhjkawh"
    }

    override fun initObserve() {
        super.initObserve()
        println("aaaaaaaaaa")
        viewModel.test.observe(this){
            println("ccccccccc")
            println("it:"+it)
        }
        println("bbbbbbbbbbbb")
    }
}