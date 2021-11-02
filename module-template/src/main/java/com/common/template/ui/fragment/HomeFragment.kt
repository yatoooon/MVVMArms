package com.common.template.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.common.core.base.mvvm.BaseVMFragment
import com.common.core.base.mvvm.BaseViewModel
import com.common.res.adapter.FragmentViewPager2Adapter
import com.common.res.callback.LoadingCallBack
import com.common.res.immersionbar.BindImmersionBar
import com.common.res.layout.XCollapsingToolbarLayout
import com.common.res.router.RouterHub
import com.common.template.R
import com.common.template.databinding.TemplateFragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ImmersionBar

@Route(path = RouterHub.PUBLIC_TEMPLATE_FRAGMENT_HOME)
class HomeFragment : BaseVMFragment<TemplateFragmentHomeBinding, BaseViewModel>(),
    XCollapsingToolbarLayout.OnScrimsListener {


    private lateinit var mPagerAdapter: FragmentViewPager2Adapter
    private val fragments = mutableListOf<Fragment>(
        StatusFragment.newInstance(),
        StatusFragment.newInstance()
    )

    override fun getLayoutId(): Int {
        return R.layout.template_fragment_home
    }


    override fun initData() {


    }

    override fun initView() {
        ImmersionBar.setTitleBar(this, binding.tbHomeTitle)
        mPagerAdapter = FragmentViewPager2Adapter(this)
        mPagerAdapter.setFragments(fragments)
        binding.ctlHomeBar.setOnScrimsListener(this)
        binding.vpHomePager.adapter = mPagerAdapter
        binding.vpHomePager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        TabLayoutMediator(binding.rvHomeTab, binding.vpHomePager) { tab, position ->
            when (position) {
                0 -> tab.text = "列表演示"
                1 -> tab.text = "网页演示"
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        ImmersionBar.with(requireActivity())
            .statusBarDarkFont(binding.ctlHomeBar.tagScrimsShown)
            .statusBarColor(R.color.res_transparent)
            .navigationBarColor(R.color.res_white)
            .init()
    }

    override fun initObserve() {

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