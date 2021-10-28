package com.common.res.layout

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/05/07
 * desc   : ViewPager 中使用 PhotoView 时出现 pointerIndex out of range 异常
 */
class PhotoViewPager : NestedViewPager {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    )

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // 当 PhotoView 和 ViewPager 组合时 ，用双指进行放大时 是没有问题的，但是用双指进行缩小的时候，程序就会崩掉
        // 并且抛出 java.lang.IllegalArgumentException: pointerIndex out of range
        return try {
            super.onInterceptTouchEvent(ev)
        } catch (ignored: IllegalArgumentException) {
            false
        } catch (ignored: ArrayIndexOutOfBoundsException) {
            false
        }
    }
}