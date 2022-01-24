package com.arms.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.arms.common.R

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2021/07/11
 * desc   : 自定义评分控件（系统的 RatingBar 不好用）
 */
class SimpleRatingBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    /** 默认的星星图标  */
    private var mNormalDrawable: Drawable?

    /** 选中的星星图标  */
    private var mFillDrawable: Drawable?

    /** 选中的星星图标  */
    private var mHalfDrawable: Drawable?

    /** 当前星等级  */
    private var mCurrentGrade: Float

    /** 星星总数量  */
    private var mGradeCount: Int

    /** 星星的宽度  */
    private var mGradeWidth: Int

    /** 星星的高度  */
    private var mGradeHeight: Int

    /** 星星之间的间隔  */
    private var mGradeSpace: Int

    /** 星星选择跨度  */
    private var mGradeStep: GradleStep? = null

    /** 星星变化监听事件  */
    private var mListener: OnRatingChangeListener? = null

    /** 星星位置记录  */
    private val mGradeBounds = Rect()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measuredWidth = mGradeWidth * mGradeCount + mGradeSpace * (mGradeCount + 1)
        val measuredHeight = mGradeHeight
        setMeasuredDimension(
            measuredWidth + paddingLeft + paddingRight,
            measuredHeight + paddingTop + paddingBottom
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 如果控件处于不可用状态，直接不处理
        if (!isEnabled) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                var grade = 0f
                val distance = event.x - paddingLeft - mGradeSpace
                if (distance > 0) {
                    grade = distance / (mGradeWidth + mGradeSpace)
                }
                grade = Math.min(Math.max(grade, 0f), mGradeCount.toFloat())
                if (grade - grade.toInt() > 0) {
                    grade = if (grade - grade.toInt() > 0.5f) {
                        // 0.5 - 1 算一颗星
                        (grade + 0.5f)
                    } else {
                        // 0 - 0.5 算半颗星
                        grade.toInt() + 0.5f
                    }
                }
                if (grade * 10 != mCurrentGrade * 10) {
                    mCurrentGrade = grade
                    invalidate()
                    if (mListener != null) {
                        mListener!!.onRatingChanged(this, mCurrentGrade, true)
                    }
                }
            }
            else -> {
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        for (i in 0 until mGradeCount) {
            val start = mGradeSpace + (mGradeWidth + mGradeSpace) * i
            mGradeBounds.left = paddingLeft + start
            mGradeBounds.top = paddingTop
            mGradeBounds.right = mGradeBounds.left + mGradeWidth
            mGradeBounds.bottom = mGradeBounds.top + mGradeHeight
            if (mCurrentGrade > i) {
                if (mHalfDrawable != null && mGradeStep == GradleStep.HALF && mCurrentGrade.toInt() == i && mCurrentGrade - mCurrentGrade.toInt() == 0.5f) {
                    mHalfDrawable!!.bounds = mGradeBounds
                    mHalfDrawable!!.draw(canvas)
                } else {
                    mFillDrawable!!.bounds = mGradeBounds
                    mFillDrawable!!.draw(canvas)
                }
            } else {
                mNormalDrawable!!.bounds = mGradeBounds
                mNormalDrawable!!.draw(canvas)
            }
        }
    }

    fun setRatingDrawable(
        @DrawableRes normalDrawableId: Int,
        @DrawableRes halfDrawableId: Int,
        @DrawableRes fillDrawableId: Int
    ) {
        setRatingDrawable(
            ContextCompat.getDrawable(context, normalDrawableId),
            ContextCompat.getDrawable(context, halfDrawableId),
            ContextCompat.getDrawable(context, fillDrawableId)
        )
    }

    fun setRatingDrawable(
        normalDrawable: Drawable?,
        halfDrawable: Drawable?,
        fillDrawable: Drawable?
    ) {
        check(!(normalDrawable == null || fillDrawable == null)) { "Drawable cannot be empty" }

        // 两张图片的宽高不一致
        check(
            !(normalDrawable.intrinsicWidth != fillDrawable.intrinsicWidth ||
                    normalDrawable.intrinsicHeight != fillDrawable.intrinsicHeight)
        ) { "The width and height of the picture do not agree" }
        mNormalDrawable = normalDrawable
        mHalfDrawable = halfDrawable
        mFillDrawable = fillDrawable
        mGradeWidth = mNormalDrawable!!.intrinsicWidth
        mGradeHeight = mNormalDrawable!!.intrinsicHeight
        requestLayout()
    }

    var grade: Float
        get() = mCurrentGrade
        set(grade) {
            var grade = grade
            if (grade > mGradeCount) {
                grade = mGradeCount.toFloat()
            }
            require(!(grade - grade.toInt() != 0.5f || grade - grade.toInt() > 0)) { "grade must be a multiple of 0.5f" }
            mCurrentGrade = grade
            invalidate()
            if (mListener != null) {
                mListener!!.onRatingChanged(this, mCurrentGrade, false)
            }
        }
    var gradeCount: Int
        get() = mGradeCount
        set(count) {
            if (count > mCurrentGrade) {
                mCurrentGrade = count.toFloat()
            }
            mGradeCount = count
            invalidate()
        }

    fun setGradeSpace(space: Int) {
        mGradeSpace = space
        requestLayout()
    }

    var gradeStep: GradleStep?
        get() = mGradeStep
        set(step) {
            mGradeStep = step
            invalidate()
        }

    fun setOnRatingBarChangeListener(listener: OnRatingChangeListener?) {
        mListener = listener
    }

    enum class GradleStep {
        /** 半颗星  */
        HALF,

        /** 一颗星  */
        ONE
    }

    interface OnRatingChangeListener {
        /**
         * 评分发生变化监听时回调
         *
         * @param grade             当前星星数
         * @param touch             是否通过触摸改变
         */
        fun onRatingChanged(ratingBar: SimpleRatingBar?, grade: Float, touch: Boolean)
    }

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.res_SimpleRatingBar)
        mNormalDrawable = ContextCompat.getDrawable(
            getContext(),
            array.getResourceId(
                R.styleable.res_SimpleRatingBar_res_normalDrawable,
                R.drawable.res_rating_star_off_ic
            )
        )
        mHalfDrawable = ContextCompat.getDrawable(
            getContext(),
            array.getResourceId(
                R.styleable.res_SimpleRatingBar_res_halfDrawable,
                R.drawable.res_rating_star_half_ic
            )
        )
        mFillDrawable = ContextCompat.getDrawable(
            getContext(),
            array.getResourceId(
                R.styleable.res_SimpleRatingBar_res_fillDrawable,
                R.drawable.res_rating_star_fill_ic
            )
        )
        // 两张图片的宽高不一致
        check(!(mNormalDrawable!!.intrinsicWidth != mFillDrawable!!.intrinsicWidth || mNormalDrawable!!.intrinsicWidth != mHalfDrawable!!.intrinsicWidth || mNormalDrawable!!.intrinsicHeight != mFillDrawable!!.intrinsicHeight || mNormalDrawable!!.intrinsicHeight != mHalfDrawable!!.intrinsicHeight)) { "The width and height of the picture do not agree" }
        mCurrentGrade = array.getFloat(R.styleable.res_SimpleRatingBar_res_grade, 0f)
        mGradeCount = array.getInt(R.styleable.res_SimpleRatingBar_res_gradeCount, 5)
        mGradeWidth = array.getDimensionPixelSize(
            R.styleable.res_SimpleRatingBar_res_gradeWidth,
            mNormalDrawable!!.intrinsicWidth
        )
        mGradeHeight = array.getDimensionPixelSize(
            R.styleable.res_SimpleRatingBar_res_gradeHeight,
            mFillDrawable!!.intrinsicHeight
        )
        mGradeSpace =
            array.getDimension(R.styleable.res_SimpleRatingBar_res_gradeSpace, mGradeWidth / 4f).toInt()
        mGradeStep = when (array.getInt(R.styleable.res_SimpleRatingBar_res_gradeStep, 0)) {
            0x01 -> GradleStep.ONE
            0x00 -> GradleStep.HALF
            else -> GradleStep.HALF
        }
        array.recycle()
    }
}