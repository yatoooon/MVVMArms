package com.common.splash.mvvm.activity

import com.common.core.base.mvvm.BaseVMActivity
import com.common.splash.R
import com.common.splash.databinding.SplashActivityMaintestBinding
import com.common.splash.mvvm.vm.MainTestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainTestActivity : BaseVMActivity<SplashActivityMaintestBinding, MainTestViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.splash_activity_maintest
    }

    override fun initData() {

    }
}