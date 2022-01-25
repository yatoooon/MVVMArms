package com.arms.template.mvvm.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.arms.common.ext.dp2px
import com.arms.core.base.BaseModel
import com.arms.core.base.mvvm.BaseVMFragment
import com.arms.core.base.mvvm.BaseViewModel
import com.arms.export.arouter.RouterHub
import com.arms.common.ext.loadImage
import com.arms.common.view.SwitchButton
import com.arms.template.R
import com.arms.template.databinding.TemplateFragmentFoundBinding
import com.gyf.immersionbar.ImmersionBar
import com.hjq.shape.drawable.ShapeType

@Route(path = RouterHub.PUBLIC_TEMPLATE_FRAGMENT_FOUND)
open class FoundFragment : BaseVMFragment<TemplateFragmentFoundBinding, BaseViewModel<BaseModel>>() {

    override fun getLayoutId(): Int {
        return R.layout.template_fragment_found
    }


    override fun initData() {

    }


    override fun initView() {
        binding.stv.shapeDrawableBuilder.shape = ShapeType.RECTANGLE
        binding.stv.shapeDrawableBuilder.centerX = 0.5f
        binding.stv.shapeDrawableBuilder.centerY = 0.5f
        binding.stv.shapeDrawableBuilder.gradientColor = intArrayOf(R.color.res_common_button_pressed_color,R.color.res_transparent,R.color.res_accent_color)
        binding.stv.shapeDrawableBuilder.setRadius(requireActivity().dp2px(20).toFloat())
        binding.stvBg.shapeDrawableBuilder.shape = ShapeType.RECTANGLE
        binding.stv.shapeDrawableBuilder.setRadius(requireActivity().dp2px(10).toFloat())
        binding.stv.shapeDrawableBuilder.shadowSize = requireActivity().dp2px(10)
        binding.stv.shapeDrawableBuilder.solidColor = R.color.res_white

        leftIcon = null
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