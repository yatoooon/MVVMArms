package com.common.home.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.common.core.base.mvvm.BaseVMFragment
import com.common.core.base.mvvm.BaseViewModel
import com.common.home.R
import com.common.home.databinding.HomeFragmentBinding
import com.common.res.adapter.FragmentViewPager2Adapter
import com.common.res.immersionbar.BindImmersionBar
import com.common.res.layout.XCollapsingToolbarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ImmersionBar

class HomeFragment : BaseVMFragment<HomeFragmentBinding, BaseViewModel>(),
    XCollapsingToolbarLayout.OnScrimsListener {

    companion object {
        fun newInstance(title: String): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            args.putString("title", title)
            return fragment
        }
    }

    private lateinit var mPagerAdapter: FragmentViewPager2Adapter
    private val fragments = mutableListOf<Fragment>(
        StatusFragment.newInstance(),
        StatusFragment.newInstance()
    )

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initViewClick() {
        ImmersionBar.setTitleBar(this, binding.tbHomeTitle)
        mPagerAdapter = FragmentViewPager2Adapter(this)
        mPagerAdapter.setFragments(fragments)
        binding.ctlHomeBar.setOnScrimsListener(this)
        binding.vpHomePager.adapter = mPagerAdapter
        binding.vpHomePager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        (binding.vpHomePager.getChildAt(0) as RecyclerView).isNestedScrollingEnabled = false
        TabLayoutMediator(binding.rvHomeTab, binding.vpHomePager) { tab, position ->
            tab.text = "TAB ${(position + 1)}"
        }.attach()
    }


    override fun initObserve() {

    }

    override fun isStatusBarDarkFont(): Boolean {
        return false
    }

    override fun getImmersionBarType(): Int {
        return BindImmersionBar.IMMERSIONBAR
    }

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

}