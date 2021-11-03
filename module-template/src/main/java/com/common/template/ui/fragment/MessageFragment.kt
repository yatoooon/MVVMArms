package com.common.template.ui.fragment

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.BaseFragment
import com.common.res.aop.Permissions
import com.common.res.ext.loadImage
import com.common.res.router.RouterHub
import com.common.res.utils.bindViewClickListener
import com.common.template.R
import com.common.template.databinding.TemplateFragmentMessageBinding
import com.gyf.immersionbar.ImmersionBar
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions

@Route(path = RouterHub.PUBLIC_TEMPLATE_FRAGMENT_MESSAGE)
class MessageFragment : BaseFragment<TemplateFragmentMessageBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.template_fragment_message
    }

    override fun initData() {
    }


    override fun initView() {
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
                        loadImage(binding.ivMessageImage, "https://www.baidu.com/img/bd_logo.png")
                    }
                    btnMessageImage2 -> {
                        binding.ivMessageImage.visibility = View.VISIBLE
                        loadImage(
                            binding.ivMessageImage,
                            "https://www.baidu.com/img/bd_logo.png",
                            isCircle = true
                        )
                    }
                    btnMessageImage3 -> {
                        binding.ivMessageImage.visibility = View.VISIBLE
                        loadImage(
                            binding.ivMessageImage,
                            "https://www.baidu.com/img/bd_logo.png",
                            imageRadius = 200
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
                        ARouter.getInstance().build(RouterHub.PUBLIC_HOME_MAINACTIVITY)
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