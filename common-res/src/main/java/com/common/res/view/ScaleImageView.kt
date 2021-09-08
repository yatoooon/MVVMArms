package com.common.res.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.common.res.R

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/08/02
 * desc   : 长按缩放松手恢复的 ImageView
 */
class ScaleImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var mScaleSize = 1.2f
    override fun dispatchSetPressed(pressed: Boolean) {
        // 判断当前手指是否按下了
        if (pressed) {
            scaleX = mScaleSize
            scaleY = mScaleSize
        } else {
            scaleX = 1f
            scaleY = 1f
        }
    }

    fun setScaleSize(size: Float) {
        mScaleSize = size
    }

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.res_ScaleImageView)
        setScaleSize(array.getFloat(R.styleable.res_ScaleImageView_res_scaleRatio, mScaleSize))
        array.recycle()
    }
}