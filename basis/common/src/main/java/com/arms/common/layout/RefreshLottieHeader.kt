package com.arms.common.layout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.widget.LinearLayout
import com.airbnb.lottie.LottieAnimationView
import com.arms.common.R
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.constant.SpinnerStyle

class RefreshLottieHeader(context: Context) : LinearLayout(context), RefreshHeader {
    private var mAnimationView: LottieAnimationView? = null
    override fun getView(): View {
        return this
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }

    override fun setPrimaryColors(vararg colors: Int) {}
    override fun onInitialized(kernel: RefreshKernel, height: Int, extendHeight: Int) {}
    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
        if (isDragging && !mAnimationView!!.isAnimating) {
            mAnimationView!!.playAnimation()
        }
        if (!isDragging && offset == 0 && mAnimationView!!.isAnimating) {
            mAnimationView!!.cancelAnimation()
        }
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {}
    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {}
    override fun onStartAnimator(layout: RefreshLayout, height: Int, extendHeight: Int) {}
    override fun onFinish(layout: RefreshLayout, success: Boolean): Int {
        mAnimationView!!.cancelAnimation()
        return 100
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {}
    private fun initView(context: Context) {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.res_layout_refresh_header, this)
        mAnimationView = view.findViewById<View>(R.id.animation_view) as LottieAnimationView
    }

    fun setAnimationViewJson(animName: String?) {
        mAnimationView!!.setAnimation(animName)
    }

    fun setAnimationViewJson(anim: Animation?) {
        mAnimationView!!.animation = anim
    }

    init {
        initView(context)
    }
}