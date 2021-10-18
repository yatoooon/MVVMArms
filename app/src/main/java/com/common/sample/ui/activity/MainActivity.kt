package com.common.sample.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.common.core.base.BaseActivity
import com.common.core.base.BaseFragment
import com.common.core.base.mvvm.BaseVMFragment
import com.common.found.ui.fragment.FoundFragment
import com.common.home.ui.fragment.HomeFragment
import com.common.res.adapter.FragmentPagerAdapter
import com.common.sample.R
import com.common.sample.databinding.AppActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<AppActivityMainBinding>() {

    private var mPagerAdapter = FragmentPagerAdapter<BaseFragment<*>>(this)

    override fun getLayoutId(): Int {
        return R.layout.app_activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
        mPagerAdapter.addFragment(HomeFragment.newInstance())
        mPagerAdapter.addFragment(FoundFragment.newInstance())
        binding.vpHomePager.adapter = mPagerAdapter
        binding.tab.material()
            .addItem(R.drawable.app_logo, "首页")
            .addItem(R.drawable.app_logo, "发现")
            .addItem(R.drawable.app_logo, "消息")
            .addItem(R.drawable.app_logo, "我的")
            .build().setupWithViewPager(binding.vpHomePager)
    }

    override fun initViewClick() {
    }
}


