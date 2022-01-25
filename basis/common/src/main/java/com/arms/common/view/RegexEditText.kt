package com.arms.common.view

import android.content.Context
import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.arms.res.R
import java.util.regex.Pattern

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/06/29
 * desc   : 正则输入限制编辑框
 */
open class RegexEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr), InputFilter {
    /** 正则表达式规则  */
    private var mPattern: Pattern? = null

    /**
     * 是否有这个输入标记
     */
    fun hasInputType(type: Int): Boolean {
        return inputType and type != 0
    }

    /**
     * 添加一个输入标记
     */
    fun addInputType(type: Int) {
        inputType = inputType or type
    }

    /**
     * 移除一个输入标记
     */
    fun removeInputType(type: Int) {
        inputType = inputType and type.inv()
    }
    /**
     * 获取输入正则
     */
    /**
     * 设置输入正则
     */
    var inputRegex: String?
        get() = if (mPattern == null) {
            null
        } else mPattern!!.pattern()
        set(regex) {
            if (TextUtils.isEmpty(regex)) {
                return
            }
            mPattern = Pattern.compile(regex)
            addFilters(this)
        }

    /**
     * 添加筛选规则
     */
    fun addFilters(filter: InputFilter?) {
        if (filter == null) {
            return
        }
        val newFilters: Array<InputFilter?>
        val oldFilters = filters
        if (oldFilters != null && oldFilters.size > 0) {
            newFilters = arrayOfNulls(oldFilters.size + 1)
            // 复制旧数组的元素到新数组中
            System.arraycopy(oldFilters, 0, newFilters, 0, oldFilters.size)
            newFilters[oldFilters.size] = filter
        } else {
            newFilters = arrayOfNulls(1)
            newFilters[0] = filter
        }
        super.setFilters(newFilters)
    }

    /**
     * 清空筛选规则
     */
    fun clearFilters() {
        super.setFilters(arrayOfNulls(0))
    }

    /**
     * [InputFilter]
     *
     * @param source        新输入的字符串
     * @param start         新输入的字符串起始下标
     * @param end           新输入的字符串终点下标
     * @param dest          输入之前文本框内容
     * @param destStart     在原内容上的起始坐标
     * @param destEnd       在原内容上的终点坐标
     * @return              返回字符串将会加入到内容中
     */
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        destStart: Int,
        destEnd: Int
    ): CharSequence {
        if (mPattern == null) {
            return source
        }

        // 拼接出最终的字符串
        val begin = dest.toString().substring(0, destStart)
        val over = dest.toString().substring(
            destStart + (destEnd - destStart),
            destStart + (dest.toString().length - begin.length)
        )
        val result = begin + source + over

        // 判断是插入还是删除
        if (destStart > destEnd - 1) {
            // 如果是插入字符
            if (!mPattern!!.matcher(result).matches()) {
                // 如果不匹配就不让这个字符输入
                return ""
            }
        } else {
            // 如果是删除字符
            if (!mPattern!!.matcher(result).matches()) {
                // 如果不匹配则不让删除（删空操作除外）
                if ("" != result) {
                    return dest.toString().substring(destStart, destEnd)
                }
            }
        }

        // 不做任何修改
        return source
    }

    companion object {
        /** 手机号（只能以 1 开头）  */
        const val REGEX_MOBILE = "[1]\\d{0,10}"

        /** 中文（普通的中文字符）  */
        const val REGEX_CHINESE = "[\\u4e00-\\u9fa5]*"

        /** 英文（大写和小写的英文）  */
        const val REGEX_ENGLISH = "[a-zA-Z]*"

        /** 数字（只允许输入纯数字） */
        const val REGEX_NUMBER = "\\d*"

        /** 计数（非 0 开头的数字）  */
        const val REGEX_COUNT = "[1-9]\\d*"

        /** 用户名（中文、英文、数字）  */
        const val REGEX_NAME = "[[\\u4e00-\\u9fa5]|[a-zA-Z]|\\d]*"

        /** 非空格的字符（不能输入空格）  */
        const val REGEX_NONNULL = "\\S+"
    }

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.res_RegexEditText)
        if (array.hasValue(R.styleable.res_RegexEditText_res_inputRegex)) {
            inputRegex = array.getString(R.styleable.res_RegexEditText_res_inputRegex)
        } else if (array.hasValue(R.styleable.res_RegexEditText_res_regexType)) {
            val regexType = array.getInt(R.styleable.res_RegexEditText_res_regexType, 0)
            when (regexType) {
                0x01 -> inputRegex = REGEX_MOBILE
                0x02 -> inputRegex = REGEX_CHINESE
                0x03 -> inputRegex = REGEX_ENGLISH
                0x04 -> inputRegex = REGEX_NUMBER
                0x05 -> inputRegex = REGEX_COUNT
                0x06 -> inputRegex = REGEX_NAME
                0x07 -> inputRegex = REGEX_NONNULL
                else -> {
                }
            }
        }
        array.recycle()
    }
}