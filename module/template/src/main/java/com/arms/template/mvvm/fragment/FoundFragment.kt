package com.arms.template.mvvm.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.arms.core.base.BaseModel
import com.arms.core.base.mvvm.BaseVMFragment
import com.arms.core.base.mvvm.BaseViewModel
import com.arms.export.arouter.RouterHub
import com.arms.common.ext.loadImage
import com.arms.common.view.SwitchButton
import com.arms.template.R
import com.arms.template.databinding.TemplateFragmentFoundBinding
import com.gyf.immersionbar.ImmersionBar

@Route(path = RouterHub.PUBLIC_TEMPLATE_FRAGMENT_FOUND)
open class FoundFragment : BaseVMFragment<TemplateFragmentFoundBinding, BaseViewModel<BaseModel>>() {

    override fun getLayoutId(): Int {
        return R.layout.template_fragment_found
    }


    override fun initData() {

    }


    override fun initView() {
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