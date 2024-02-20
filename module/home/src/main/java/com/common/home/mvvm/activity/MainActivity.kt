package com.common.home.mvvm.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.BaseActivity
import com.common.home.R
import com.common.home.databinding.HomeActivityMainBinding
import com.common.home.export.HomeExport

import com.common.res.adapter.FragmentViewPager2Adapter
import com.common.res.ext.color
import com.common.res.immersionbar.BindImmersionBar
import com.common.res.utils.AppManager
import com.common.res.utils.DoubleClickUtils.isOnDoubleClick
import com.common.template.export.TemplateExport

import dagger.hilt.android.AndroidEntryPoint
import me.majiajie.pagerbottomtabstrip.internal.Utils
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem
import me.majiajie.pagerbottomtabstrip.item.NormalItemView


@Route(path = HomeExport.PUBLIC_HOME_MAINACTIVITY)
@AndroidEntryPoint
class MainActivity : BaseActivity<HomeActivityMainBinding>() {


    private var mPagerAdapter = FragmentViewPager2Adapter(this)


    override fun getLayoutId(): Int {
        return R.layout.home_activity_main
    }

    override fun initData() {
        val apply = binding.tab.custom()
            .addItem(newItem(R.drawable.app_logo, R.drawable.app_logo, "首页"))
            .addItem(newItem(R.drawable.app_logo,R.drawable.app_logo, "发现"))
            .addItem(newItem(R.drawable.app_logo,R.drawable.app_logo, "消息"))
            .addItem(newItem(R.drawable.app_logo,R.drawable.app_logo, "我的"))
            .build().apply {
                addSimpleTabItemSelectedListener { index, old ->
                    binding.vpHomePager.setCurrentItem(index, false)//false 不平滑过度
                }
            }
        mPagerAdapter.setFragmentCreators(listOf(
            {ARouter.getInstance().build(TemplateExport.PUBLIC_TEMPLATE_FRAGMENT_HOME)
                .withString("title", "首页")
                .navigation() as Fragment},
            {ARouter.getInstance().build(TemplateExport.PUBLIC_TEMPLATE_FRAGMENT_FOUND)
                .withString("title", "发现")
                .navigation() as Fragment},
            {ARouter.getInstance().build(TemplateExport.PUBLIC_TEMPLATE_FRAGMENT_MESSAGE)
                .withString("title", "消息")
                .navigation() as Fragment},
            {ARouter.getInstance().build(TemplateExport.PUBLIC_TEMPLATE_FRAGMENT_MINE)
                .withString("title", "我的")
                .navigation() as Fragment}
        ))
        binding.vpHomePager.adapter = mPagerAdapter
        binding.vpHomePager.offscreenPageLimit = mPagerAdapter.itemCount
        binding.vpHomePager.isUserInputEnabled = false //禁止viewpager2左右滑动
        binding.vpHomePager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                apply.setSelect(position)
            }
        })
        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        binding.vpHomePager.setCurrentItem(getIntent().getIntExtra("index", 0), false)//false 不平滑过度
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

    override fun isStatusBarDarkFont(): Boolean {
        return false
    }

    override fun getImmersionBarType(): Int {
        return BindImmersionBar.IMMERSION
    }

    //创建一个Item
    private fun newItem(drawable: Int, checkedDrawable: Int, text: String): BaseTabItem {
        val normalItemView = NormalItemView(this)
        normalItemView.initialize(drawable, checkedDrawable, text)
        normalItemView.setDefaultDrawable(Utils.tinting(AppCompatResources.getDrawable(context,drawable),Color.GRAY))
        normalItemView.setSelectedDrawable(Utils.tinting(AppCompatResources.getDrawable(context,drawable),color(R.color.res_primary_color)))
        normalItemView.setTextDefaultColor(Color.GRAY)
        normalItemView.setTextCheckedColor(color(R.color.res_primary_color))
        return normalItemView
    }

}


