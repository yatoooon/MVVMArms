package com.common.res.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentViewPager2Adapter : FragmentStateAdapter {

    private var fragmentCreators: List<() -> Fragment> = emptyList()

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity)
    constructor(fragment: Fragment) : super(fragment)
    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle) : super(
        fragmentManager,
        lifecycle
    )

    override fun createFragment(position: Int): Fragment {
        return fragmentCreators[position].invoke()
    }

    override fun getItemCount(): Int {
        return fragmentCreators.size
    }

    fun setFragmentCreators(creators: List<() -> Fragment>) {
        this.fragmentCreators = creators
    }
}
