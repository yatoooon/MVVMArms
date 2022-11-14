package com.common.res.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.common.res.BR

/**
 *
 */
open class BaseAdapter<T>(layoutId: Int, private val variableId: Int = BR.item) :
    BaseQuickAdapter<T, DataBindingViewHolder<*>>(layoutId), LoadMoreModule {

    override fun convert(holder: DataBindingViewHolder<*>, item: T) {
        holder.getDataBinding()?.let {
            it.setVariable(variableId, item)
            it.setVariable(BR.position, holder.bindingAdapterPosition)
            it.executePendingBindings()
        }
    }


    companion object {
        const val PAGE_SIZE = 10
    }

    var page = 1

    fun nextPage() {
        page++
    }

    fun reset() {
        page = 1
    }

    fun isFirstPage() = page == 1

    var total = 0
}