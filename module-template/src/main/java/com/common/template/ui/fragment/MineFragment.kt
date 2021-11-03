package com.common.template.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.BaseFragment
import com.common.res.router.RouterHub
import com.common.res.utils.bindViewClickListener
import com.common.template.R
import com.common.template.databinding.TemplateFragmentMineBinding
import com.gyf.immersionbar.ImmersionBar
import com.tencent.bugly.crashreport.CrashReport
import java.lang.IllegalStateException

@Route(path = RouterHub.PUBLIC_TEMPLATE_FRAGMENT_MINE)
class MineFragment : BaseFragment<TemplateFragmentMineBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.template_fragment_mine
    }

    override fun initData() {
    }


    override fun initView() {
        binding.apply {
            bindViewClickListener(
                btnMineDialog,
                btnMineHint,
                btnMineLogin,
                btnMineRegister,
                btnMineForget,
                btnMineReset,
                btnMineChange,
                btnMinePersonal,
                btnMineSetting,
                btnMineAbout,
                btnMineGuide,
                btnMineBrowser,
                btnMineImageSelect,
                btnMineVideoSelect,
                btnMineVideoPlay,
                btnMineCrash
            ) {
                when (this) {
                    btnMineImageSelect -> {
                        ARouter.getInstance().build(RouterHub.PUBLIC_MEDIA_IMAGE_SELECT)
                            .withString("maxSelect", "1")
                            .navigation()
                    }
                    btnMineCrash -> {
                        // 上报错误到 Bugly 上
                        CrashReport.postCatchedException(IllegalStateException("are you ok?"))
                        // 关闭 Bugly 异常捕捉
                        CrashReport.closeBugly()

                        throw IllegalStateException("are you ok?")
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

}