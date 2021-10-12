package com.common.res.glide.config

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.common.res.utils.ArmsUtil
import com.common.res.utils.DataHelper
import java.io.File
import java.io.InputStream

//glide 配置类
/**
 * ================================================
 * [AppGlideModule] 的默认实现类
 * 用于配置缓存文件夹,切换图片请求框架等操作
 * ================================================
 */
@GlideModule(glideName = "GlideArms")
class GlideConfiguration : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val appComponent = ArmsUtil.obtainAppComponent()
        builder.setDiskCache { DiskLruCacheWrapper.create(DataHelper.makeDirs(File(appComponent.cacheFile, "Glide")), IMAGE_DISK_CACHE_MAX_SIZE.toLong()) }
        val calculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize
        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toInt()
        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toInt()
        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize.toLong()))

        //将配置 Glide 的机会转交给 GlideImageLoaderStrategy,如你觉得框架提供的 GlideImageLoaderStrategy
        //并不能满足自己的需求,想自定义 BaseImageLoaderStrategy,那请你最好实现 GlideAppliesOptions
        //因为只有成为 GlideAppliesOptions 的实现类,这里才能调用 applyGlideOptions(),让你具有配置 Glide 的权利
        val loadImgStrategy = appComponent.imageLoader.loadImgStrategy
        if (loadImgStrategy is GlideAppliesOptions) {
            (loadImgStrategy as GlideAppliesOptions).applyGlideOptions(context, builder)
        }
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        //Glide 默认使用 HttpURLConnection 做网络请求,在这切换成 Okhttp 请求
        val appComponent = ArmsUtil.obtainAppComponent()
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(appComponent.okHttpClient))
        val loadImgStrategy = appComponent.imageLoader.loadImgStrategy
        if (loadImgStrategy is GlideAppliesOptions) {
            (loadImgStrategy as GlideAppliesOptions).registerComponents(context, glide, registry)
        }
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    companion object {
        const val IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024 //图片缓存文件最大值为100Mb
    }
}