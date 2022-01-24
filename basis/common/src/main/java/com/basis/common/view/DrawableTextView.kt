package com.basis.common.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.basis.common.R

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2021/04/18
 * desc   : 支持限定 Drawable 大小的 TextView
 */
class DrawableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    private var mDrawableWidth: Int
    private var mDrawableHeight: Int

    /**
     * 限定 Drawable 大小
     */
    fun setDrawableSize(width: Int, height: Int) {
        mDrawableWidth = width
        mDrawableHeight = height
        if (!isAttachedToWindow) {
            return
        }
        refreshDrawablesSize()
    }

    /**
     * 限定 Drawable 宽度
     */
    fun setDrawableWidth(width: Int) {
        mDrawableWidth = width
        if (!isAttachedToWindow) {
            return
        }
        refreshDrawablesSize()
    }

    /**
     * 限定 Drawable 高度
     */
    fun setDrawableHeight(height: Int) {
        mDrawableHeight = height
        if (!isAttachedToWindow) {
            return
        }
        refreshDrawablesSize()
    }

    override fun setCompoundDrawables(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        super.setCompoundDrawables(left, top, right, bottom)
        if (!isAttachedToWindow) {
            return
        }
        refreshDrawablesSize()
    }

    override fun setCompoundDrawablesRelative(
        start: Drawable?,
        top: Drawable?,
        end: Drawable?,
        bottom: Drawable?
    ) {
        super.setCompoundDrawablesRelative(start, top, end, bottom)
        if (!isAttachedToWindow) {
            return
        }
        refreshDrawablesSize()
    }

    /**
     * 刷新 Drawable 列表大小
     */
    private fun refreshDrawablesSize() {
        if (mDrawableWidth == 0 || mDrawableHeight == 0) {
            return
        }
        var compoundDrawables = compoundDrawables
        if (compoundDrawables[0] != null || compoundDrawables[1] != null) {
            super.setCompoundDrawables(
                limitDrawableSize(compoundDrawables[0]),
                limitDrawableSize(compoundDrawables[1]),
                limitDrawableSize(compoundDrawables[2]),
                limitDrawableSize(compoundDrawables[3])
            )
            return
        }
        compoundDrawables = compoundDrawablesRelative
        super.setCompoundDrawablesRelative(
            limitDrawableSize(compoundDrawables[0]),
            limitDrawableSize(compoundDrawables[1]),
            limitDrawableSize(compoundDrawables[2]),
            limitDrawableSize(compoundDrawables[3])
        )
    }

    /**
     * 重新限定 Drawable 宽高
     */
    private fun limitDrawableSize(drawable: Drawable?): Drawable? {
        if (drawable == null) {
            return null
        }
        if (mDrawableWidth == 0 || mDrawableHeight == 0) {
            return drawable
        }
        drawable.setBounds(0, 0, mDrawableWidth, mDrawableHeight)
        return drawable
    }

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.res_DrawableTextView)
        mDrawableWidth = array.getDimensionPixelSize(R.styleable.res_DrawableTextView_res_drawableWidth, 0)
        mDrawableHeight =
            array.getDimensionPixelSize(R.styleable.res_DrawableTextView_res_drawableHeight, 0)
        array.recycle()
        refreshDrawablesSize()
    }
}