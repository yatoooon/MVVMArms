package com.arms.template.mvvm.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.arms.core.base.BaseFragment
import com.arms.export.arouter.RouterHub
import com.arms.export.arouter.routerNavigation
import com.arms.export.arouter.service.IMediaService
import com.arms.export.data.VideoPlayBuilder
import com.arms.common.utils.bindViewClickListener
import com.arms.template.R
import com.arms.template.databinding.TemplateFragmentMineBinding
import com.arms.template.mvvm.activity.DialogActivity
import com.arms.template.mvvm.activity.StatusActivity
import com.arms.template.mvvm.activity.TemplateActivity
import com.gyf.immersionbar.ImmersionBar
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.shadow.dynamic.host.EnterCallback
import com.tencent.shadow.dynamic.host.PluginManager
import com.tencent.shadow.sample.introduce_shadow_lib.Constant
import com.tencent.shadow.sample.introduce_shadow_lib.InitApplication
import java.util.*

@Route(path = RouterHub.PUBLIC_TEMPLATE_FRAGMENT_MINE)
class MineFragment : BaseFragment<TemplateFragmentMineBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.template_fragment_mine
    }

    override fun initData() {
    }


    override fun initView() {
        leftIcon = null
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
                btnMineTemplate,
                btnMineCommon
            ) {
                when (this) {

                    btnMineDialog -> {
                        startActivity(DialogActivity::class.java)
                    }
                    btnMineHint -> {
                        startActivity(StatusActivity::class.java)
                    }
                    btnMineLogin -> {
//                        routerNavigation(RouterHub.PUBLIC_LOGIN_LOGINACTIVITY)

                        val bundle = Bundle()
                        bundle.putString(Constant.KEY_PLUGIN_ZIP_PATH,"/data/local/tmp/login-plugin-debug.zip")
                        bundle.putString(Constant.KEY_PLUGIN_PART_KEY,"login-plugin")
                        bundle.putString(Constant.KEY_ACTIVITY_CLASSNAME,"com.arms.login.mvvm.activity.LoginActivity")
                        InitApplication.getPluginManager().enter(context,Constant.FROM_ID_START_ACTIVITY,
                            bundle,object :EnterCallback{
                                override fun onShowLoadingView(view: View?) {
                                    toast("login插件加载中")
                                }

                                override fun onCloseLoadingView() {
                                }

                                override fun onEnterComplete() {
                                    toast("login插件完成")
                                }
                            })
                    }
                    btnMineRegister -> {
                        routerNavigation(RouterHub.PUBLIC_LOGIN_REGISTERACTIVITY)
                    }
                    btnMineForget -> {
                        routerNavigation(RouterHub.PUBLIC_LOGIN_PASSWORDFORGETACTIVITY)
                    }
                    btnMineReset -> {
                        routerNavigation(RouterHub.PUBLIC_LOGIN_PASSWORDRESETACTIVITY)
                    }
                    btnMineChange -> {
                        routerNavigation(RouterHub.PUBLIC_LOGIN_PHONERESETACTIVITY)
                    }
                    btnMinePersonal -> {
                        routerNavigation(RouterHub.PUBLIC_PERSONAL_PERSONALDATAACTIVITY)
                    }
                    btnMineSetting -> {
                        routerNavigation(RouterHub.PUBLIC_PERSONAL_SETTINGACTIVITY)
                    }
                    btnMineGuide -> {
                        val bundle = Bundle()
                        bundle.putString(Constant.KEY_PLUGIN_ZIP_PATH,"/data/local/tmp/splash-plugin-debug.zip")
                        bundle.putString(Constant.KEY_PLUGIN_PART_KEY,"splash-plugin")
                        bundle.putString(Constant.KEY_ACTIVITY_CLASSNAME,"com.arms.splash.mvvm.activity.SplashActivity")
                        InitApplication.getPluginManager().enter(context,Constant.FROM_ID_START_ACTIVITY,
                            bundle,object :EnterCallback{
                                override fun onShowLoadingView(view: View?) {
                                    toast("login插件加载中")
                                }

                                override fun onCloseLoadingView() {
                                }

                                override fun onEnterComplete() {
                                    toast("login插件完成")
                                }
                            })
//                        routerNavigation(RouterHub.PUBLIC_SPLASH_GUIDEACTIVITY)
                    }
                    btnMineBrowser -> {
//                        ARouter.getInstance().build(RouterHub.PUBLIC_WEBPAGEACTIVITY)
//                            .withString("url", "www.baidu.com")
//                            .withString("title", "百度一下")
//                            .navigation()
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
                    btnMineTemplate -> {
                        startActivity(TemplateActivity::class.java)
                    }
                    btnMineCommon -> {
                        val bundle = Bundle()
                        bundle.putString(Constant.KEY_PLUGIN_ZIP_PATH, "/data/local/tmp/base-plugin-debug.zip")
                        bundle.putString(Constant.KEY_PLUGIN_PART_KEY, "base-plugin")
                        val pluginManager = InitApplication.getPluginManager()
                        pluginManager.enter(
                            context,
                            Constant.FROM_LOAD_PLUGIN,
                           bundle,
                            object : EnterCallback {
                                override fun onShowLoadingView(view: View?) {
                                    showLoadingDialog()
                                }

                                override fun onCloseLoadingView() {
                                    hideLoadingDialog()
                                }

                                override fun onEnterComplete() {
                                    toast("插件成功加载")
                                }
                            })
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