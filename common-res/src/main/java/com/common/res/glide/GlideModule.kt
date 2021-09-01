package com.common.res.glide

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

/**
 *
 */
@GlideModule
class GlideModule : AppGlideModule(){

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.setDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
    }

}