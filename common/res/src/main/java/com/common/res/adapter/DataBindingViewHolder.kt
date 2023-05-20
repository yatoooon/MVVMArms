package com.common.res.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class DataBindingViewHolder<BD : ViewDataBinding>(itemView: View) : BaseViewHolder(itemView) {

    private val mDataBinding by lazy {
        try {
            val bind: BD? = DataBindingUtil.bind(itemView)
            bind
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 获取dataBinding
     */
    fun getDataBinding(): BD? {
        return mDataBinding
    }
}