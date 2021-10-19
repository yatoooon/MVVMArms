package com.common.sample.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.common.core.base.BaseActivity
import com.common.found.ui.fragment.FoundFragment
import com.common.home.ui.fragment.HomeFragment
import com.common.res.adapter.FragmentViewPager2Adapter
import com.common.sample.R
import com.common.sample.databinding.AppActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<AppActivityMainBinding>() {

    private var mPagerAdapter = FragmentViewPager2Adapter(this)
    private val fragments = mutableListOf<Fragment>(
        HomeFragment.newInstance(),
        FoundFragment.newInstance(),
        FoundFragment.newInstance(),
        FoundFragment.newInstance()
    )
    private val fragmentTitles = mutableListOf<String>(
        "首页", "发现", "消息", "我的"
    )

    override fun getLayoutId(): Int {
        return R.layout.app_activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
        val apply = binding.tab.material()
            .addItem(R.drawable.app_logo, "首页")
            .addItem(R.drawable.app_logo, "发现")
            .addItem(R.drawable.app_logo, "消息")
            .addItem(R.drawable.app_logo, "我的")
            .build().apply {
                addSimpleTabItemSelectedListener { index, old ->
                    binding.vpHomePager.setCurrentItem(index,false)//false 不平滑过度
                }
            }
        mPagerAdapter.setFragments(fragments)
        mPagerAdapter.setFragmentTitles(fragmentTitles)
        binding.vpHomePager.adapter = mPagerAdapter
        binding.vpHomePager.isUserInputEnabled = false //禁止viewpager2左右滑动
        binding.vpHomePager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                apply.setSelect(position)
            }
        })


    }

    override fun initViewClick() {
    }
}


