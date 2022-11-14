package com.common.res.utils

import android.view.View

/**
 * 批量设置控件点击事件。
 *
 * @param v 点击的控件
 * @param block 处理点击事件回调代码块
 */


//上次点击view
private var viewToString = ""
private var viewHashCode = ""

// 上次点击时间
private var lastClickTime: Long = 0L

// 两次点击间隔时间（毫秒）
private const val FAST_CLICK_DELAY_TIME = 800
fun bindViewClickListener(vararg v: View?, block: View.() -> Unit) {
    v.forEach {
        it?.setOnClickListener { view ->
            if (view.toString() == viewToString && view.hashCode()
                    .toString() == viewHashCode && System.currentTimeMillis() - lastClickTime <= FAST_CLICK_DELAY_TIME
            ) {
                return@setOnClickListener
            }
            lastClickTime = System.currentTimeMillis()
            view?.block()
        }
    }
}



