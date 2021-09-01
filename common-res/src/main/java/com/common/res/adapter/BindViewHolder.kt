package com.common.res.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.king.base.adapter.holder.ViewHolder


/**
 *
 */
class BindViewHolder<VDB: ViewDataBinding>(view: View) : ViewHolder(view) {

    val dataBinding: VDB? = DataBindingUtil.bind<VDB>(convertView)
}