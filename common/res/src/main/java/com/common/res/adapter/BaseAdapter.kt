package com.common.res.adapter


import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.DataBindingHolder
import com.common.res.BR

open class BaseAdapter<T:Any>(val layoutId: Int, private val variableId: Int = BR.item) :
    BaseQuickAdapter<T, DataBindingHolder<*>>() {

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

    var total = Integer.MAX_VALUE
    override fun onBindViewHolder(holder: DataBindingHolder<*>, position: Int, item: T?) {
        holder.binding.apply {
            setVariable(variableId, item)
            setVariable(BR.position, position)
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int,
    ): DataBindingHolder<*> {
        return DataBindingHolder<ViewDataBinding>(layoutId,parent)
    }


}
