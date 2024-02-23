package com.common.home.mvvm.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.common.core.base.mvvm.BaseVMActivity
import dagger.hilt.android.AndroidEntryPoint
import com.common.home.databinding.HomeActivityMainBinding
import com.common.home.mvvm.vm.MainViewModel
import com.common.home.R
import com.common.home.export.HomeExport.Companion.publicHomeMainActivity

@Route(path = publicHomeMainActivity)
@AndroidEntryPoint
class MainActivity : BaseVMActivity<HomeActivityMainBinding, MainViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.home_activity_main
    }

    override fun initData() {

    }
}