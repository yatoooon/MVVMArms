package com.arms.res.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.arms.res.R

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/12/2
 * desc   : 密码遮挡自定义控件
 */
class PasswordView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private val mPaint: Paint
    private val mPath: Path
    private val mPointPaint: Paint

    /** 单个密码框的宽度  */
    private val mItemWidth: Int

    /** 单个密码框的高度  */
    private val mItemHeight: Int

    /** 已经输入的密码个数，也就是需要显示的小黑点个数  */
    private var mCurrentIndex = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> widthMeasureSpec =
                MeasureSpec.makeMeasureSpec(mItemWidth * PASSWORD_COUNT, MeasureSpec.EXACTLY)
            MeasureSpec.EXACTLY -> {
            }
            else -> {
            }
        }
        when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> heightMeasureSpec =
                MeasureSpec.makeMeasureSpec(mItemHeight, MeasureSpec.EXACTLY)
            MeasureSpec.EXACTLY -> {
            }
            else -> {
            }
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        mPaint.strokeWidth = 5f
        canvas.drawPath(mPath, mPaint)

        // 画单个的分割线
        mPaint.strokeWidth = 3f
        for (index in 1 until PASSWORD_COUNT) {
            canvas.drawLine(
                (mItemWidth * index).toFloat(),
                0f,
                (mItemWidth * index).toFloat(),
                mItemHeight.toFloat(),
                mPaint
            )
        }

        // 绘制中间的小黑点
        if (mCurrentIndex == 0) {
            return
        }
        for (i in 1..mCurrentIndex) {
            canvas.drawCircle(
                i * mItemWidth - mItemWidth.toFloat() / 2,
                mItemHeight.toFloat() / 2,
                POINT_RADIUS.toFloat(),
                mPointPaint
            )
        }
    }

    /**
     * 改变密码提示小黑点的个数
     */
    fun setPassWordLength(index: Int) {
        mCurrentIndex = index
        invalidate()
    }

    companion object {
        /** 中心黑点的半径大小  */
        private const val POINT_RADIUS = 15

        /** 中心黑点的颜色  */
        private const val POINT_COLOR = -0x99999a

        /** 密码框边界线的颜色值  */
        private const val STROKE_COLOR = -0x131314

        /** 密码总个数  */
        const val PASSWORD_COUNT = 6
    }

    init {
        mItemWidth = resources.getDimension(R.dimen.res_dp_44).toInt()
        mItemHeight = resources.getDimension(R.dimen.res_dp_41).toInt()
        mPaint = Paint()
        // 设置抗锯齿
        mPaint.isAntiAlias = true
        // 设置颜色
        mPaint.color = STROKE_COLOR
        // 设置描边
        mPaint.style = Paint.Style.STROKE
        mPath = Path()
        mPath.moveTo(0f, 0f)
        mPath.lineTo((mItemWidth * PASSWORD_COUNT).toFloat(), 0f)
        mPath.lineTo((mItemWidth * PASSWORD_COUNT).toFloat(), mItemHeight.toFloat())
        mPath.lineTo(0f, mItemHeight.toFloat())
        mPath.close()
        mPointPaint = Paint()
        mPointPaint.isAntiAlias = true
        mPointPaint.style = Paint.Style.FILL
        mPointPaint.color = POINT_COLOR
    }
}