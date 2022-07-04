package com.common.res.layout

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 简单的 Layout（常用于自定义组合控件继承的基类，可以起到性能优化的作用）
 */
open class SimpleLayout : ViewGroup {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val count = childCount
        var maxHeight = 0
        var maxWidth = 0
        var childState = 0

        // 测量子 View
        for (i in 0 until count) {
            val child = getChildAt(i)
            // 被测量的子 View 不能是隐藏的
            if (child.visibility != GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
                val params = child.layoutParams as MarginLayoutParams
                maxWidth = Math.max(maxWidth, child.measuredWidth + params.leftMargin + params.rightMargin)
                maxHeight = Math.max(maxHeight, child.measuredHeight + params.topMargin + params.bottomMargin)
                childState = combineMeasuredStates(childState, child.measuredState)
            }
        }
        maxWidth += paddingLeft + paddingRight
        maxHeight += paddingTop + paddingBottom
        maxWidth = Math.max(maxWidth, suggestedMinimumWidth)
        maxHeight = Math.max(maxHeight, suggestedMinimumHeight)

        // 测量自身
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState shl MEASURED_HEIGHT_STATE_SHIFT))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // 遍历子 View
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            val params = child.layoutParams as MarginLayoutParams
            val left = params.leftMargin + paddingLeft
            val top = params.topMargin + paddingTop
            val right = left + child.measuredWidth + paddingRight + params.rightMargin
            val bottom = top + child.measuredHeight + paddingBottom + params.bottomMargin
            // 将子 View 放置到左上角的位置
            child.layout(left, top, right, bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    override fun generateLayoutParams(params: LayoutParams): LayoutParams {
        return MarginLayoutParams(params)
    }

    override fun checkLayoutParams(params: LayoutParams): Boolean {
        return params is MarginLayoutParams
    }

    override fun shouldDelayChildPressedState(): Boolean {
        return false
    }
}