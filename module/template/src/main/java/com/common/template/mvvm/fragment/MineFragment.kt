package com.common.template.mvvm.fragment

import android.content.pm.ActivityInfo
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.BaseFragment
import com.common.login.export.LoginExport
import com.common.media.export.MediaExport

import com.common.media.export.data.VideoPlayBuilder
import com.common.personal.export.PersonalExport

import com.common.res.utils.bindViewClickListener
import com.common.res.utils.routerNavigation
import com.common.splash.export.SplashExport
import com.common.template.R
import com.common.template.databinding.TemplateFragmentMineBinding
import com.common.template.export.TemplateExport

import com.common.template.mvvm.activity.DialogActivity
import com.common.template.mvvm.activity.StatusActivity
import com.common.test.export.TestExport
import com.common.web.export.WebExport
import com.flyjingfish.module_communication_annotation.ImplementClassUtils

import com.gyf.immersionbar.ImmersionBar
import com.tencent.bugly.crashreport.CrashReport

@Route(path = TemplateExport.PUBLIC_TEMPLATE_FRAGMENT_MINE)
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
                        routerNavigation(LoginExport.PUBLIC_LOGIN_LOGINACTIVITY)
                    }
                    btnMineRegister -> {
                        routerNavigation(LoginExport.PUBLIC_LOGIN_REGISTERACTIVITY)
                    }
                    btnMineForget -> {
                        routerNavigation(LoginExport.PUBLIC_LOGIN_PASSWORDFORGETACTIVITY)
                    }
                    btnMineReset -> {
                        routerNavigation(LoginExport.PUBLIC_LOGIN_PASSWORDRESETACTIVITY)
                    }
                    btnMineChange -> {
                        routerNavigation(LoginExport.PUBLIC_LOGIN_PHONERESETACTIVITY)
                    }
                    btnMinePersonal -> {
                        routerNavigation(PersonalExport.PUBLIC_PERSONAL_PERSONALDATAACTIVITY)
                    }
                    btnMineSetting -> {
                        routerNavigation(PersonalExport.PUBLIC_PERSONAL_SETTINGACTIVITY)
                    }
                    btnMineGuide -> {
                        routerNavigation(SplashExport.PUBLIC_SPLASH_GUIDEACTIVITY)
                    }
                    btnMineBrowser -> {
                        ARouter.getInstance().build(WebExport.PUBLIC_WEBPAGEACTIVITY)
                            .withString("url", "https://www.baidu.com/")
                            .withString("title", "百度一下")
                            .navigation()
                    }
                    btnMineImageSelect -> {
                        ImplementClassUtils.getSingleInstance<MediaExport>(MediaExport::class)
                            .startImageSelectActivity(
                                mActivity, 3
                            ) { toast(it.toString()) }
                    }
                    btnMineImagePreview -> {
                        val images = ArrayList<String>()
                        images.add("https://www.baidu.com/img/bd_logo.png")
                        images.add("https://avatars1.githubusercontent.com/u/28616817")
                        ImplementClassUtils.getSingleInstance<MediaExport>(MediaExport::class).startImagePreviewActivity(mActivity, images, 0)

                    }
                    btnMineVideoSelect -> {
                        ImplementClassUtils.getSingleInstance<MediaExport>(MediaExport::class)
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
                            .build(MediaExport.PUBLIC_MEDIA_VIDEOPLAYACTIVITY_LANDSCAPE)
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
                        routerNavigation(TestExport.publicTestTestActivity)
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