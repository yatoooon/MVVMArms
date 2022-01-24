package com.basis.common.layout

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.CollapsingToolbarLayout

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 支持监听渐变的 CollapsingToolbarLayout
 */
class XCollapsingToolbarLayout : CollapsingToolbarLayout {
    /** 渐变监听  */
    private var mListener: OnScrimsListener? = null
    /**
     * 获取当前的渐变状态
     */
    /** 当前渐变状态  */
    public var tagScrimsShown = false

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    )

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    )

    override fun setScrimsShown(shown: Boolean, animate: Boolean) {
        super.setScrimsShown(shown, true)
        // 判断渐变状态是否改变了
        if (tagScrimsShown == shown) {
            return
        }
        // 如果是就记录并且回调监听器
        tagScrimsShown = shown
        if (mListener == null) {
            return
        }
        mListener!!.onScrimsStateChange(this, tagScrimsShown)
    }

    /**
     * 设置CollapsingToolbarLayout渐变监听
     */
    fun setOnScrimsListener(listener: OnScrimsListener?) {
        mListener = listener
    }

    /**
     * CollapsingToolbarLayout渐变监听器
     */
    interface OnScrimsListener {
        /**
         * 渐变状态变化
         *
         * @param shown         渐变开关
         */
        fun onScrimsStateChange(layout: XCollapsingToolbarLayout?, shown: Boolean)
    }
}