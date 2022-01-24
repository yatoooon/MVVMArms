package com.arms.common.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.animation.AccelerateInterpolator
import androidx.annotation.FloatRange
import androidx.appcompat.widget.AppCompatButton
import com.arms.common.R

/**
 * author : Unstoppable & Android 轮子哥
 * github : https://github.com/Someonewow/SubmitButton
 * time   : 2016/12/31
 * desc   : 带提交动画按钮
 */
class SubmitButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatButton(context, attrs, defStyleAttr) {
    /** 当前按钮状态  */
    private var mButtonState = STATE_NONE

    /** 当前进度条样式  */
    private val mProgressStyle: Int
    private var mCurrentProgress = 0f

    /** View 宽高  */
    private var mViewWidth = 0
    private var mViewHeight = 0

    /** View 最大宽高  */
    private var mMaxWidth = 0
    private var mMaxHeight = 0

    /** 画布坐标原点  */
    private var mX = 0
    private var mY = 0

    /** 进度按钮的颜色  */
    private val mProgressColor: Int

    /** 成功按钮的颜色  */
    private val mSucceedColor: Int

    /** 失败按钮的颜色  */
    private val mErrorColor: Int
    private var mBackgroundPaint: Paint? = null
    private var mLoadingPaint: Paint? = null
    private var mResultPaint: Paint? = null
    private var mButtonPath: Path? = null
    private var mLoadPath: Path? = null
    private var mDstPath: Path? = null
    private var mPathMeasure: PathMeasure? = null
    private var mResultPath: Path? = null
    private var mCircleLeft: RectF? = null
    private var mCircleMid: RectF? = null
    private var mCircleRight: RectF? = null
    private var mLoadValue = 0f
    private var mSubmitAnim: ValueAnimator? = null
    private var mLoadingAnim: ValueAnimator? = null
    private var mResultAnim: ValueAnimator? = null

    /** 是否有结果  */
    private var mDoResult = false

    /** 是否成功了  */
    private var mSucceed = false
    private fun initPaint() {
        mBackgroundPaint = Paint()
        mLoadingPaint = Paint()
        mResultPaint = Paint()
        mButtonPath = Path()
        mLoadPath = Path()
        mResultPath = Path()
        mDstPath = Path()
        mCircleMid = RectF()
        mCircleLeft = RectF()
        mCircleRight = RectF()
        mPathMeasure = PathMeasure()
    }

    /**
     * 重置画笔
     */
    private fun resetPaint() {
        mBackgroundPaint!!.color = mProgressColor
        mBackgroundPaint!!.strokeWidth = 5f
        mBackgroundPaint!!.isAntiAlias = true
        mLoadingPaint!!.color = mProgressColor
        mLoadingPaint!!.style = Paint.Style.STROKE
        mLoadingPaint!!.strokeWidth = 9f
        mLoadingPaint!!.isAntiAlias = true
        mResultPaint!!.color = Color.WHITE
        mResultPaint!!.style = Paint.Style.STROKE
        mResultPaint!!.strokeWidth = 9f
        mResultPaint!!.strokeCap = Paint.Cap.ROUND
        mResultPaint!!.isAntiAlias = true
        mButtonPath!!.reset()
        mLoadPath!!.reset()
        mResultPath!!.reset()
        mDstPath!!.reset()
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        // 当前必须不是在动画执行过程中
        if (mButtonState != STATE_LOADING) {
            mViewWidth = width - 10
            mViewHeight = height - 10
            mX = (width * 0.5).toInt()
            mY = (height * 0.5).toInt()
            mMaxWidth = mViewWidth
            mMaxHeight = mViewHeight
        }
    }

    override fun onDraw(canvas: Canvas) {
        when (mButtonState) {
            STATE_NONE -> super.onDraw(canvas)
            STATE_SUBMIT, STATE_LOADING -> {
                // 清除画布之前绘制的背景
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                canvas.translate(mX.toFloat(), mY.toFloat())
                drawButton(canvas)
                drawLoading(canvas)
            }
            STATE_RESULT -> {
                // 清除画布之前绘制的背景
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                canvas.translate(mX.toFloat(), mY.toFloat())
                drawButton(canvas)
                drawResult(canvas, mSucceed)
            }
            else -> {
            }
        }
    }

    /**
     * 绘制按钮
     */
    private fun drawButton(canvas: Canvas) {
        mButtonPath!!.reset()
        mCircleLeft!![-mViewWidth / 2f, -mViewHeight / 2f, -mViewWidth / 2f + mViewHeight] = mViewHeight / 2f
        mButtonPath!!.arcTo(mCircleLeft!!, 90f, 180f)
        mButtonPath!!.lineTo(mViewWidth / 2f - mViewHeight / 2f, -mViewHeight / 2f)
        mCircleRight!![mViewWidth / 2f - mViewHeight, -mViewHeight / 2f, mViewWidth / 2f] = mViewHeight / 2f
        mButtonPath!!.arcTo(mCircleRight!!, 270f, 180f)
        mButtonPath!!.lineTo(-mViewWidth / 2f + mViewHeight / 2f, mViewHeight / 2f)
        canvas.drawPath(mButtonPath!!, mBackgroundPaint!!)
    }

    /**
     * 绘制加载转圈
     */
    private fun drawLoading(canvas: Canvas) {
        mDstPath!!.reset()
        mCircleMid!![-mMaxHeight / 2f, -mMaxHeight / 2f, mMaxHeight / 2f] = mMaxHeight / 2f
        mLoadPath!!.addArc(mCircleMid!!, 270f, 359.999f)
        mPathMeasure!!.setPath(mLoadPath, true)
        var startD = 0f
        val stopD: Float
        if (mProgressStyle == STYLE_LOADING) {
            startD = mPathMeasure!!.length * mLoadValue
            stopD = startD + mPathMeasure!!.length / 2 * mLoadValue
        } else {
            stopD = mPathMeasure!!.length * mCurrentProgress
        }
        mPathMeasure!!.getSegment(startD, stopD, mDstPath, true)
        canvas.drawPath(mDstPath!!, mLoadingPaint!!)
    }

    /**
     * 绘制结果按钮
     */
    private fun drawResult(canvas: Canvas, isSucceed: Boolean) {
        if (isSucceed) {
            mResultPath!!.moveTo(-mViewHeight / 6f, 0f)
            mResultPath!!.lineTo(0f, (-mViewHeight / 6 + (1 + Math.sqrt(5.0)) * mViewHeight / 12).toFloat())
            mResultPath!!.lineTo(mViewHeight / 6f, -mViewHeight / 6f)
        } else {
            mResultPath!!.moveTo(-mViewHeight / 6f, mViewHeight / 6f)
            mResultPath!!.lineTo(mViewHeight / 6f, -mViewHeight / 6f)
            mResultPath!!.moveTo(-mViewHeight / 6f, -mViewHeight / 6f)
            mResultPath!!.lineTo(mViewHeight / 6f, mViewHeight / 6f)
        }
        canvas.drawPath(mResultPath!!, mResultPaint!!)
    }

    /**
     * 开始提交动画
     */
    private fun startSubmitAnim() {
        mButtonState = STATE_SUBMIT
        mSubmitAnim = ValueAnimator.ofInt(mMaxWidth, mMaxHeight)
        mSubmitAnim?.run {
            addUpdateListener(AnimatorUpdateListener { animation: ValueAnimator ->
                mViewWidth = animation.animatedValue as Int
                if (mViewWidth == mViewHeight) {
                    mBackgroundPaint!!.color = Color.parseColor("#DDDDDD")
                    mBackgroundPaint!!.style = Paint.Style.STROKE
                }
                invalidate()
            })
            duration = 300
            interpolator = AccelerateInterpolator()
            start()
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    if (mDoResult) {
                        startResultAnim()
                    } else {
                        startLoadingAnim()
                    }
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
    }

    /**
     * 开始加载动画
     */
    private fun startLoadingAnim() {
        mButtonState = STATE_LOADING
        if (mProgressStyle == STYLE_PROGRESS) {
            return
        }
        mLoadingAnim = ValueAnimator.ofFloat(0f, 1f)
        mLoadingAnim?.run {
            addUpdateListener(AnimatorUpdateListener { animation: ValueAnimator ->
                mLoadValue = animation.animatedValue as Float
                invalidate()
            })
            duration = 2000
            repeatCount = ValueAnimator.INFINITE
            start()
        }
    }

    /**
     * 开始结果动画
     */
    private fun startResultAnim() {
        mButtonState = STATE_RESULT
        if (mLoadingAnim != null) {
            mLoadingAnim!!.cancel()
        }
        mResultAnim = ValueAnimator.ofInt(mMaxHeight, mMaxWidth)
        mResultAnim?.run {
            addUpdateListener(AnimatorUpdateListener { animation: ValueAnimator ->
                mViewWidth = animation.animatedValue as Int
                mResultPaint!!.alpha = (mViewWidth - mViewHeight) * 255 / (mMaxWidth - mMaxHeight)
                if (mViewWidth == mViewHeight) {
                    if (mSucceed) {
                        mBackgroundPaint!!.color = mSucceedColor
                    } else {
                        mBackgroundPaint!!.color = mErrorColor
                    }
                    mBackgroundPaint!!.style = Paint.Style.FILL_AND_STROKE
                }
                invalidate()
            })
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    // 请求重新测量自身，因为 onMeasure 方法中避开了动画执行中获取 View 宽高
                    requestLayout()
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
            duration = 300
            interpolator = AccelerateInterpolator()
            start()
        }
    }

    override fun performClick(): Boolean {
        if (mButtonState == STATE_NONE) {
            startSubmitAnim()
            return super.performClick()
        }
        return true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mSubmitAnim != null) {
            mSubmitAnim!!.cancel()
        }
        if (mLoadingAnim != null) {
            mLoadingAnim!!.cancel()
        }
        if (mResultAnim != null) {
            mResultAnim!!.cancel()
        }
    }

    /**
     * 显示进度
     */
    fun showProgress() {
        if (mButtonState == STATE_NONE) {
            startSubmitAnim()
        }
    }

    /**
     * 显示成功
     */
    fun showSucceed() {
        showResult(true)
    }

    /**
     * 显示错误
     */
    fun showError() {
        showResult(false)
    }

    /**
     * 显示错误之后延迟重置
     */
    fun showError(delayMillis: Long) {
        showResult(false)
        postDelayed({ reset() }, delayMillis)
    }

    /**
     * 显示提交结果
     */
    private fun showResult(succeed: Boolean) {
        if (mButtonState == STATE_NONE || mButtonState == STATE_RESULT || mDoResult) {
            return
        }
        mDoResult = true
        mSucceed = succeed
        if (mButtonState == STATE_LOADING) {
            startResultAnim()
        }
    }

    /**
     * 重置按钮的状态
     */
    fun reset() {
        if (mSubmitAnim != null) {
            mSubmitAnim!!.cancel()
        }
        if (mLoadingAnim != null) {
            mLoadingAnim!!.cancel()
        }
        if (mResultAnim != null) {
            mResultAnim!!.cancel()
        }
        mButtonState = STATE_NONE
        mViewWidth = mMaxWidth
        mViewHeight = mMaxHeight
        mSucceed = false
        mDoResult = false
        mCurrentProgress = 0f
        resetPaint()
        invalidate()
    }

    /**
     * 设置按钮进度
     */
    fun setProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
        mCurrentProgress = progress
        if (mProgressStyle == STYLE_PROGRESS && mButtonState == STATE_LOADING) {
            invalidate()
        }
    }

    /**
     * 获取当前主题的强调色
     */
    private val accentColor: Int
        private get() {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(R.attr.colorAccent, typedValue, true)
            return typedValue.data
        }

    companion object {
        /** 无进度  */
        const val STYLE_LOADING = 0x00

        /** 带进度  */
        const val STYLE_PROGRESS = 0x01

        /** 默认状态  */
        const val STATE_NONE = 0

        /** 提交状态  */
        const val STATE_SUBMIT = 1

        /** 加载状态  */
        const val STATE_LOADING = 2

        /** 结果状态  */
        const val STATE_RESULT = 3
    }

    init {
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.res_SubmitButton, defStyleAttr, 0)
        mProgressColor = typedArray.getColor(R.styleable.res_SubmitButton_res_progressColor, accentColor)
        mSucceedColor = typedArray.getColor(R.styleable.res_SubmitButton_res_succeedColor, Color.parseColor("#19CC95"))
        mErrorColor = typedArray.getColor(R.styleable.res_SubmitButton_res_errorColor, Color.parseColor("#FC8E34"))
        mProgressStyle = typedArray.getInt(R.styleable.res_SubmitButton_res_progressStyle, STYLE_LOADING)
        typedArray.recycle()
        initPaint()
        resetPaint()
    }
}