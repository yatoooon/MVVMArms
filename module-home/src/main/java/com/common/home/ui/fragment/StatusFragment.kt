package com.common.home.ui.fragment

import android.os.Bundle
import com.common.core.base.mvvm.BaseVMFragment
import com.common.core.base.mvvm.BaseViewModel
import com.common.home.R
import com.common.home.databinding.HomeStatusFragmentBinding

class StatusFragment : BaseVMFragment<HomeStatusFragmentBinding, BaseViewModel>() {
    companion object{
        fun newInstance(): StatusFragment {
            val args = Bundle()
            val fragment = StatusFragment()
            fragment.arguments = args
            return fragment
        }
    }



    override fun getLayoutId(): Int {
        return R.layout.home_status_fragment
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}