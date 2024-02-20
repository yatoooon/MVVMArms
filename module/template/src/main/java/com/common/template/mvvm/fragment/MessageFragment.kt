package com.common.template.mvvm.fragment

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.mvvm.BaseVMLazyFragment
import com.common.home.export.HomeExport
import com.common.res.aop.Permissions
import com.common.res.ext.dp2px
import com.common.res.ext.load
import com.common.res.utils.bindViewClickListener
import com.common.template.R
import com.common.template.databinding.TemplateFragmentMessageBinding
import com.common.template.export.TemplateExport
import com.common.template.mvvm.vm.MessageViewModel

import com.gyf.immersionbar.ImmersionBar
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = TemplateExport.PUBLIC_TEMPLATE_FRAGMENT_MESSAGE)
class MessageFragment : BaseVMLazyFragment<TemplateFragmentMessageBinding, MessageViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.template_fragment_message
    }

    override fun initData() {
    }


    override fun initView() {

    }

    override fun onLazyLoad() {
        binding.apply {
            bindViewClickListener(
                btnMessageImage1,
                btnMessageImage2,
                btnMessageImage3,
                btnMessageToast,
                btnMessagePermission,
                btnMessageSetting,
                btnMessageBlack,
                btnMessageWhite,
                btnMessageTab
            ) {
                when (this) {
                    btnMessageImage1 -> {
                        binding.ivMessageImage.visibility = View.VISIBLE
                        binding.ivMessageImage.load("https://www.baidu.com/img/bd_logo.png")
                    }

                    btnMessageImage2 -> {
                        binding.ivMessageImage.visibility = View.VISIBLE
                        binding.ivMessageImage.load(
                            "https://www.baidu.com/img/bd_logo.png",
                            isCircle = true
                        )
                    }

                    btnMessageImage3 -> {
                        binding.ivMessageImage.visibility = View.VISIBLE
                        binding.ivMessageImage.load(
                            "https://www.baidu.com/img/bd_logo.png",
                            imageRadius = context.dp2px(20)
                        )
                    }

                    btnMessageToast -> {
                        toast("我是吐司")
                    }

                    btnMessagePermission -> {
                        requestPermission()
                    }

                    btnMessageSetting -> {
                        XXPermissions.startPermissionActivity(this@MessageFragment)
                    }

                    btnMessageBlack -> {
                        ImmersionBar.with(requireActivity()).statusBarDarkFont(true).init()
                    }

                    btnMessageWhite -> {
                        ImmersionBar.with(requireActivity()).statusBarDarkFont(false).init()

                    }

                    btnMessageTab -> {
                        ARouter.getInstance().build(HomeExport.PUBLIC_HOME_MAINACTIVITY)
                            .withString("index", "0")
                            .navigation()
                    }
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        ImmersionBar.with(requireActivity())
            .statusBarDarkFont(true)
            .statusBarColor(R.color.res_white)
            .navigationBarColor(R.color.res_white)
            .init()
    }

    @Permissions(Permission.CAMERA)
    private fun requestPermission() {
        toast("获取摄像头权限成功")
    }
}