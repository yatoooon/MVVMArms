package com.common.home.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.common.core.base.mvvm.BaseVMFragment
import com.common.core.base.mvvm.BaseViewModel
import com.common.home.R
import com.common.home.databinding.HomeFragmentBinding
import com.common.res.immersionbar.BindImmersionBar
import com.common.res.layout.XCollapsingToolbarLayout
import com.gyf.immersionbar.ImmersionBar

open class HomeFragment : BaseVMFragment<HomeFragmentBinding, BaseViewModel>() {

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

    }

    override fun initViewClick() {
        ImmersionBar.setTitleBar(this, binding.tbHomeTitle)
        binding.ctlHomeBar.setOnScrimsListener(object : XCollapsingToolbarLayout.OnScrimsListener {
            override fun onScrimsStateChange(layout: XCollapsingToolbarLayout?, shown: Boolean) {
                ImmersionBar.with(requireActivity()).statusBarDarkFont(shown).init()
                binding.tvHomeAddress.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        if (shown) R.color.res_black else R.color.res_white
                    )
                )
                binding.tvHomeHint.setBackgroundResource(if (shown) R.drawable.home_search_bar_gray_bg else R.drawable.home_search_bar_transparent_bg)
                binding.tvHomeHint.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        if (shown) R.color.res_black60 else R.color.res_white60
                    )
                )
                ImageViewCompat.setImageTintList(
                    binding.ivHomeSearch,
                    ColorStateList.valueOf(getColor(if (shown) R.color.res_common_icon_color else R.color.res_white))
                )
            }
        })
    }


    override fun initObserve() {

    }

    override fun isStatusBarDarkFont(): Boolean {
        return false
    }

    override fun getImmersionBarType(): Int {
        return BindImmersionBar.IMMERSIONBAR
    }

}