package com.common.web.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.common.web.R

/**
 * desc :URL adapter
 * author：panyy
 * data：2018/12/5
 */
class H5UrlAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.web_layout_item_h5_url, null) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.tv_url, item)
    }

}

