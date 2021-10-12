package com.common.res.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.common.res.http.imageloader.BaseImageLoaderStrategy
import com.common.res.http.imageloader.ImageConfig
import com.common.res.utils.ArmsUtil
import com.common.res.utils.Preconditions
import com.common.res.glide.config.GlideAppliesOptions
import com.common.res.glide.config.GlideArms
import com.common.res.glide.config.GlideRequests
import com.common.res.glide.config.ImageConfigImpl
import timber.log.Timber

//glide 加载策略
/**
 * ================================================
 * 此类只是简单的实现了 Glide 加载的策略,方便快速使用,但大部分情况会需要应对复杂的场景
 * 这时可自行实现 [BaseImageLoaderStrategy] 和 [ImageConfig] 替换现有策略
 * ================================================
 */
class GlideImageLoaderStrategy : BaseImageLoaderStrategy<ImageConfigImpl?>, GlideAppliesOptions {
    override fun loadImage(ctx: Context?, config: ImageConfigImpl?) {
        Preconditions.checkNotNull(ctx, "Context is required")
        Preconditions.checkNotNull(config, "ImageConfigImpl is required")
        Preconditions.checkNotNull(config!!.imageView, "ImageView is required")
        val requests: GlideRequests
        requests = GlideArms.with(ctx!!) //如果context是activity则自动使用Activity的生命周期
        val glideRequest = requests.load(config.res)
        when (config.cacheStrategy) {
            CacheStrategy.Companion.NONE -> glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE)
            CacheStrategy.Companion.RESOURCE -> glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            CacheStrategy.Companion.DATA -> glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA)
            CacheStrategy.Companion.AUTOMATIC -> glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            else -> glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL)
        }
        if (config.isCrossFade) {
            glideRequest.transition(DrawableTransitionOptions.withCrossFade())
        }
        if (config.isCenterCrop) {
            glideRequest.centerCrop()
        }
        if (config.isCircle) {
            glideRequest.circleCrop()
        }
        if (config.isImageRadius()) {
            glideRequest.transform(RoundedCorners(config.imageRadius))
        }
        if (config.isBlurImage) {
            glideRequest.transform(BlurTransformation(config.blurValue))
        }
        if (config.transformation != null) { //glide用它来改变图形的形状
            glideRequest.transform(config.transformation)
        }
        if (config.placeholder != null) //设置占位符
        {
            glideRequest.placeholder(config.placeholder)
        }
        if (config.errorPic != null) //设置错误的图片
        {
            glideRequest.error(config.errorPic)
        }
        if (config.fallback != null) //设置请求 url 为空图片
        {
            glideRequest.fallback(config.fallback)
        }
        glideRequest
                .into(config.imageView)
    }

    override fun clear(ctx: Context?, config: ImageConfigImpl?) {
        Preconditions.checkNotNull(ctx, "Context is required")
        Preconditions.checkNotNull(config, "ImageConfigImpl is required")
        if (config!!.imageView != null) {
            GlideArms.get(ctx!!).requestManagerRetriever[ctx].clear(config.imageView)
        }
        if (config.getImageViews() != null && config.getImageViews()!!.isNotEmpty()) { //取消在执行的任务并且释放资源
            for (imageView in config.getImageViews()!!) {
                GlideArms.get(ctx!!).requestManagerRetriever[ctx].clear(imageView!!)
            }
        }
        if (config.isClearDiskCache) { //清除本地缓存
            ArmsUtil.obtainAppComponent().executorService.execute { Glide.get(ctx!!).clearDiskCache() }
        }
        if (config.isClearMemory) { //清除内存缓存
            ArmsUtil.obtainAppComponent().executorService.execute { Glide.get(ctx!!).clearMemory() }
        }
    }

    override fun applyGlideOptions(context: Context, builder: GlideBuilder) {
        Timber.i("applyGlideOptions")
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        Timber.i("registerComponents")
    }
}