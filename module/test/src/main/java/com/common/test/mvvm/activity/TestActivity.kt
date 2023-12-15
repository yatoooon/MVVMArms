package com.common.test.mvvm.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.common.core.base.mvvm.BaseVMActivity
import dagger.hilt.android.AndroidEntryPoint
import com.common.test.databinding.TestActivityTestBinding
import com.common.test.mvvm.vm.TestViewModel
import com.common.test.R
import com.common.test.export.arouter.TestRouterHub

@Route(path = TestRouterHub.publicTestTestActivity)
@AndroidEntryPoint
class TestActivity : BaseVMActivity<TestActivityTestBinding, TestViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.test_activity_test
    }

    override fun initData() {

    }
}