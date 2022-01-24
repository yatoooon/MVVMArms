package com.common.splash.mvvm.activity

import com.basis.core.base.mvvm.BaseVMActivity
import dagger.hilt.android.AndroidEntryPoint
import com.common.splash.databinding.SplashActivityMaintestBinding
import com.common.splash.mvvm.vm.MainTestViewModel
import com.common.splash.R

@AndroidEntryPoint
class MainTestActivity : BaseVMActivity<SplashActivityMaintestBinding, MainTestViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.splash_activity_maintest
    }

    override fun initData() {

    }
}