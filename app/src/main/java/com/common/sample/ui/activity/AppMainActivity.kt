package com.common.sample.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.common.core.base.BaseActivity
import com.common.found.ui.fragment.FoundFragment
import com.common.home.ui.fragment.HomeFragment
import com.common.sample.R
import com.common.sample.databinding.AppActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppMainActivity : BaseActivity<AppActivityMainBinding>() {
    private val homeFragment = HomeFragment.newInstance()
    private val foundFragment = FoundFragment.newInstance()
    private var activeFragment: Fragment = homeFragment

    override fun getLayoutId(): Int {
        return R.layout.app_activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initViewClick() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, homeFragment, getString(R.string.res_nav_index))
            add(
                R.id.container,
                foundFragment,
                getString(R.string.res_nav_found)
            ).hide(foundFragment)
        }.commit()
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(homeFragment)
                        .commit()
                    activeFragment = homeFragment
                    true
                }
                R.id.navigation_found -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(foundFragment)
                        .commit()
                    activeFragment = foundFragment
                    true
                }
                else -> false
            }
        }
    }
}


