package com.common.core.lifecycle

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.common.core.base.BaseFragment
import com.common.res.R
import com.common.res.immersionbar.BindImmersionBar
import com.common.res.immersionbar.BindImmersionBar.Companion.DEFAULTBAR
import com.common.res.immersionbar.BindImmersionBar.Companion.FULLSCREEN
import com.common.res.immersionbar.BindImmersionBar.Companion.IMMERSIONBAR
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import timber.log.Timber

class CoreFragmentLifecycleCallbacksImpl : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        Timber.i("%s - onFragmentAttached", f.toString())
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        Timber.i("%s - onFragmentCreated", f.toString())
    }

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        Timber.i("%s - onFragmentViewCreated", f.toString())
        //设置全局toolbar和title
        val toolbar = v.findViewById<View>(R.id.res_toolbar)
        if (toolbar != null) {
            val tvTitle = toolbar.findViewById<TextView>(R.id.res_tv_title)
            //找到 Toolbar 的标题栏并设置标题名
            if (tvTitle != null && !TextUtils.isEmpty(f.arguments?.getString("title"))) {
                tvTitle.text = f.arguments?.getString("title")
            }
            val ivBack = toolbar.findViewById<ImageView>(R.id.res_iv_back)
            ivBack.visibility = View.GONE
            ivBack?.setOnClickListener { f.activity?.onBackPressed() }
        }
    }

    override fun onFragmentActivityCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        Timber.i("%s - onFragmentActivityCreated", f.toString())
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        Timber.i("%s - onFragmentStarted", f.toString())
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        Timber.i("%s - onFragmentResumed", f.toString())
        //设置全局fragment状态栏
        if (f is BaseFragment<*>) {
            when (f.getImmersionBarType()) {
                IMMERSIONBAR -> {
                    ImmersionBar.with(f)
                        .statusBarDarkFont(f.isStatusBarDarkFont())
                        .navigationBarColor(R.color.res_color_ffffff)
                        .autoDarkModeEnable(true, 0.2f)
                        .init()
                }
                FULLSCREEN -> {
                    ImmersionBar.with(f)
                        .fullScreen(true)
                        .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                        .navigationBarColor(R.color.res_color_ffffff)
                        .init()
                }
                DEFAULTBAR -> {
                    ImmersionBar.with(f)
                        .fitsSystemWindows(true)
                        .autoDarkModeEnable(true)
                        .statusBarColor(R.color.res_color_ffffff)
                        .navigationBarColor(R.color.res_color_ffffff)
                        .autoDarkModeEnable(true, 0.2f)
                        .init()
                }

            }
        }
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        Timber.i("%s - onFragmentPaused", f.toString())
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        Timber.i("%s - onFragmentStopped", f.toString())
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        Timber.i("%s - onFragmentSaveInstanceState", f.toString())
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        Timber.i("%s - onFragmentViewDestroyed", f.toString())
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        Timber.i("%s - onFragmentDestroyed", f.toString())
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        Timber.i("%s - onFragmentDetached", f.toString())
    }
}