package com.common.simple.mvvm.ui.activity

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.content.res.AppCompatResources
import com.common.core.base.BaseActivity
import com.common.res.ext.load
import com.common.res.immersionbar.BindFullScreen
import com.common.res.router.RouterHub
import com.common.res.router.routerNavigation
import com.common.simple.R
import com.common.simple.databinding.AppActivitySplashBinding
import com.common.template.mvvm.ui.TemplateActivity
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
                binding.ivLogo.load(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.res_white_round_bg
                    )
                )
                routerNavigation(RouterHub.PUBLIC_TEMPLATE)
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        view.startAnimation(anim)
    }


}