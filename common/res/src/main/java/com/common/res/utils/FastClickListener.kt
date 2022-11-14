package com.common.res.utils

import android.os.SystemClock
import android.view.View

/**
 * @param clickTimes 连续点击多少次才触发点击事件
 * @param clickInterval 快速点击的间隔时长小于多久才算有效
 */
@Suppress("unused")
abstract class FastClickListener(
    private val clickTimes: Int,
    private val clickInterval: Long = 200L
) :
    View.OnClickListener {
    //上一次点击的时间
    private var lastClickTimes = 0L

    //已经点击的次数
    private var alreadyClickTimes = 0

    override fun onClick(view: View) {
        if (lastClickTimes == 0L) {
            lastClickTimes = SystemClock.elapsedRealtime()
            if (isClickTimesFill()) {
                fastClick(view)
                lastClickTimes = 0L
            }
        } else {
            if (SystemClock.elapsedRealtime() - lastClickTimes < clickInterval) {
                //点击有效
                lastClickTimes = SystemClock.elapsedRealtime()
                if (isClickTimesFill()) {
                    fastClick(view)
                    lastClickTimes = 0L
                }
            } else {
                //点击间隔太久,重新计算
                lastClickTimes = SystemClock.elapsedRealtime()
                alreadyClickTimes = 1
            }
        }
    }

    private fun isClickTimesFill(): Boolean {
        alreadyClickTimes++
        return if (clickTimes <= alreadyClickTimes) {
            alreadyClickTimes = 0
            true
        } else false
    }

    abstract fun fastClick(view: View)

}

fun View.setFastClickListener(clickTimes: Int, func: (v: View) -> Unit) {
    setOnClickListener(object : FastClickListener(clickTimes) {
        override fun fastClick(view: View) {
            func(view)
        }
    })
}