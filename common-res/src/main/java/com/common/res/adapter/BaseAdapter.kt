package com.common.res.adapter

import android.content.Context
import com.common.res.BR
import com.king.base.adapter.BaseRecyclerAdapter

/**
 *
 */
open class BaseAdapter<T>(context: Context,layoutId: Int,private val variableId: Int = BR.data) : BaseRecyclerAdapter<T, BindViewHolder<*>>(context,layoutId) {

    override fun bindViewDatas(holder: BindViewHolder<*>, item: T, position: Int) {
        holder.dataBinding?.let {
            it.setVariable(variableId,item)
            it.executePendingBindings()
        }
    }
}