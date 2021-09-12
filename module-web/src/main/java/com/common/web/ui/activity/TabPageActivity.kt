package com.common.web.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.common.core.base.BaseActivity
import com.common.web.R
import com.common.web.databinding.WebActivityTabpageBinding
import com.common.web.entity.RefreshTabRightBtnEntity
import com.common.web.entity.TabPageEntity.TabEntity
import com.common.web.ui.adapter.TabPagerAdapter
import com.common.web.ui.fragment.WebPageFrgment
import com.common.web.view.BaseDWebView
import java.util.*

/**
 * desc :TabPageActivity
 * author：panyy
 * data：2018/3/14
 */
class TabPageActivity : BaseActivity<WebActivityTabpageBinding>() {


    override fun getLayoutId(): Int {
        return R.layout.web_activity_tabpage
    }

    private var tabs: ArrayList<TabEntity>? = null
    private val tabFragments: MutableList<WebPageFrgment> = ArrayList()
    private var currPosition = 0
    private var btnRights: List<RefreshTabRightBtnEntity.BtnrightEntity>? = null

    override fun initData(savedInstanceState: Bundle?) {
        tabs = this.intent.getSerializableExtra("btntabs") as ArrayList<TabEntity>?
        binding.layoutToolbar.ivWebframeBack.setOnClickListener { v -> onBackPressed() }
        binding.layoutToolbar.radioGroup.visibility = View.VISIBLE
        for (i in tabs!!.indices) {
            val tab = tabs!![i]
            tabFragments.add(WebPageFrgment.newInstance(tab.url))
            val rb = LayoutInflater.from(context).inflate(R.layout.web_layout_radiobutton, null) as RadioButton
            rb.text = tab.name
            binding.layoutToolbar.radioGroup.addView(rb)
        }
        val pagerAdapter = TabPagerAdapter(supportFragmentManager, tabFragments)
        binding.viewpager.adapter = pagerAdapter
        binding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                val rb = binding.layoutToolbar.radioGroup.getChildAt(position) as RadioButton
                rb.isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        binding.layoutToolbar.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            for (i in 0 until group.childCount) {
                val view = group.getChildAt(i)
                if (view.id == checkedId) {
                    currPosition = i
                    break
                }
            }
            refreshTabRightBtn()
            binding.viewpager.currentItem = currPosition
        }
        val rb = binding.layoutToolbar.radioGroup.getChildAt(0) as RadioButton
        rb.isChecked = true
    }

    fun setAndRefreshTabRightBtn(btnRights: List<RefreshTabRightBtnEntity.BtnrightEntity>?) {
        this.btnRights = btnRights
        refreshTabRightBtn()
    }

    private fun refreshTabRightBtn() {
        runOnUiThread {
            if (btnRights != null) {
                val name = btnRights!![currPosition].name
                if (!TextUtils.isEmpty(name)) {
                    binding.layoutToolbar.tvWebframeRightText.visibility = View.VISIBLE
                    binding.layoutToolbar.tvWebframeRightText.text = name
                    binding.layoutToolbar.tvWebframeRightText.setOnClickListener(View.OnClickListener {
                        val dWebView: BaseDWebView? = tabFragments[currPosition].getDWebView()
                        dWebView?.callHandler(btnRights!![currPosition].data, arrayOf())
                    })
                }
                val image = btnRights!![currPosition].image
                if (!TextUtils.isEmpty(image)) {
                    binding.layoutToolbar.ivRightImage.visibility = View.VISIBLE
                    Glide.with(this@TabPageActivity).load(image).into(binding.layoutToolbar.ivRightImage)
                    binding.layoutToolbar.ivRightImage.setOnClickListener {
                        tabFragments[currPosition].getDWebView()?.callHandler(btnRights!![currPosition].data, arrayOf())
                    }
                }
            } else {
                binding.layoutToolbar.tvWebframeRightText.visibility = View.GONE
            }
        }
    }

    companion object {
        fun openTabPage(context: Context?, tabs: ArrayList<TabEntity>?) {
            val intent = Intent(context, TabPageActivity::class.java)
            intent.putExtra("btntabs", tabs)
            context?.startActivity(intent)
        }
    }
}