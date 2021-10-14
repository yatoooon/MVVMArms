package com.common.home.ui.fragment

import android.os.Bundle
import com.common.core.base.mvvm.BaseVMFragment
import com.common.core.base.mvvm.BaseViewModel
import com.common.home.R
import com.common.home.databinding.HomeFragmentBinding

open class HomeFragment : BaseVMFragment<HomeFragmentBinding, BaseViewModel>() {

    companion object{
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

}