package com.arms.template.mvvm.fragment

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.arms.core.base.BaseModel
import com.arms.core.base.mvvm.BaseVMFragment
import com.arms.core.base.mvvm.BaseViewModel
import com.arms.export.arouter.RouterHub
import com.arms.common.adapter.FragmentViewPager2Adapter
import com.arms.res.layout.XCollapsingToolbarLayout
import com.arms.template.R
import com.arms.template.databinding.TemplateFragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ImmersionBar

@Route(path = RouterHub.PUBLIC_TEMPLATE_FRAGMENT_HOME)
class HomeFragment : BaseVMFragment<TemplateFragmentHomeBinding, BaseViewModel<BaseModel>>(),
    XCollapsingToolbarLayout.OnScrimsListener {


    private lateinit var mPagerAdapter: FragmentViewPager2Adapter

    private val fragments = mutableListOf<Fragment>(
        StatusFragment.newInstance(),
        RepositoryFragment.newInstance(),
        ARouter.getInstance().build(RouterHub.PUBLIC_WEBPAGEFRAGMENT)
            .withString("url", "https://github.com/yatoooon")
            .navigation() as Fragment,
        TemplateFragment.newInstance()
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
        binding.vpHomePager.offscreenPageLimit = fragments.size
        binding.vpHomePager.isUserInputEnabled = false
        binding.vpHomePager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        TabLayoutMediator(binding.rvHomeTab, binding.vpHomePager) { tab, position ->
            if (position < 2) {
                tab.text = "列表" + (position + 1)
            } else if (position == 2) {
                tab.text = "网页"
            } else if (position == 3) {
                tab.text = "fragment模版"
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
        binding.tvHomeHint.setBackgroundResource(if (shown) R.drawable.template_search_bar_gray_bg else R.drawable.template_search_bar_transparent_bg)
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