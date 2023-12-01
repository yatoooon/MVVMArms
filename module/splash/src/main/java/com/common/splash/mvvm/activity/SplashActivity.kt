package com.common.splash.mvvm.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.common.core.base.BaseActivity
import com.common.home.export.arouter.HomeRouterHub
import com.common.res.immersionbar.BindImmersionBar
import com.common.res.utils.routerNavigation
import com.common.splash.BuildConfig
import com.common.splash.R
import com.common.splash.databinding.SplashActivityBinding
import com.common.splash.export.arouter.SplashRouterHub
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


/**
 * 模板示例
 *
 */
@Route(path = SplashRouterHub.PUBLIC_SPLASH_SPLASHACTIVITY)
@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashActivityBinding>() {

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.splash_activity
    }


    override fun getImmersionBarType(): Int {
        return BindImmersionBar.FULLSCREEN
    }

    override fun initView() {
        super.initView()
        binding.lavSplashLottie.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                binding.lavSplashLottie.removeAnimatorListener(this)
                routerNavigation(HomeRouterHub.PUBLIC_HOME_MAINACTIVITY)
                finish()
            }
        })
        binding.ivSplashDebug.text = BuildConfig.BUILD_TYPE.uppercase(Locale.getDefault())
        if (BuildConfig.DEBUG) {
            binding.ivSplashDebug.visibility = View.VISIBLE
        } else {
            binding.ivSplashDebug.visibility = View.INVISIBLE
        }
    }
}