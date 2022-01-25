package com.arms.common.view

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2021/09/17
 * desc   : 带悬浮动画的按钮
 */
class FloatActionButton : AppCompatImageView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    /**
     * 显示
     */
    fun show() {
        removeCallbacks(mHideRunnable)
        postDelayed(mShowRunnable, (ANIM_TIME * 2).toLong())
    }

    /**
     * 隐藏
     */
    fun hide() {
        removeCallbacks(mShowRunnable)
        post(mHideRunnable)
    }

    /**
     * 显示悬浮球动画
     */
    private val mShowRunnable = Runnable {
        if (visibility == INVISIBLE) {
            visibility = VISIBLE
        }
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = ANIM_TIME.toLong()
        valueAnimator.addUpdateListener { animation: ValueAnimator ->
            val value = animation.animatedValue as Float
            alpha = value
            scaleX = value
            scaleY = value
        }
        valueAnimator.start()
    }

    /**
     * 隐藏悬浮球动画
     */
    private val mHideRunnable = label@ Runnable {
        if (visibility == INVISIBLE) {
            return@Runnable
        }
        val valueAnimator = ValueAnimator.ofFloat(1f, 0f)
        valueAnimator.duration = ANIM_TIME.toLong()
        valueAnimator.addUpdateListener { animation: ValueAnimator ->
            val value = animation.animatedValue as Float
            alpha = value
            scaleX = value
            scaleY = value
            if (value == 0f) {
                visibility = INVISIBLE
            }
        }
        valueAnimator.start()
    }

    companion object {
        /** 动画显示时长  */
        private const val ANIM_TIME = 300
    }
}