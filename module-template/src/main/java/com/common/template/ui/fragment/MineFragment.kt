package com.common.template.ui.fragment

import android.content.pm.ActivityInfo
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.BaseFragment
import com.common.res.router.RouterHub
import com.common.res.router.data.VideoPlayBuilder
import com.common.res.router.routerNavigation
import com.common.res.utils.bindViewClickListener
import com.common.template.R
import com.common.template.databinding.TemplateFragmentMineBinding
import com.gyf.immersionbar.ImmersionBar
import com.tencent.bugly.crashreport.CrashReport
import java.lang.IllegalStateException
import java.util.ArrayList

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
                btnMineImagePreview,
                btnMineVideoSelect,
                btnMineVideoPlay,
                btnMineCrash
            ) {
                when (this) {

                    btnMineLogin -> {
                        routerNavigation(RouterHub.PUBLIC_LOGINACTIVITY)
                    }
                    btnMineGuide -> {
                        routerNavigation(RouterHub.PUBLIC_SPLASH_GUIDEACTIVITY)
                    }
                    btnMineBrowser -> {
                        ARouter.getInstance().build(RouterHub.PUBLIC_WEBPAGEACTIVITY)
                            .withString("url", "www.baidu.com")
                            .withString("title", "百度一下")
                            .navigation()
                    }
                    btnMineImageSelect -> {
                        ARouter.getInstance().build(RouterHub.PUBLIC_MEDIA_IMAGESELECTACTIVITY)
                            .withInt("maxSelect", 3)
                            .navigation()
                    }
                    btnMineImagePreview -> {
                        val images = ArrayList<String>()
                        images.add("https://www.baidu.com/img/bd_logo.png")
                        images.add("https://avatars1.githubusercontent.com/u/28616817")
                        ARouter.getInstance().build(RouterHub.PUBLIC_MEDIA_IMAGEPREVIEWACTIVITY)
                            .withStringArrayList("imageList", images)
                            .withInt("imageIndex", 0)
                            .navigation()
                    }
                    btnMineVideoSelect -> {
                        ARouter.getInstance().build(RouterHub.PUBLIC_MEDIA_VIDEOSELECTACTIVITY)
                            .withInt("maxSelect", 1)
                            .navigation()
                    }
                    btnMineVideoPlay -> {
                        val builder = VideoPlayBuilder()
                            .setVideoTitle("速度与激情特别行动")
                            .setVideoSource("http://vfx.mtime.cn/Video/2019/06/29/mp4/190629004821240734.mp4")
                            .setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                        ARouter.getInstance()
                            .build(RouterHub.PUBLIC_MEDIA_VIDEOPLAYACTIVITY_LANDSCAPE)
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