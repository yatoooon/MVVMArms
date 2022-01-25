package com.arms.splash.mvvm.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.arms.core.base.BaseActivity
import com.arms.export.arouter.RouterHub
import com.arms.export.arouter.routerNavigation
import com.arms.common.immersionbar.BindImmersionBar
import com.arms.splash.BuildConfig
import com.arms.splash.R
import com.arms.splash.databinding.SplashActivityBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * 模板示例
 *
 */
@Route(path = RouterHub.PUBLIC_SPLASH_SPLASHACTIVITY)
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
        binding.lavSplashLottie.repeatCount = 0
        binding.lavSplashLottie.setAnimation(R.raw.splash_welcome)
        binding.lavSplashLottie.playAnimation()
        binding.lavSplashLottie.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                binding.lavSplashLottie.removeAnimatorListener(this)
                routerNavigation(RouterHub.PUBLIC_HOME_MAINACTIVITY)
                finish()
            }
        })
        binding.ivSplashDebug.text = BuildConfig.BUILD_TYPE.toUpperCase()
        if (BuildConfig.DEBUG) {
            binding.ivSplashDebug.visibility = View.VISIBLE
        } else {
            binding.ivSplashDebug.visibility = View.INVISIBLE
        }
    }
}