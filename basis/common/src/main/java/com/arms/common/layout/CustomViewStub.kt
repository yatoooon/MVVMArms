package com.arms.common.layout

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.arms.res.R

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/07/06
 * desc   : 自定义 ViewStub（原生 ViewStub 的缺点：继承至 View，不支持 findViewById、动态添加和移除 View、监听显示隐藏）
 */
class CustomViewStub(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private var mListener: com.arms.common.layout.CustomViewStub.OnViewStubListener? = null
    private val mLayoutResource: Int

    /**
     * 获取填充的 View
     */
    private var inflateView: View? = null

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : this(context, attrs, defStyleAttr, 0)

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (inflateView == null && visibility != GONE) {
            inflateView = LayoutInflater.from(context).inflate(mLayoutResource, this, false)
            val layoutParams = inflateView!!.layoutParams as LayoutParams
            layoutParams.width = getLayoutParams().width
            layoutParams.height = getLayoutParams().height
            if (layoutParams.gravity == LayoutParams.UNSPECIFIED_GRAVITY) {
                layoutParams.gravity = Gravity.CENTER
            }
            inflateView!!.layoutParams = layoutParams
            addView(inflateView)
            if (mListener != null) {
                mListener!!.onInflate(this, inflateView)
            }
        }
        if (mListener != null) {
            mListener!!.onVisibility(this, visibility)
        }
    }

    /**
     * 设置显示状态（避免 setVisibility 导致的无限递归）
     */
    fun setCustomVisibility(visibility: Int) {
        super.setVisibility(visibility)
    }

    /**
     * 设置监听器
     */
    fun setOnViewStubListener(listener: com.arms.common.layout.CustomViewStub.OnViewStubListener?) {
        mListener = listener
    }

    interface OnViewStubListener {
        /**
         * 布局填充回调（可在此中做 View 初始化）
         *
         * @param stub              当前 ViewStub 对象
         * @param inflatedView      填充布局对象
         */
        fun onInflate(stub: com.arms.common.layout.CustomViewStub?, inflatedView: View?)

        /**
         * 可见状态改变（可在此中做 View 更新）
         *
         * @param stub              当前 ViewStub 对象
         * @param visibility        可见状态参数改变
         */
        fun onVisibility(stub: com.arms.common.layout.CustomViewStub?, visibility: Int)
    }

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.res_CustomViewStub)
        mLayoutResource = array.getResourceId(R.styleable.res_CustomViewStub_android_layout, 0)
        array.recycle()

        // 隐藏自己
        visibility = GONE
    }
}