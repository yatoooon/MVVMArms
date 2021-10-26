package com.common.sample.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.common.core.base.BaseActivity
import com.common.found.ui.fragment.FoundFragment
import com.common.home.ui.fragment.HomeFragment
import com.common.res.adapter.FragmentViewPager2Adapter
import com.common.res.immersionbar.BindImmersionBar
import com.common.res.utils.AppManager
import com.common.res.utils.DoubleClickUtils.isOnDoubleClick
import com.common.sample.R
import com.common.sample.databinding.AppActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<AppActivityMainBinding>() ,BindImmersionBar{


    private var mPagerAdapter = FragmentViewPager2Adapter(this)

    private val fragments = mutableListOf<Fragment>(
        HomeFragment.newInstance("首页"),
        FoundFragment.newInstance("发现"),
        FoundFragment.newInstance("消息"),
        FoundFragment.newInstance("我的")
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
                    binding.vpHomePager.setCurrentItem(index, false)//false 不平滑过度
                }
            }
        mPagerAdapter.setFragments(fragments)
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

    override fun onBackPressed() {
        if (!isOnDoubleClick()) {
            toast(R.string.res_home_exit_hint)
            return
        }
        // 移动到上一个任务栈，避免侧滑引起的不良反应
        moveTaskToBack(false)
        postDelayed({
            // 进行内存优化，销毁掉所有的界面
            AppManager.getAppManager().killAll()
        }, 300)
    }
}


