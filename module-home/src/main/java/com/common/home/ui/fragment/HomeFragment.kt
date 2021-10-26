package com.common.home.ui.fragment

import android.os.Bundle
import com.common.core.base.mvvm.BaseVMFragment
import com.common.core.base.mvvm.BaseViewModel
import com.common.home.R
import com.common.home.databinding.HomeFragmentBinding
import com.common.res.immersionbar.BindImmersionBar
import com.gyf.immersionbar.ImmersionBar

open class HomeFragment : BaseVMFragment<HomeFragmentBinding, BaseViewModel>(), BindImmersionBar {

    companion object {
        fun newInstance(title: String): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            args.putString("title", title)
            return fragment
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

    override fun initData(savedInstanceState: Bundle?) {
        ImmersionBar.setTitleBar(activity, binding.tbHomeTitle)
    }


}