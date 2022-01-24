package com.arms.common.layout

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.arms.common.R

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/04/18
 * desc   : 状态布局（网络错误，异常错误，空数据）
 */
class StatusLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    /** 主布局  */
    private var mMainLayout: ViewGroup? = null

    /** 提示图标  */
    private var mLottieView: LottieAnimationView? = null

    /** 提示文本  */
    private var mTextView: TextView? = null

    /** 重试按钮  */
    private var mRetryView: TextView? = null

    /** 重试监听  */
    private var mListener: OnRetryListener? = null

    /**
     * 显示
     */
    fun show() {
        if (mMainLayout == null) {
            //初始化布局
            initLayout()
        }
        if (isShow) {
            return
        }
        mRetryView!!.visibility =
            if (mListener == null) INVISIBLE else VISIBLE
        // 显示布局
        mMainLayout!!.visibility = VISIBLE
    }

    /**
     * 隐藏
     */
    fun hide() {
        if (mMainLayout == null || !isShow) {
            return
        }
        //隐藏布局
        mMainLayout!!.visibility = INVISIBLE
    }

    /**
     * 是否显示了
     */
    val isShow: Boolean
        get() = mMainLayout != null && mMainLayout!!.visibility == VISIBLE

    /**
     * 设置提示图标，请在show方法之后调用
     */
    fun setIcon(@DrawableRes id: Int) {
        setIcon(ContextCompat.getDrawable(context, id))
    }

    fun setIcon(drawable: Drawable?) {
        if (mLottieView == null) {
            return
        }
        if (mLottieView!!.isAnimating) {
            mLottieView!!.cancelAnimation()
        }
        mLottieView!!.setImageDrawable(drawable)
    }

    /**
     * 设置提示动画
     */
    fun setAnimResource(@RawRes id: Int) {
        if (mLottieView == null) {
            return
        }
        mLottieView!!.setAnimation(id)
        if (!mLottieView!!.isAnimating) {
            mLottieView!!.playAnimation()
        }
    }

    /**
     * 设置提示文本，请在show方法之后调用
     */
    fun setHint(@StringRes id: Int) {
        setHint(resources.getString(id))
    }

    fun setHint(text: CharSequence?) {
        var text = text
        if (mTextView == null) {
            return
        }
        if (text == null) {
            text = ""
        }
        mTextView!!.text = text
    }

    /**
     * 初始化提示的布局
     */
    private fun initLayout() {
        mMainLayout = LayoutInflater.from(context)
            .inflate(R.layout.res_widget_status_layout, this, false) as ViewGroup
        mLottieView = mMainLayout!!.findViewById(R.id.iv_status_icon)
        mTextView = mMainLayout!!.findViewById(R.id.iv_status_text)
        mRetryView = mMainLayout!!.findViewById(R.id.iv_status_retry)
        if (mMainLayout!!.background == null) {
            // 默认使用 windowBackground 作为背景
            val typedArray =
                context.obtainStyledAttributes(intArrayOf(android.R.attr.windowBackground))
            mMainLayout!!.background = typedArray.getDrawable(0)
            mMainLayout!!.isClickable = true
            typedArray.recycle()
        }
        mRetryView?.setOnClickListener(mClickWrapper)
        addView(mMainLayout)
    }

    /**
     * 设置重试监听器
     */
    fun setOnRetryListener(listener: OnRetryListener?) {
        mListener = listener
        if (isShow) {
            mRetryView!!.visibility =
                if (mListener == null) INVISIBLE else VISIBLE
        }
    }

    /**
     * 点击事件包装类
     */
    private val mClickWrapper = OnClickListener {
        if (mListener == null) {
            return@OnClickListener
        }
        mListener!!.onRetry(this@StatusLayout)
    }

    /**
     * 重试监听器
     */
    interface OnRetryListener {
        /**
         * 点击了重试
         */
        fun onRetry(layout: StatusLayout?)
    }
}