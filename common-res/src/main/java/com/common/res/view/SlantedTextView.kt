package com.common.res.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.text.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import androidx.annotation.StringRes
import com.common.res.R

/**
 * author : HaoZhang & Android 轮子哥
 * github : https://github.com/HeZaiJin/SlantedTextView
 * time   : 2016/06/30
 * desc   : 一个倾斜的 TextView，适用于标签效果
 */
@SuppressLint("RtlHardcoded")
class SlantedTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    /** 背景画笔  */
    private val mBackgroundPaint: Paint

    /** 文字画笔  */
    private val mTextPaint: TextPaint
    /**
     * 获取显示文本
     */
    /** 显示的文本  */
    var text = ""
        private set

    /** 倾斜重心  */
    private var mGravity = 0

    /** 是否绘制成三角形的  */
    private var mTriangle = false

    /** 背景颜色  */
    private var mColorBackground = 0

    /** 文字测量范围装载  */
    private val mTextBounds = Rect()

    /** 测量出来的文本高度  */
    private var mTextHeight = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mTextPaint.getTextBounds(text, 0, text.length, mTextBounds)
        mTextHeight = mTextBounds.height() + paddingTop + paddingBottom
        var width = 0
        when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.EXACTLY -> width = MeasureSpec.getSize(widthMeasureSpec)
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> width =
                mTextBounds.width() + paddingLeft + paddingRight
            else -> {
            }
        }
        var height = 0
        when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.EXACTLY -> height = MeasureSpec.getSize(heightMeasureSpec)
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> height =
                mTextBounds.height() + paddingTop + paddingBottom
            else -> {
            }
        }
        setMeasuredDimension(Math.max(width, height), Math.max(width, height))
    }

    override fun onDraw(canvas: Canvas) {
        drawBackground(canvas)
        drawText(canvas)
    }

    /**
     * 绘制背景
     */
    private fun drawBackground(canvas: Canvas) {
        val path = Path()
        val width = canvas.width
        val height = canvas.height
        when (mGravity) {
            Gravity.LEFT, Gravity.LEFT or Gravity.TOP -> if (mTriangle) {
                path.lineTo(0f, height.toFloat())
                path.lineTo(width.toFloat(), 0f)
            } else {
                path.moveTo(width.toFloat(), 0f)
                path.lineTo(0f, height.toFloat())
                path.lineTo(0f, (height - mTextHeight).toFloat())
                path.lineTo((width - mTextHeight).toFloat(), 0f)
            }
            Gravity.NO_GRAVITY, Gravity.RIGHT, Gravity.RIGHT or Gravity.TOP -> if (mTriangle) {
                path.lineTo(width.toFloat(), 0f)
                path.lineTo(width.toFloat(), height.toFloat())
            } else {
                path.lineTo(width.toFloat(), height.toFloat())
                path.lineTo(width.toFloat(), (height - mTextHeight).toFloat())
                path.lineTo(mTextHeight * 1f, 0f)
            }
            Gravity.BOTTOM, Gravity.LEFT or Gravity.BOTTOM -> if (mTriangle) {
                path.lineTo(width.toFloat(), height.toFloat())
                path.lineTo(0f, height.toFloat())
            } else {
                path.lineTo(width.toFloat(), height.toFloat())
                path.lineTo((width - mTextHeight).toFloat(), height.toFloat())
                path.lineTo(0f, mTextHeight.toFloat())
            }
            Gravity.RIGHT or Gravity.BOTTOM -> if (mTriangle) {
                path.moveTo(0f, height.toFloat())
                path.lineTo(width.toFloat(), height.toFloat())
                path.lineTo(width.toFloat(), 0f)
            } else {
                path.moveTo(0f, height.toFloat())
                path.lineTo(mTextHeight * 1f, height.toFloat())
                path.lineTo(width.toFloat(), mTextHeight.toFloat())
                path.lineTo(width.toFloat(), 0f)
            }
            else -> throw IllegalArgumentException("are you ok?")
        }
        path.close()
        canvas.drawPath(path, mBackgroundPaint)
        canvas.save()
    }

    /**
     * 绘制文本
     */
    private fun drawText(canvas: Canvas) {
        val width = canvas.width - mTextHeight / 2
        val height = canvas.height - mTextHeight / 2
        val rect: Rect
        val rectF: RectF
        val offset = mTextHeight / 2
        val toX: Float
        val toY: Float
        val centerX: Float
        val centerY: Float
        val angle: Float
        when (mGravity) {
            Gravity.LEFT, Gravity.LEFT or Gravity.TOP -> {
                rect = Rect(0, 0, width, height)
                rectF = RectF(rect)
                rectF.right = mTextPaint.measureText(text, 0, text.length)
                rectF.bottom = mTextPaint.descent() - mTextPaint.ascent()
                rectF.left += (rect.width() - rectF.right) / 2.0f
                rectF.top += (rect.height() - rectF.bottom) / 2.0f
                toX = rectF.left
                toY = rectF.top - mTextPaint.ascent()
                centerX = width / 2f
                centerY = height / 2f
                angle = -ROTATE_ANGLE.toFloat()
            }
            Gravity.NO_GRAVITY, Gravity.RIGHT, Gravity.RIGHT or Gravity.TOP -> {
                rect = Rect(offset, 0, width + offset, height)
                rectF = RectF(rect)
                rectF.right = mTextPaint.measureText(text, 0, text.length)
                rectF.bottom = mTextPaint.descent() - mTextPaint.ascent()
                rectF.left += (rect.width() - rectF.right) / 2.0f
                rectF.top += (rect.height() - rectF.bottom) / 2.0f
                toX = rectF.left
                toY = rectF.top - mTextPaint.ascent()
                centerX = width / 2f + offset
                centerY = height / 2f
                angle = ROTATE_ANGLE.toFloat()
            }
            Gravity.BOTTOM, Gravity.LEFT or Gravity.BOTTOM -> {
                rect = Rect(0, offset, width, height + offset)
                rectF = RectF(rect)
                rectF.right = mTextPaint.measureText(text, 0, text.length)
                rectF.bottom = mTextPaint.descent() - mTextPaint.ascent()
                rectF.left += (rect.width() - rectF.right) / 2.0f
                rectF.top += (rect.height() - rectF.bottom) / 2.0f
                toX = rectF.left
                toY = rectF.top - mTextPaint.ascent()
                centerX = width / 2f
                centerY = height / 2f + offset
                angle = ROTATE_ANGLE.toFloat()
            }
            Gravity.RIGHT or Gravity.BOTTOM -> {
                rect = Rect(offset, offset, width + offset, height + offset)
                rectF = RectF(rect)
                rectF.right = mTextPaint.measureText(text, 0, text.length)
                rectF.bottom = mTextPaint.descent() - mTextPaint.ascent()
                rectF.left += (rect.width() - rectF.right) / 2.0f
                rectF.top += (rect.height() - rectF.bottom) / 2.0f
                toX = rectF.left
                toY = rectF.top - mTextPaint.ascent()
                centerX = width / 2f + offset
                centerY = height / 2f + offset
                angle = -ROTATE_ANGLE.toFloat()
            }
            else -> throw IllegalArgumentException("are you ok?")
        }
        canvas.rotate(angle, centerX, centerY)
        canvas.drawText(text, toX, toY, mTextPaint)
    }

    /**
     * 设置显示文本
     */
    fun setText(@StringRes id: Int) {
        setText(resources.getString(id))
    }

    fun setText(text: String) {
        if (!TextUtils.isEmpty(text) && text != text) {
            this.text = text
            invalidate()
        }
    }
    /**
     * 获取字体颜色
     */
    /**
     * 设置字体颜色
     */
    var textColor: Int
        get() = mTextPaint.color
        set(color) {
            if (textColor != color) {
                mTextPaint.color = color
                invalidate()
            }
        }
    /**
     * 获取字体大小
     */
    /**
     * 设置字体大小
     */
    var textSize: Float
        get() = mTextPaint.textSize
        set(size) {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
        }

    fun setTextSize(unit: Int, size: Float) {
        val textSize = TypedValue.applyDimension(unit, size, resources.displayMetrics)
        if (textSize != textSize) {
            mTextPaint.textSize = textSize
            invalidate()
        }
    }
    /**
     * 获取文本样式
     */
    /**
     * 设置文本样式
     */
    var textStyle: Typeface
        get() = mTextPaint.typeface
        set(tf) {
            if (textStyle !== tf) {
                mTextPaint.typeface = tf
                invalidate()
            }
        }
    /**
     * 获取背景颜色
     */
    /**
     * 设置背景颜色
     */
    var colorBackground: Int
        get() = mColorBackground
        set(color) {
            if (colorBackground != color) {
                mColorBackground = color
                mBackgroundPaint.color = mColorBackground
                invalidate()
            }
        }
    /**
     * 获取倾斜重心
     */// 适配布局反方向
    /**
     * 设置倾斜重心
     */
    var gravity: Int
        get() = mGravity
        set(gravity) {
            if (mGravity != gravity) {
                // 适配布局反方向
                mGravity =
                    Gravity.getAbsoluteGravity(gravity, resources.configuration.layoutDirection)
                invalidate()
            }
        }
    /**
     * 当前是否是三角形
     */
    /**
     * 是否设置成三角形
     */
    var isTriangle: Boolean
        get() = mTriangle
        set(triangle) {
            if (isTriangle != triangle) {
                mTriangle = triangle
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
        /** 旋转角度  */
        const val ROTATE_ANGLE = 45
    }

    init {
        mBackgroundPaint = Paint()
        mBackgroundPaint.style = Paint.Style.FILL
        mBackgroundPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
        mBackgroundPaint.isAntiAlias = true
        mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint.isAntiAlias = true
        val array = context.obtainStyledAttributes(attrs, R.styleable.res_SlantedTextView)
        setText(array.getString(R.styleable.res_SlantedTextView_android_text)!!)
        setTextSize(
            TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(
                R.styleable.res_SlantedTextView_android_textSize, resources.getDimension(R.dimen.res_sp_12)
                    .toInt()
            ).toFloat()
        )
        textColor = array.getColor(R.styleable.res_SlantedTextView_android_textColor, Color.WHITE)
        textStyle = Typeface.defaultFromStyle(
            array.getInt(
                R.styleable.res_SlantedTextView_android_textStyle,
                Typeface.NORMAL
            )
        )
        gravity = array.getInt(R.styleable.res_SlantedTextView_android_gravity, Gravity.END)
        colorBackground =
            array.getColor(R.styleable.res_SlantedTextView_android_colorBackground, accentColor)
        isTriangle = array.getBoolean(R.styleable.res_SlantedTextView_res_triangle, false)
        array.recycle()
    }
}