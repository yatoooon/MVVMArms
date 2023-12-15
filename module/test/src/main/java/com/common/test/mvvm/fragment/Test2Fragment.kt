package com.common.test.mvvm.fragment

import android.os.Bundle
import com.common.core.base.mvvm.BaseVMFragment
import dagger.hilt.android.AndroidEntryPoint
import com.common.test.databinding.TestFragmentTest2Binding
import com.common.test.mvvm.vm.Test2ViewModel
import com.common.test.R

@AndroidEntryPoint
class Test2Fragment : BaseVMFragment<TestFragmentTest2Binding, Test2ViewModel>() {
    companion object {
        fun newInstance(): Test2Fragment {
            val args = Bundle()
            val fragment = Test2Fragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.test_fragment_test2
    }

    override fun initData() {

    }
}