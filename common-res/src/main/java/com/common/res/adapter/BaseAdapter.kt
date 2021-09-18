package com.common.res.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.common.res.BR

/**
 *
 */
open class BaseAdapter<T>(layoutId: Int, private val variableId: Int = BR.data) :
    BaseQuickAdapter<T, BaseDataBindingHolder<*>>(layoutId) {
    override fun convert(holder: BaseDataBindingHolder<*>, item: T) {
        holder.dataBinding?.let {
            it.setVariable(variableId, item)
            it.executePendingBindings()
        }
    }
}