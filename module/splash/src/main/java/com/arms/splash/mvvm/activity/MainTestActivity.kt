package com.arms.splash.mvvm.activity

import com.arms.core.base.mvvm.BaseVMActivity
import dagger.hilt.android.AndroidEntryPoint
import com.arms.splash.databinding.SplashActivityMaintestBinding
import com.arms.splash.mvvm.vm.MainTestViewModel
import com.arms.splash.R

@AndroidEntryPoint
class MainTestActivity : BaseVMActivity<SplashActivityMaintestBinding, MainTestViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.splash_activity_maintest
    }

    override fun initData() {

    }
}