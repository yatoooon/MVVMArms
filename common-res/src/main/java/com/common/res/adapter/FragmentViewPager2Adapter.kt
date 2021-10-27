package com.common.res.adapter

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.fragment.app.FragmentActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import java.util.ArrayList

class FragmentViewPager2Adapter : FragmentStateAdapter {

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity) {}
    constructor(fragment: Fragment) : super(fragment) {}
    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle) : super(
        fragmentManager,
        lifecycle
    ) {}

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    private var fragmentList: List<Fragment> = ArrayList()
    fun setFragments(fragments: List<Fragment>) {
        fragmentList = fragments
    }
}