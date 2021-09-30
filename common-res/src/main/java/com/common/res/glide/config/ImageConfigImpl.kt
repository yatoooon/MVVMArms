package com.common.res.glide.config

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.common.res.http.imageloader.ImageConfig
import com.common.res.glide.CacheStrategy

/**
 * ================================================
 * 这里存放图片请求的配置信息,可以一直扩展字段,如果外部调用时想让图片加载框架
 * 做一些操作,比如清除缓存或者切换缓存策略,则可以定义一个 int 类型的变量,内部根据 switch(int) 做不同的操作
 * 其他操作同理
 * ================================================
 */
class ImageConfigImpl private constructor(builder: Builder) : ImageConfig() {
    @CacheStrategy.Strategy
    val cacheStrategy //0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
            : Int
    val fallback //请求 url 为空,则使用此图片作为占位符
            : Drawable?
    val imageRadius //图片每个圆角的大小
            : Int
    val blurValue //高斯模糊值, 值越大模糊效果越大
            : Int

    /**
     * @see {@link Builder.transformation
     */
    @Deprecated("")
    val transformation //glide用它来改变图形的形状
            : BitmapTransformation?
    private var imageViews: Array<out ImageView>? = null
    val isCrossFade //是否使用淡入淡出过渡动画
            : Boolean
    val isCenterCrop //是否将图片剪切为 CenterCrop
            : Boolean
    val isCircle //是否将图片剪切为圆形
            : Boolean
    val isClearMemory //清理内存缓存
            : Boolean
    val isClearDiskCache //清理本地缓存
            : Boolean

    fun getImageViews(): Array<out ImageView>? {
        return imageViews
    }

    val isBlurImage: Boolean
        get() = blurValue > 0

    fun isImageRadius(): Boolean {
        return imageRadius > 0
    }

    class Builder {
        var res: Any? = null
        var imageView: ImageView? = null
        var placeholder: Drawable? = null
        var errorPic: Drawable? = null
        var fallback //请求 url 为空,则使用此图片作为占位符
                : Drawable? = null

        @CacheStrategy.Strategy
        var cacheStrategy //0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
                = 0
        var imageRadius //图片每个圆角的大小
                = 0
        var blurValue //高斯模糊值, 值越大模糊效果越大
                = 0

        /**
         * @see {@link Builder.transformation
         */
        @Deprecated("")
        var transformation //glide用它来改变图形的形状
                : BitmapTransformation? = null
        var imageViews: Array<out ImageView>? = null
        var isCrossFade //是否使用淡入淡出过渡动画
                = false
        var isCenterCrop //是否将图片剪切为 CenterCrop
                = false
        var isCircle //是否将图片剪切为圆形
                = false
        var isClearMemory //清理内存缓存
                = false
        var isClearDiskCache //清理本地缓存
                = false

        fun res(res: Any?): Builder {
            this.res = res
            return this
        }

        fun placeholder(placeholder: Drawable?): Builder {
            this.placeholder = placeholder
            return this
        }

        fun errorPic(errorPic: Drawable?): Builder {
            this.errorPic = errorPic
            return this
        }

        fun fallback(fallback: Drawable?): Builder {
            this.fallback = fallback
            return this
        }

        fun imageView(imageView: ImageView?): Builder {
            this.imageView = imageView
            return this
        }

        fun cacheStrategy(@CacheStrategy.Strategy cacheStrategy: Int): Builder {
            this.cacheStrategy = cacheStrategy
            return this
        }

        fun imageRadius(imageRadius: Int): Builder {
            this.imageRadius = imageRadius
            return this
        }

        fun blurValue(blurValue: Int): Builder { //blurValue 建议设置为 15
            this.blurValue = blurValue
            return this
        }

        /**
         * 给图片添加 Glide 独有的 BitmapTransformation
         *
         *
         * 因为 BitmapTransformation 是 Glide 独有的类, 所以如果 BitmapTransformation 出现在 [ImageConfigImpl] 中
         * 会使 [ImageLoader] 难以切换为其他图片加载框架, 在 [ImageConfigImpl] 中只能配置基础类型和 Android 包里的类
         * 此 API 会在后面的版本中被删除, 请使用其他 API 替代
         *
         * @param transformation [BitmapTransformation]
         */
        @Deprecated("""请使用 {@link #isCircle()}, {@link #isCenterCrop()}, {@link #isImageRadius()} 替代
          如果有其他自定义 BitmapTransformation 的需求, 请自行扩展 {@link BaseImageLoaderStrategy}""")
        fun transformation(transformation: BitmapTransformation?): Builder {
            this.transformation = transformation
            return this
        }

        fun imageViews(vararg imageViews: ImageView): Builder {
            this.imageViews = imageViews
            return this
        }

        fun isCrossFade(isCrossFade: Boolean): Builder {
            this.isCrossFade = isCrossFade
            return this
        }

        fun isCenterCrop(isCenterCrop: Boolean): Builder {
            this.isCenterCrop = isCenterCrop
            return this
        }

        fun isCircle(isCircle: Boolean): Builder {
            this.isCircle = isCircle
            return this
        }

        fun isClearMemory(isClearMemory: Boolean): Builder {
            this.isClearMemory = isClearMemory
            return this
        }

        fun isClearDiskCache(isClearDiskCache: Boolean): Builder {
            this.isClearDiskCache = isClearDiskCache
            return this
        }

        fun build(): ImageConfigImpl {
            return ImageConfigImpl(this)
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    init {
        res = builder.res
        imageView = builder.imageView
        placeholder = builder.placeholder
        errorPic = builder.errorPic
        fallback = builder.fallback
        cacheStrategy = builder.cacheStrategy
        imageRadius = builder.imageRadius
        blurValue = builder.blurValue
        transformation = builder.transformation
        imageViews = builder.imageViews
        isCrossFade = builder.isCrossFade
        isCenterCrop = builder.isCenterCrop
        isCircle = builder.isCircle
        isClearMemory = builder.isClearMemory
        isClearDiskCache = builder.isClearDiskCache
    }
}