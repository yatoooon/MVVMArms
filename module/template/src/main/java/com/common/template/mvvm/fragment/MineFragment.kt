package com.common.template.mvvm.fragment

import android.content.pm.ActivityInfo
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.BaseFragment
import com.common.login.export.arouter.LoginRouterHub
import com.common.media.export.arouter.MediaRouterHub
import com.common.media.export.data.VideoPlayBuilder
import com.common.media.export.arouter.IMediaService
import com.common.personal.export.arouter.PersonalRouterHub
import com.common.res.utils.bindViewClickListener
import com.common.res.utils.routerNavigation
import com.common.splash.export.arouter.SplashRouterHub
import com.common.template.R
import com.common.template.databinding.TemplateFragmentMineBinding
import com.common.template.export.arouter.TemplateRouterHub
import com.common.template.mvvm.activity.DialogActivity
import com.common.template.mvvm.activity.StatusActivity
import com.common.test.export.arouter.TestRouterHub
import com.common.web.service.export.arouter.WebRouterHub
import com.gyf.immersionbar.ImmersionBar
import com.tencent.bugly.crashreport.CrashReport

@Route(path = TemplateRouterHub.PUBLIC_TEMPLATE_FRAGMENT_MINE)
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
                btnMineCrash,
                btnMineTemplate
            ) {
                when (this) {

                    btnMineDialog -> {
                        startActivity(DialogActivity::class.java)
                    }
                    btnMineHint -> {
                        startActivity(StatusActivity::class.java)
                    }
                    btnMineLogin -> {
                        routerNavigation(LoginRouterHub.PUBLIC_LOGIN_LOGINACTIVITY)
                    }
                    btnMineRegister -> {
                        routerNavigation(LoginRouterHub.PUBLIC_LOGIN_REGISTERACTIVITY)
                    }
                    btnMineForget -> {
                        routerNavigation(LoginRouterHub.PUBLIC_LOGIN_PASSWORDFORGETACTIVITY)
                    }
                    btnMineReset -> {
                        routerNavigation(LoginRouterHub.PUBLIC_LOGIN_PASSWORDRESETACTIVITY)
                    }
                    btnMineChange -> {
                        routerNavigation(LoginRouterHub.PUBLIC_LOGIN_PHONERESETACTIVITY)
                    }
                    btnMinePersonal -> {
                        routerNavigation(PersonalRouterHub.PUBLIC_PERSONAL_PERSONALDATAACTIVITY)
                    }
                    btnMineSetting -> {
                        routerNavigation(PersonalRouterHub.PUBLIC_PERSONAL_SETTINGACTIVITY)
                    }
                    btnMineGuide -> {
                        routerNavigation(SplashRouterHub.PUBLIC_SPLASH_GUIDEACTIVITY)
                    }
                    btnMineBrowser -> {
                        ARouter.getInstance().build(WebRouterHub.PUBLIC_WEBPAGEACTIVITY)
                            .withString("url", "https://www.baidu.com/")
                            .withString("title", "百度一下")
                            .navigation()
                    }
                    btnMineImageSelect -> {
                        ARouter.getInstance().navigation(IMediaService::class.java)
                            .startImageSelectActivity(
                                mActivity, 3
                            ) { toast(it.toString()) }
                    }
                    btnMineImagePreview -> {
                        val images = ArrayList<String>()
                        images.add("https://www.baidu.com/img/bd_logo.png")
                        images.add("https://avatars1.githubusercontent.com/u/28616817")
                        ARouter.getInstance().navigation(
                            IMediaService::class.java
                        ).startImagePreviewActivity(mActivity, images, 0)

                    }
                    btnMineVideoSelect -> {
                        ARouter.getInstance().navigation(IMediaService::class.java)
                            .startVideoSelectActivity(mActivity, 1) {
                                toast(it.toString())
                            }
                    }
                    btnMineVideoPlay -> {
                        val builder = VideoPlayBuilder()
                            .setVideoTitle("速度与激情特别行动")
                            .setVideoSource("http://vfx.mtime.cn/Video/2019/06/29/mp4/190629004821240734.mp4")
                            .setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                        ARouter.getInstance()
                            .build(MediaRouterHub.PUBLIC_MEDIA_VIDEOPLAYACTIVITY_LANDSCAPE)
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
                    btnMineTemplate -> {
                        routerNavigation(TestRouterHub.publicTestTestActivity)
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