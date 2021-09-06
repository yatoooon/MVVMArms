package com.common.res.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoSlideViewPager : ViewPager {
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?) : super(context!!) {}

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return false
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item)
    }
}