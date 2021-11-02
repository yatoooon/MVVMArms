package com.common.template.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.common.core.base.mvvm.BaseVMFragment
import com.common.core.base.mvvm.BaseViewModel
import com.common.res.App
import com.common.res.ext.dp2px
import com.common.res.ext.loadImage
import com.common.res.immersionbar.BindImmersionBar
import com.common.res.router.RouterHub
import com.common.res.view.SwitchButton
import com.common.template.R
import com.common.template.databinding.TemplateFragmentFoundBinding
import com.gyf.immersionbar.ImmersionBar

@Route(path = RouterHub.PUBLIC_TEMPLATE_FRAGMENT_FOUND)
open class FoundFragment : BaseVMFragment<TemplateFragmentFoundBinding, BaseViewModel>() {

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