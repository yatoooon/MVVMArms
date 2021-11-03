package com.common.template.ui.fragment

import android.content.pm.ActivityInfo
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.BaseFragment
import com.common.res.data.Builder
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
                        ARouter.getInstance().build(RouterHub.PUBLIC_MEDIA_IMAGESELECTACTIVITY)
                            .withString("maxSelect", "1")
                            .navigation()
                    }
                    btnMineVideoPlay -> {
                        val builder = Builder()
                            .setVideoTitle("速度与激情特别行动")
                            .setVideoSource("http://vfx.mtime.cn/Video/2019/06/29/mp4/190629004821240734.mp4")
                            .setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
//                            .start(requireActivity())
                        ARouter.getInstance().build(RouterHub.PUBLIC_MEDIA_VIDEOPLAYACTIVITY)
                            .withParcelable("parameters", builder)
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