package com.common.res.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2021/09/12
 * desc   : 长按半透明松手恢复的 TextView
 */
class PressAlphaTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    override fun dispatchSetPressed(pressed: Boolean) {
        // 判断当前手指是否按下了
        alpha = if (pressed) {
            0.5f
        } else {
            1f
        }
    }
}