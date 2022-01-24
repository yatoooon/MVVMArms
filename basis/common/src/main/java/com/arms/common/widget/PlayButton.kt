package com.arms.common.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnticipateInterpolator
import com.arms.res.R

/**
 * author : codeestX & Android 轮子哥
 * github : https://github.com/codeestX/ENViews
 * time   : 2021/09/12
 * desc   : 播放暂停动效的按钮
 */
class PlayButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    /**
     * 获取当前状态
     */
    /** 当前状态  */
    var currentState = STATE_PAUSE
        private set

    /** 动画时间  */
    private var mAnimDuration: Int
    private val mPaint: Paint
    private var mWidth = 0
    private var mHeight = 0
    private var mCenterX = 0
    private var mCenterY = 0
    private var mCircleRadius = 0
    private var mRectF: RectF? = null
    private var mBgRectF: RectF? = null
    private var mFraction = 1f
    private val mPath: Path
    private val mDstPath: Path
    private val mPathMeasure: PathMeasure
    private var mPathLength = 0f
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        mWidth = width * 9 / 10
        mHeight = height * 9 / 10
        mCircleRadius = mWidth / resources.getDimension(R.dimen.res_dp_4).toInt()
        mCenterX = width / 2
        mCenterY = height / 2
        mRectF = RectF(
            (mCenterX - mCircleRadius).toFloat(), mCenterY + 0.6f * mCircleRadius,
            (mCenterX + mCircleRadius).toFloat(), mCenterY + 2.6f * mCircleRadius
        )
        mBgRectF = RectF(
            mCenterX - mWidth / 2f, mCenterY - mHeight / 2f,
            mCenterX + mWidth / 2f, mCenterY + mHeight / 2f
        )
        mPath.moveTo((mCenterX - mCircleRadius).toFloat(), mCenterY + 1.8f * mCircleRadius)
        mPath.lineTo((mCenterX - mCircleRadius).toFloat(), mCenterY - 1.8f * mCircleRadius)
        mPath.lineTo((mCenterX + mCircleRadius).toFloat(), mCenterY.toFloat())
        mPath.close()
        mPathMeasure.setPath(mPath, false)
        mPathLength = mPathMeasure.length
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> widthMeasureSpec =
                MeasureSpec.makeMeasureSpec(
                    resources.getDimension(R.dimen.res_dp_60).toInt(), MeasureSpec.EXACTLY
                )
            MeasureSpec.EXACTLY -> {
            }
            else -> {
            }
        }
        when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> heightMeasureSpec =
                MeasureSpec.makeMeasureSpec(
                    resources.getDimension(R.dimen.res_dp_60).toInt(), MeasureSpec.EXACTLY
                )
            MeasureSpec.EXACTLY -> {
            }
            else -> {
            }
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(mCenterX.toFloat(), mCenterY.toFloat(), mWidth / 2f, mPaint)
        if (mFraction < 0) {
            // 弹性部分
            canvas.drawLine(
                (mCenterX + mCircleRadius).toFloat(),
                mCenterY - 1.6f * mCircleRadius + 10 * mCircleRadius * mFraction,
                (
                        mCenterX + mCircleRadius).toFloat(),
                mCenterY + 1.6f * mCircleRadius + 10 * mCircleRadius * mFraction,
                mPaint
            )
            canvas.drawLine(
                (mCenterX - mCircleRadius).toFloat(), mCenterY - 1.6f * mCircleRadius, (
                        mCenterX - mCircleRadius).toFloat(), mCenterY + 1.6f * mCircleRadius, mPaint
            )
            canvas.drawArc(mBgRectF!!, -105f, 360f, false, mPaint)
        } else if (mFraction <= 0.3) {
            // 右侧直线和下方曲线
            canvas.drawLine(
                (mCenterX + mCircleRadius).toFloat(),
                mCenterY - 1.6f * mCircleRadius + mCircleRadius * 3.2f / 0.3f * mFraction,
                (
                        mCenterX + mCircleRadius).toFloat(),
                mCenterY + 1.6f * mCircleRadius,
                mPaint
            )
            canvas.drawLine(
                (mCenterX - mCircleRadius).toFloat(), mCenterY - 1.6f * mCircleRadius, (
                        mCenterX - mCircleRadius).toFloat(), mCenterY + 1.6f * mCircleRadius, mPaint
            )
            if (mFraction != 0f) {
                canvas.drawArc(mRectF!!, 0f, 180f / 0.3f * mFraction, false, mPaint)
            }
            canvas.drawArc(mBgRectF!!, -105 + 360 * mFraction, 360 * (1 - mFraction), false, mPaint)
        } else if (mFraction <= 0.6) {
            // 下方曲线和三角形
            canvas.drawArc(
                mRectF!!,
                180f / 0.3f * (mFraction - 0.3f),
                180 - 180f / 0.3f * (mFraction - 0.3f),
                false,
                mPaint
            )
            mDstPath.reset()
            mPathMeasure.getSegment(
                0.02f * mPathLength,
                0.38f * mPathLength + 0.42f * mPathLength / 0.3f * (mFraction - 0.3f),
                mDstPath,
                true
            )
            canvas.drawPath(mDstPath, mPaint)
            canvas.drawArc(mBgRectF!!, -105 + 360 * mFraction, 360 * (1 - mFraction), false, mPaint)
        } else if (mFraction <= 0.8) {
            // 三角形
            mDstPath.reset()
            mPathMeasure.getSegment(
                0.02f * mPathLength + 0.2f * mPathLength / 0.2f * (mFraction - 0.6f),
                0.8f * mPathLength + 0.2f * mPathLength / 0.2f * (mFraction - 0.6f),
                mDstPath,
                true
            )
            canvas.drawPath(mDstPath, mPaint)
            canvas.drawArc(mBgRectF!!, -105 + 360 * mFraction, 360 * (1 - mFraction), false, mPaint)
        } else {
            // 弹性部分
            mDstPath.reset()
            mPathMeasure.getSegment(
                10 * mCircleRadius * (mFraction - 1), mPathLength,
                mDstPath, true
            )
            canvas.drawPath(mDstPath, mPaint)
        }
    }

    /**
     * 播放状态
     */
    fun play() {
        if (currentState == STATE_PLAY) {
            return
        }
        currentState = STATE_PLAY
        val valueAnimator = ValueAnimator.ofFloat(1f, 100f)
        valueAnimator.duration = mAnimDuration.toLong()
        valueAnimator.interpolator = AnticipateInterpolator()
        valueAnimator.addUpdateListener { animation: ValueAnimator ->
            mFraction = 1 - animation.animatedFraction
            invalidate()
        }
        valueAnimator.start()
    }

    /**
     * 暂停状态
     */
    fun pause() {
        if (currentState == STATE_PAUSE) {
            return
        }
        currentState = STATE_PAUSE
        val valueAnimator = ValueAnimator.ofFloat(1f, 100f)
        valueAnimator.duration = mAnimDuration.toLong()
        valueAnimator.interpolator = AnticipateInterpolator()
        valueAnimator.addUpdateListener { animation: ValueAnimator ->
            mFraction = animation.animatedFraction
            invalidate()
        }
        valueAnimator.start()
    }

    /**
     * 设置动画时间
     */
    fun setAnimDuration(duration: Int) {
        mAnimDuration = duration
    }

    /**
     * 设置线条颜色
     */
    fun setLineColor(color: Int) {
        mPaint.color = color
        invalidate()
    }

    /**
     * 设置线条大小
     */
    fun setLineSize(size: Int) {
        mPaint.strokeWidth = size.toFloat()
        invalidate()
    }

    companion object {
        /** 播放状态  */
        const val STATE_PLAY = 0

        /** 暂停状态  */
        const val STATE_PAUSE = 1
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.res_PlayButton)
        val lineColor = typedArray.getColor(R.styleable.res_PlayButton_res_pb_lineColor, Color.WHITE)
        val lineSize = typedArray.getInteger(
            R.styleable.res_PlayButton_res_pb_lineSize, resources.getDimension(R.dimen.res_dp_4)
                .toInt()
        )
        mAnimDuration = typedArray.getInteger(R.styleable.res_PlayButton_res_pb_animDuration, 200)
        typedArray.recycle()

        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.color = lineColor
        mPaint.strokeWidth = lineSize.toFloat()
        mPaint.pathEffect = CornerPathEffect(1f)
        mPath = Path()
        mDstPath = Path()
        mPathMeasure = PathMeasure()
    }
}