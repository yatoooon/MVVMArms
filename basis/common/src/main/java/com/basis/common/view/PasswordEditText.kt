package com.basis.common.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
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
 * time   : 2019/08/25
 * desc   : 密码隐藏显示 EditText
 */
class PasswordEditText @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : RegexEditText(
    context!!, attrs, defStyleAttr
), OnTouchListener, OnFocusChangeListener, TextWatcher {
    private var mCurrentDrawable: Drawable
    private val mVisibleDrawable: Drawable
    private val mInvisibleDrawable: Drawable
    private var mTouchListener: OnTouchListener? = null
    private var mFocusChangeListener: OnFocusChangeListener? = null
    private fun setDrawableVisible(visible: Boolean) {
        if (mCurrentDrawable.isVisible == visible) {
            return
        }
        mCurrentDrawable.setVisible(visible, false)
        val drawables = compoundDrawablesRelative
        setCompoundDrawablesRelative(
            drawables[0],
            drawables[1],
            if (visible) mCurrentDrawable else null,
            drawables[3]
        )
    }

    private fun refreshDrawableStatus() {
        val drawables = compoundDrawablesRelative
        setCompoundDrawablesRelative(
            drawables[0],
            drawables[1],
            mCurrentDrawable,
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
            touchDrawable = x > width - mCurrentDrawable.intrinsicWidth - paddingEnd &&
                    x < width - paddingEnd
        } else if (layoutDirection == LAYOUT_DIRECTION_RTL) {
            // 从右往左
            touchDrawable = x > paddingStart &&
                    x < paddingStart + mCurrentDrawable.intrinsicWidth
        }
        if (mCurrentDrawable.isVisible && touchDrawable) {
            if (event.action == MotionEvent.ACTION_UP) {
                if (mCurrentDrawable === mVisibleDrawable) {
                    mCurrentDrawable = mInvisibleDrawable
                    // 密码可见
                    transformationMethod = HideReturnsTransformationMethod.getInstance()
                    refreshDrawableStatus()
                } else if (mCurrentDrawable === mInvisibleDrawable) {
                    mCurrentDrawable = mVisibleDrawable
                    // 密码不可见
                    transformationMethod = PasswordTransformationMethod.getInstance()
                    refreshDrawableStatus()
                }
                val editable = text
                if (editable != null) {
                    setSelection(editable.toString().length)
                }
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
        mVisibleDrawable =
            DrawableCompat.wrap(
                ContextCompat.getDrawable(
                    context!!,
                    R.drawable.res_password_off_ic
                )!!
            )
        mVisibleDrawable.setBounds(
            0,
            0,
            mVisibleDrawable.intrinsicWidth,
            mVisibleDrawable.intrinsicHeight
        )
        mInvisibleDrawable =
            DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.res_password_on_ic)!!)
        mInvisibleDrawable.setBounds(
            0,
            0,
            mInvisibleDrawable.intrinsicWidth,
            mInvisibleDrawable.intrinsicHeight
        )
        mCurrentDrawable = mVisibleDrawable

        // 密码不可见
        addInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
        if (inputRegex == null) {
            // 密码输入规则
            inputRegex = REGEX_NONNULL
        }


        setDrawableVisible(false)
        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
        super.addTextChangedListener(this)
    }
}