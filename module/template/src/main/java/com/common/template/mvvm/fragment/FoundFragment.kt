package com.common.template.mvvm.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.common.core.base.mvvm.BaseVMLazyFragment
import com.common.res.ext.loadImage
import com.common.res.view.SwitchButton
import com.common.template.R
import com.common.template.databinding.TemplateFragmentFoundBinding
import com.common.template.export.TemplateExport
import com.common.template.mvvm.vm.FoundViewModel

import com.gyf.immersionbar.ImmersionBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = TemplateExport.PUBLIC_TEMPLATE_FRAGMENT_FOUND)
open class FoundFragment : BaseVMLazyFragment<TemplateFragmentFoundBinding, FoundViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.template_fragment_found
    }


    override fun initData() {

    }


    override fun initView() {

    }

    override fun onLazyLoad() {
        loadImage(binding.ivFindCircle, R.drawable.res_example_bg, isCircle = true)
        loadImage(
            binding.ivFindCorner,
            R.drawable.res_example_bg,
            imageRadius = 200
        )
        binding.cvFindCountdown.apply {
            setOnClickListener {
                this.start()
            }
        }
        binding.sbFindSwitch.setOnCheckedChangeListener(object :
            SwitchButton.OnCheckedChangeListener {
            override fun onCheckedChanged(button: SwitchButton?, checked: Boolean) {
                toast(checked)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        ImmersionBar.with(requireActivity())
            .statusBarDarkFont(true)
            .statusBarColor(R.color.res_white)
            .navigationBarColor(R.color.res_white)
            .init()
    }


}