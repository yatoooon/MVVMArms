package com.basis.common.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.basis.common.BR

/**
 *
 */
open class BaseAdapter<T>(layoutId: Int, private val variableId: Int = BR.item) :
    BaseQuickAdapter<T, BaseDataBindingHolder<*>>(layoutId), LoadMoreModule {

    override fun convert(holder: BaseDataBindingHolder<*>, item: T) {
        holder.dataBinding?.let {
            it.setVariable(variableId, item)
            it.executePendingBindings()
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }

    var page = 1

    fun nextPage() {
        page++
    }

    fun reset() {
        page = 1
    }

    fun isFirstPage() = page == 1
}