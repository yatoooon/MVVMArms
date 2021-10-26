package com.common.found.ui.fragment

import android.os.Bundle
import com.common.core.base.mvvm.BaseVMFragment
import com.common.core.base.mvvm.BaseViewModel
import com.common.found.R
import com.common.found.databinding.FoundFragmentBinding

open class FoundFragment : BaseVMFragment<FoundFragmentBinding, BaseViewModel>() {

    companion object {
        fun newInstance(title: String): FoundFragment {
            val args = Bundle()
            val fragment = FoundFragment()
            fragment.arguments = args
            args.putString("title", title)
            return fragment
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.found_fragment
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}