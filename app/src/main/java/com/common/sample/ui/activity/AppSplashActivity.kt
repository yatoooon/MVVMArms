package com.common.sample.ui.activity

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.common.core.base.BaseActivity
import com.common.res.immersionbar.BindFullScreen
import com.common.res.router.RouterHub
import com.common.res.router.routerNavigation
import com.common.sample.R
import com.common.sample.databinding.AppActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 模板示例
 *
 */
@AndroidEntryPoint
class AppSplashActivity : BaseActivity<AppActivitySplashBinding>(), BindFullScreen {

    override fun initData(savedInstanceState: Bundle?) {
        startAnimation(viewDataBinding.rootView)
    }

    override fun getLayoutId(): Int {
        return R.layout.app_activity_splash
    }

    private fun startAnimation(view: View) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.app_splash_anim)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                routerNavigation(RouterHub.PUBLIC_TEMPLATE)
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        view.startAnimation(anim)
    }


}