package com.common.res.layout

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.common.res.R

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/01/23
 * desc   : 设置条自定义控件
 */
class SettingBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    /**
     * 获取主布局
     */
    val mainLayout: LinearLayout

    /**
     * 获取左标题
     */
    val leftView: TextView

    /**
     * 获取右标题
     */
    val rightView: TextView

    /**
     * 获取分割线
     */
    val lineView: View

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : this(context, attrs, defStyleAttr, 0)

    /**
     * 设置左边的标题
     */
    fun setLeftText(@StringRes id: Int): SettingBar {
        return setLeftText(resources.getString(id))
    }

    fun setLeftText(text: CharSequence?): SettingBar {
        leftView.text = text
        return this
    }

    val leftText: CharSequence
        get() = leftView.text

    /**
     * 设置左边的提示
     */
    fun setLeftHint(@StringRes id: Int): SettingBar {
        return setLeftHint(resources.getString(id))
    }

    fun setLeftHint(hint: CharSequence?): SettingBar {
        leftView.hint = hint
        return this
    }

    /**
     * 设置右边的标题
     */
    fun setRightText(@StringRes id: Int): SettingBar {
        setRightText(resources.getString(id))
        return this
    }

    fun setRightText(text: CharSequence?): SettingBar {
        rightView.text = text
        return this
    }

    val rightText: CharSequence
        get() = rightView.text

    /**
     * 设置右边的提示
     */
    fun setRightHint(@StringRes id: Int): SettingBar {
        return setRightHint(resources.getString(id))
    }

    fun setRightHint(hint: CharSequence?): SettingBar {
        rightView.hint = hint
        return this
    }

    /**
     * 设置左边的图标
     */
    fun setLeftIcon(@DrawableRes id: Int): SettingBar {
        setLeftIcon(ContextCompat.getDrawable(context, id))
        return this
    }

    fun setLeftIcon(drawable: Drawable?): SettingBar {
        leftView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        return this
    }

    val leftIcon: Drawable
        get() = leftView.compoundDrawables[0]

    /**
     * 设置右边的图标
     */
    fun setRightIcon(@DrawableRes id: Int): SettingBar {
        setRightIcon(ContextCompat.getDrawable(context, id))
        return this
    }

    fun setRightIcon(drawable: Drawable?): SettingBar {
        rightView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        return this
    }

    val rightIcon: Drawable
        get() = rightView.compoundDrawables[2]

    /**
     * 设置左标题颜色
     */
    fun setLeftColor(@ColorInt color: Int): SettingBar {
        leftView.setTextColor(color)
        return this
    }

    /**
     * 设置右标题颜色
     */
    fun setRightColor(@ColorInt color: Int): SettingBar {
        rightView.setTextColor(color)
        return this
    }

    /**
     * 设置左标题的文本大小
     */
    fun setLeftSize(unit: Int, size: Float): SettingBar {
        leftView.setTextSize(unit, size)
        return this
    }

    /**
     * 设置右标题的文本大小
     */
    fun setRightSize(unit: Int, size: Float): SettingBar {
        rightView.setTextSize(unit, size)
        return this
    }

    /**
     * 设置分割线是否显示
     */
    fun setLineVisible(visible: Boolean): SettingBar {
        lineView.visibility = if (visible) VISIBLE else GONE
        return this
    }

    /**
     * 设置分割线的颜色
     */
    fun setLineColor(@ColorInt color: Int): SettingBar {
        return setLineDrawable(ColorDrawable(color))
    }

    fun setLineDrawable(drawable: Drawable?): SettingBar {
        lineView.background = drawable
        return this
    }

    /**
     * 设置分割线的大小
     */
    fun setLineSize(size: Int): SettingBar {
        val layoutParams = lineView.layoutParams
        layoutParams.height = size
        lineView.layoutParams = layoutParams
        return this
    }

    /**
     * 设置分割线边界
     */
    fun setLineMargin(margin: Int): SettingBar {
        val params = lineView.layoutParams as LayoutParams
        params.leftMargin = margin
        params.rightMargin = margin
        lineView.layoutParams = params
        return this
    }

    init {
        mainLayout = LinearLayout(getContext())
        leftView = TextView(getContext())
        rightView = TextView(getContext())
        lineView = View(getContext())
        leftView.gravity = Gravity.START or Gravity.CENTER_VERTICAL
        rightView.gravity = Gravity.END or Gravity.CENTER_VERTICAL
        leftView.isSingleLine = true
        rightView.isSingleLine = true
        leftView.ellipsize = TextUtils.TruncateAt.END
        rightView.ellipsize = TextUtils.TruncateAt.END
        leftView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics), leftView.lineSpacingMultiplier)
        rightView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics), rightView.lineSpacingMultiplier)
        leftView.setPaddingRelative(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, resources.displayMetrics).toInt(),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, resources.displayMetrics).toInt(),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, resources.displayMetrics).toInt(),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, resources.displayMetrics).toInt())
        rightView.setPaddingRelative(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, resources.displayMetrics).toInt(),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, resources.displayMetrics).toInt(),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, resources.displayMetrics).toInt(),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, resources.displayMetrics).toInt())
        leftView.compoundDrawablePadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics).toInt()
        rightView.compoundDrawablePadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics).toInt()
        val array = getContext().obtainStyledAttributes(attrs, R.styleable.res_SettingBar)

        // 文本设置
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_leftText)) {
            setLeftText(array.getString(R.styleable.res_SettingBar_res_bar_leftText))
        }
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_rightText)) {
            setRightText(array.getString(R.styleable.res_SettingBar_res_bar_rightText))
        }

        // 提示设置
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_leftHint)) {
            setLeftHint(array.getString(R.styleable.res_SettingBar_res_bar_leftHint))
        }
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_rightHint)) {
            setRightHint(array.getString(R.styleable.res_SettingBar_res_bar_rightHint))
        }

        // 图标设置
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_leftIcon)) {
            setLeftIcon(array.getDrawable(R.styleable.res_SettingBar_res_bar_leftIcon))
        }
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_rightIcon)) {
            setRightIcon(array.getDrawable(R.styleable.res_SettingBar_res_bar_rightIcon))
        }

        // 文字颜色设置
        setLeftColor(array.getColor(R.styleable.res_SettingBar_res_bar_leftColor, ContextCompat.getColor(getContext(), R.color.res_color_656565)))
        setRightColor(array.getColor(R.styleable.res_SettingBar_res_bar_rightColor, ContextCompat.getColor(getContext(), R.color.res_color_333333)))

        // 文字大小设置
        setLeftSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.res_SettingBar_res_bar_leftSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15f, resources.displayMetrics).toInt()).toFloat())
        setRightSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.res_SettingBar_res_bar_rightSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics).toInt()).toFloat())

        // 分割线设置
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_lineColor)) {
            setLineDrawable(array.getDrawable(R.styleable.res_SettingBar_res_bar_lineColor))
        } else {
            setLineDrawable(ColorDrawable(-0x131314))
        }
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_lineVisible)) {
            setLineVisible(array.getBoolean(R.styleable.res_SettingBar_res_bar_lineVisible, true))
        }
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_lineSize)) {
            setLineSize(array.getDimensionPixelSize(R.styleable.res_SettingBar_res_bar_lineSize, 0))
        }
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_lineMargin)) {
            setLineMargin(array.getDimensionPixelSize(R.styleable.res_SettingBar_res_bar_lineMargin, 0))
        }
        if (background == null) {
            val drawable = StateListDrawable()
            drawable.addState(intArrayOf(android.R.attr.state_pressed), ColorDrawable(ContextCompat.getColor(getContext(), R.color.res_color_656565)))
            drawable.addState(intArrayOf(android.R.attr.state_selected), ColorDrawable(ContextCompat.getColor(getContext(), R.color.res_color_656565)))
            drawable.addState(intArrayOf(android.R.attr.state_focused), ColorDrawable(ContextCompat.getColor(getContext(), R.color.res_color_656565)))
            drawable.addState(intArrayOf(), ColorDrawable(ContextCompat.getColor(getContext(), R.color.res_color_ffffff)))
            background = drawable

            // 必须要设置可点击，否则点击屏幕任何角落都会触发按压事件
            isFocusable = true
            isClickable = true
        }
        array.recycle()
        val leftParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        leftParams.gravity = Gravity.CENTER_VERTICAL
        mainLayout.addView(leftView, leftParams)
        val rightParams = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT)
        rightParams.gravity = Gravity.CENTER_VERTICAL
        rightParams.weight = 1f
        mainLayout.addView(rightView, rightParams)
        addView(mainLayout, 0, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL))
        addView(lineView, 1, LayoutParams(LayoutParams.MATCH_PARENT, 1, Gravity.BOTTOM))
    }
}