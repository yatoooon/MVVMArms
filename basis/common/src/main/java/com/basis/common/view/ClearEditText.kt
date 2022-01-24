package com.basis.common.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.View.OnTouchListener
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.basis.common.R

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 带清除按钮的 EditText
 */
class ClearEditText @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : RegexEditText(
    context!!, attrs, defStyleAttr
), OnTouchListener, OnFocusChangeListener, TextWatcher {
    private val mClearDrawable: Drawable
    private var mTouchListener: OnTouchListener? = null
    private var mFocusChangeListener: OnFocusChangeListener? = null
    private fun setDrawableVisible(visible: Boolean) {
        if (mClearDrawable.isVisible == visible) {
            return
        }
        mClearDrawable.setVisible(visible, false)
        val drawables = compoundDrawablesRelative
        setCompoundDrawablesRelative(
            drawables[0],
            drawables[1],
            if (visible) mClearDrawable else null,
            drawables[3]
        )
    }

    override fun setOnFocusChangeListener(onFocusChangeListener: OnFocusChangeListener?) {
        mFocusChangeListener = onFocusChangeListener
    }

    override fun setOnTouchListener(onTouchListener: OnTouchListener?) {
        mTouchListener = onTouchListener
    }

    /**
     * [OnFocusChangeListener]
     */
    override fun onFocusChange(view: View, hasFocus: Boolean) {
        if (hasFocus && text != null) {
            setDrawableVisible(text!!.length > 0)
        } else {
            setDrawableVisible(false)
        }
        if (mFocusChangeListener != null) {
            mFocusChangeListener!!.onFocusChange(view, hasFocus)
        }
    }

    /**
     * [OnTouchListener]
     */
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val x = event.x.toInt()

        // 是否触摸了 Drawable
        var touchDrawable = false
        // 获取布局方向
        val layoutDirection = layoutDirection
        if (layoutDirection == LAYOUT_DIRECTION_LTR) {
            // 从左往右
            touchDrawable = x > width - mClearDrawable.intrinsicWidth - paddingEnd &&
                    x < width - paddingEnd
        } else if (layoutDirection == LAYOUT_DIRECTION_RTL) {
            // 从右往左
            touchDrawable = x > paddingStart &&
                    x < paddingStart + mClearDrawable.intrinsicWidth
        }
        if (mClearDrawable.isVisible && touchDrawable) {
            if (event.action == MotionEvent.ACTION_UP) {
                setText("")
            }
            return true
        }
        return mTouchListener != null && mTouchListener!!.onTouch(view, event)
    }

    /**
     * [TextWatcher]
     */
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (isFocused) {
            setDrawableVisible(s.length > 0)
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable) {}

    init {
        mClearDrawable =
            DrawableCompat.wrap(ContextCompat.getDrawable(context!!, R.drawable.res_input_delete_ic)!!)
        mClearDrawable.setBounds(
            0,
            0,
            mClearDrawable.intrinsicWidth,
            mClearDrawable.intrinsicHeight
        )
        setDrawableVisible(false)
        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
        super.addTextChangedListener(this)
    }
}