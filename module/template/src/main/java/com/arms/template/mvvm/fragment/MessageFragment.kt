package com.arms.template.mvvm.fragment

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.arms.common.action.TitleBarAction
import com.arms.common.aop.Permissions
import com.arms.core.base.BaseFragment
import com.arms.export.arouter.RouterHub
import com.arms.common.ext.dp2px
import com.arms.common.ext.load
import com.arms.common.utils.bindViewClickListener
import com.arms.template.R
import com.arms.template.databinding.TemplateFragmentMessageBinding
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions

@Route(path = RouterHub.PUBLIC_TEMPLATE_FRAGMENT_MESSAGE)
class MessageFragment : BaseFragment<TemplateFragmentMessageBinding>() ,TitleBarAction{

    override fun getLayoutId(): Int {
        return R.layout.template_fragment_message
    }

    override fun initData() {
    }


    override fun initView() {
        leftIcon = null
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