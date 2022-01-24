package com.arms.common.layout

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 禁用水平滑动的ViewPager（一般用于 APP 首页的 ViewPager + Fragment）
 */
class NoScrollViewPager : ViewPager {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // 不拦截这个事件
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        // 不处理这个事件
        return false
    }

    override fun executeKeyEvent(event: KeyEvent): Boolean {
        // 不响应按键事件
        return false
    }

    override fun setCurrentItem(item: Int) {
        // 只有相邻页才会有动画
        super.setCurrentItem(item, Math.abs(currentItem - item) == 1)
    }
}