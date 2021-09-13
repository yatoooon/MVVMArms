package com.common.template.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.common.template.R
import com.common.template.BR
import com.common.template.databinding.LayoutFooterBinding

class FooterAdapter(val retry: () -> Unit) : LoadStateAdapter<FooterAdapter.BindingHolder>() {

    inner class BindingHolder(val binding: LayoutFooterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BindingHolder {
        return BindingHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_footer,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BindingHolder, loadState: LoadState) {
        val footer:Boolean = loadState is LoadState.Loading
        holder.binding.setVariable(BR.footer, footer)
        holder.binding.executePendingBindings()
        holder.binding.retryButton.setOnClickListener { retry() }
    }

}
