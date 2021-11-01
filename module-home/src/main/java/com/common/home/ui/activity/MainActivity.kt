package com.common.home.ui.activity

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.BaseActivity
import com.common.home.R
import com.common.home.databinding.HomeActivityMainBinding
import com.common.res.adapter.FragmentViewPager2Adapter
import com.common.res.immersionbar.BindImmersionBar
import com.common.res.router.RouterHub
import com.common.res.utils.AppManager
import com.common.res.utils.DoubleClickUtils.isOnDoubleClick
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterHub.PUBLIC_MAIN)
@AndroidEntryPoint
class MainActivity : BaseActivity<HomeActivityMainBinding>() {


    private var mPagerAdapter = FragmentViewPager2Adapter(this)

    private val fragments = mutableListOf(
        ARouter.getInstance().build(RouterHub.PUBLIC_TEMPLATE_FRAGMENT_HOME)
            .withString("title", "首页")
            .navigation() as Fragment,
        ARouter.getInstance().build(RouterHub.PUBLIC_TEMPLATE_FRAGMENT_FOUND)
            .withString("title", "发现")
            .navigation() as Fragment,
        ARouter.getInstance().build(RouterHub.PUBLIC_TEMPLATE_FRAGMENT_FOUND)
            .withString("title", "消息")
            .navigation() as Fragment,
        ARouter.getInstance().build(RouterHub.PUBLIC_TEMPLATE_FRAGMENT_FOUND)
            .withString("title", "我的")
            .navigation() as Fragment
    )


    override fun getLayoutId(): Int {
        return R.layout.home_activity_main
    }

    override fun initData() {
        val apply = binding.tab.material()
            .addItem(R.drawable.home_logo, "首页")
            .addItem(R.drawable.home_logo, "发现")
            .addItem(R.drawable.home_logo, "消息")
            .addItem(R.drawable.home_logo, "我的")
            .build().apply {
                addSimpleTabItemSelectedListener { index, old ->
                    binding.vpHomePager.setCurrentItem(index, false)//false 不平滑过度
                }
            }
        mPagerAdapter.setFragments(fragments)
        binding.vpHomePager.offscreenPageLimit = fragments.size
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

    override fun getImmersionBarType(): Int {
        return BindImmersionBar.IMMERSIONBAR
    }

    override fun isStatusBarDarkFont(): Boolean {
        return false
    }
}


