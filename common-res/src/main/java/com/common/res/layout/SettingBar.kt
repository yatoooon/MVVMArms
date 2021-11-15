package com.common.res.layout

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
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
class SettingBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    /**
     * 获取主布局
     */
    val mainLayout: LinearLayout

    /**
     * 获取左 TextView
     */
    val leftView: TextView

    /**
     * 获取右 TextView
     */
    val rightView: TextView

    /**
     * 获取分割线
     */
    val lineView: View

    /** 图标着色器  */
    private var mLeftDrawableTint = 0
    private var mRightDrawableTint = 0

    /** 图标显示大小  */
    private var mLeftDrawableSize = 0
    private var mRightDrawableSize = 0

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : this(context, attrs, defStyleAttr, 0) {
    }

    /**
     * 设置左边的文本
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
    fun setLeftTextHint(@StringRes id: Int): SettingBar {
        return setLeftTextHint(resources.getString(id))
    }

    fun setLeftTextHint(hint: CharSequence?): SettingBar {
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
    fun setRightTextHint(@StringRes id: Int): SettingBar {
        return setRightTextHint(resources.getString(id))
    }

    fun setRightTextHint(hint: CharSequence?): SettingBar {
        rightView.hint = hint
        return this
    }

    /**
     * 设置左边的图标
     */
    fun setLeftDrawable(@DrawableRes id: Int): SettingBar {
        setLeftDrawable(ContextCompat.getDrawable(context, id))
        return this
    }

    fun setLeftDrawable(drawable: Drawable?): SettingBar {
        leftView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        setLeftDrawableSize(mLeftDrawableSize)
        setLeftDraawbleTint(mLeftDrawableTint)
        return this
    }

    val leftDrawable: Drawable
        get() = leftView.compoundDrawables[0]

    /**
     * 设置右边的图标
     */
    fun setRightDrawable(@DrawableRes id: Int): SettingBar {
        setRightDrawable(ContextCompat.getDrawable(context, id))
        return this
    }

    fun setRightDrawable(drawable: Drawable?): SettingBar {
        rightView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        setRightDrawableSize(mRightDrawableSize)
        setRightDrawableTint(mRightDrawableTint)
        return this
    }

    val rightDrawable: Drawable
        get() = rightView.compoundDrawables[2]

    /**
     * 设置左边的图标间距
     */
    fun setLeftDrawablePadding(padding: Int): SettingBar {
        leftView.compoundDrawablePadding = padding
        return this
    }

    /**
     * 设置右边的图标间距
     */
    fun setRightDrawablePadding(padding: Int): SettingBar {
        rightView.compoundDrawablePadding = padding
        return this
    }

    /**
     * 设置左边的图标大小
     */
    fun setLeftDrawableSize(size: Int): SettingBar {
        mLeftDrawableSize = size
        val drawable = leftDrawable
        if (drawable != null) {
            if (size > 0) {
                drawable.setBounds(0, 0, size, size)
            } else {
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            }
            leftView.setCompoundDrawables(drawable, null, null, null)
        }
        return this
    }

    /**
     * 设置右边的图标大小
     */
    fun setRightDrawableSize(size: Int): SettingBar {
        mRightDrawableSize = size
        val drawable = rightDrawable
        if (drawable != null) {
            if (size > 0) {
                drawable.setBounds(0, 0, size, size)
            } else {
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            }
            rightView.setCompoundDrawables(null, null, drawable, null)
        }
        return this
    }

    /**
     * 设置左边的图标着色器
     */
    fun setLeftDraawbleTint(color: Int): SettingBar {
        mLeftDrawableTint = color
        val drawable = leftDrawable
        if (drawable != null && color != NO_COLOR) {
            drawable.mutate()
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
        return this
    }

    /**
     * 设置右边的图标着色器
     */
    fun setRightDrawableTint(color: Int): SettingBar {
        mRightDrawableTint = color
        val drawable = rightDrawable
        if (drawable != null && color != NO_COLOR) {
            drawable.mutate()
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
        return this
    }

    /**
     * 设置左边的文本颜色
     */
    fun setLeftTextColor(@ColorInt color: Int): SettingBar {
        leftView.setTextColor(color)
        return this
    }

    /**
     * 设置右边的文本颜色
     */
    fun setRightTextColor(@ColorInt color: Int): SettingBar {
        rightView.setTextColor(color)
        return this
    }

    /**
     * 设置左边的文字大小
     */
    fun setLeftTextSize(unit: Int, size: Float): SettingBar {
        leftView.setTextSize(unit, size)
        return this
    }

    /**
     * 设置右边的文字大小
     */
    fun setRightTextSize(unit: Int, size: Float): SettingBar {
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
        var params: LayoutParams? = lineView.layoutParams as LayoutParams
        if (params == null) {
            params = generateDefaultLayoutParams()
        }
        params!!.height = size
        lineView.layoutParams = params
        return this
    }

    /**
     * 设置分割线边界
     */
    fun setLineMargin(margin: Int): SettingBar {
        var params: LayoutParams? = lineView.layoutParams as LayoutParams
        if (params == null) {
            params = generateDefaultLayoutParams()
        }
        params!!.leftMargin = margin
        params.rightMargin = margin
        lineView.layoutParams = params
        return this
    }

    companion object {
        /** 无色值  */
        const val NO_COLOR = Color.TRANSPARENT
    }

    init {
        mainLayout = LinearLayout(getContext())
        leftView = TextView(getContext())
        rightView = TextView(getContext())
        lineView = View(getContext())
        mainLayout.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT,
            Gravity.CENTER_VERTICAL
        )
        val leftParams =
            LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        leftParams.gravity = Gravity.CENTER_VERTICAL
        leftView.layoutParams = leftParams
        val rightParams = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT)
        rightParams.gravity = Gravity.CENTER_VERTICAL
        rightParams.weight = 1f
        rightView.layoutParams = rightParams
        lineView.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, 1, Gravity.BOTTOM)
        leftView.gravity = Gravity.START or Gravity.CENTER_VERTICAL
        rightView.gravity = Gravity.END or Gravity.CENTER_VERTICAL
        leftView.isSingleLine = true
        rightView.isSingleLine = true
        leftView.ellipsize = TextUtils.TruncateAt.END
        rightView.ellipsize = TextUtils.TruncateAt.END
        leftView.setLineSpacing(
            resources.getDimension(R.dimen.res_dp_5),
            leftView.lineSpacingMultiplier
        )
        rightView.setLineSpacing(
            resources.getDimension(R.dimen.res_dp_5),
            rightView.lineSpacingMultiplier
        )
        leftView.setPaddingRelative(
            resources.getDimension(R.dimen.res_dp_15).toInt(),
            resources.getDimension(R.dimen.res_dp_12).toInt(),
            resources.getDimension(R.dimen.res_dp_15).toInt(),
            resources.getDimension(R.dimen.res_dp_12).toInt()
        )
        rightView.setPaddingRelative(
            resources.getDimension(R.dimen.res_dp_15).toInt(),
            resources.getDimension(R.dimen.res_dp_12).toInt(),
            resources.getDimension(R.dimen.res_dp_15).toInt(),
            resources.getDimension(R.dimen.res_dp_12).toInt()
        )
        val array = getContext().obtainStyledAttributes(attrs, R.styleable.res_SettingBar)

        // 文本设置
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_leftText)) {
            setLeftText(array.getString(R.styleable.res_SettingBar_res_bar_leftText))
        }
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_rightText)) {
            setRightText(array.getString(R.styleable.res_SettingBar_res_bar_rightText))
        }

        // 提示设置
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_leftTextHint)) {
            setLeftTextHint(array.getString(R.styleable.res_SettingBar_res_bar_leftTextHint))
        }
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_rightTextHint)) {
            setRightTextHint(array.getString(R.styleable.res_SettingBar_res_bar_rightTextHint))
        }

        // 图标显示的大小
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_leftDrawableSize)) {
            setLeftDrawableSize(
                array.getDimensionPixelSize(
                    R.styleable.res_SettingBar_res_bar_leftDrawableSize,
                    0
                )
            )
        }
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_rightDrawableSize)) {
            setRightDrawableSize(
                array.getDimensionPixelSize(
                    R.styleable.res_SettingBar_res_bar_rightDrawableSize,
                    0
                )
            )
        }

        // 图标着色器
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_leftDrawableTint)) {
            setLeftDraawbleTint(
                array.getColor(
                    R.styleable.res_SettingBar_res_bar_leftDrawableTint,
                    NO_COLOR
                )
            )
        }
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_rightDrawableTint)) {
            setRightDrawableTint(
                array.getColor(
                    R.styleable.res_SettingBar_res_bar_rightDrawableTint,
                    NO_COLOR
                )
            )
        }

        // 图标和文字之间的间距
        setLeftDrawablePadding(
            if (array.hasValue(R.styleable.res_SettingBar_res_bar_leftDrawablePadding)) array.getDimensionPixelSize(
                R.styleable.res_SettingBar_res_bar_leftDrawablePadding,
                0
            ) else resources.getDimension(R.dimen.res_dp_10)
                .toInt()
        )
        setRightDrawablePadding(
            if (array.hasValue(R.styleable.res_SettingBar_res_bar_rightDrawablePadding)) array.getDimensionPixelSize(
                R.styleable.res_SettingBar_res_bar_rightDrawablePadding,
                0
            ) else resources.getDimension(R.dimen.res_dp_10)
                .toInt()
        )

        // 图标设置
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_leftDrawable)) {
            setLeftDrawable(array.getDrawable(R.styleable.res_SettingBar_res_bar_leftDrawable))
        }
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_rightDrawable)) {
            setRightDrawable(array.getDrawable(R.styleable.res_SettingBar_res_bar_rightDrawable))
        }

        // 文字颜色设置
        setLeftTextColor(
            array.getColor(
                R.styleable.res_SettingBar_res_bar_leftTextColor,
                ContextCompat.getColor(getContext(), R.color.res_black80)
            )
        )
        setRightTextColor(
            array.getColor(
                R.styleable.res_SettingBar_res_bar_rightTextColor,
                ContextCompat.getColor(getContext(), R.color.res_black60)
            )
        )

        // 文字大小设置
        setLeftTextSize(
            TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(
                R.styleable.res_SettingBar_res_bar_leftTextSize,
                resources.getDimension(R.dimen.res_sp_15)
                    .toInt()
            ).toFloat()
        )
        setRightTextSize(
            TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(
                R.styleable.res_SettingBar_res_bar_rightTextSize,
                resources.getDimension(R.dimen.res_sp_14)
                    .toInt()
            ).toFloat()
        )

        // 分割线设置
        if (array.hasValue(R.styleable.res_SettingBar_res_bar_lineDrawable)) {
            setLineDrawable(array.getDrawable(R.styleable.res_SettingBar_res_bar_lineDrawable))
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
            setLineMargin(
                array.getDimensionPixelSize(
                    R.styleable.res_SettingBar_res_bar_lineMargin,
                    0
                )
            )
        }
        if (background == null) {
            val drawable = StateListDrawable()
            drawable.addState(
                intArrayOf(android.R.attr.state_pressed),
                ColorDrawable(ContextCompat.getColor(getContext(), R.color.res_black5))
            )
            drawable.addState(
                intArrayOf(android.R.attr.state_selected),
                ColorDrawable(ContextCompat.getColor(getContext(), R.color.res_black5))
            )
            drawable.addState(
                intArrayOf(android.R.attr.state_focused),
                ColorDrawable(ContextCompat.getColor(getContext(), R.color.res_black5))
            )
            drawable.addState(
                intArrayOf(),
                ColorDrawable(ContextCompat.getColor(getContext(), R.color.res_white))
            )
            background = drawable

            // 必须要设置可点击，否则点击屏幕任何角落都会触发按压事件
            isFocusable = true
            isClickable = true
        }
        array.recycle()
        mainLayout.addView(leftView)
        mainLayout.addView(rightView)
        addView(mainLayout, 0)
        addView(lineView, 1)
    }
}