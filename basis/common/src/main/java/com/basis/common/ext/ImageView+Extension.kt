package com.basis.common.ext

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.basis.common.R
import com.basis.common.glide.config.ImageConfigImpl
import com.basis.common.utils.ArmsUtil

//ImageView 扩展
fun ImageView.load(
        res: Any?,
        isCircle: Boolean = false,
        isCrossFade: Boolean = false,
        isCenterCrop: Boolean = false,
        isClearMemory: Boolean = false,
        isClearDiskCache: Boolean = false,
        blurValue: Int = 0,
        imageRadius: Int = 0,
        cacheStrategy: Int = 0,
        placeholder: Int = R.drawable.res_ic_image_default,
        errorPic: Int = R.drawable.res_ic_image_default,
        fallback: Int = R.drawable.res_ic_image_default
) {
    loadImage(
            this,
            res,
            isCircle,
            isCrossFade,
            isCenterCrop,
            isClearMemory,
            isClearDiskCache,
            blurValue,
            imageRadius,
            cacheStrategy,
            ContextCompat.getDrawable(context, placeholder),
            ContextCompat.getDrawable(context, errorPic),
            ContextCompat.getDrawable(context, fallback))
}

@BindingAdapter(
        value = [
            "res",
            "isCircle",
            "isCrossFade",
            "isCenterCrop",
            "isClearMemory",
            "isClearDiskCache",
            "blurValue",
            "imageRadius",
            "cacheStrategy",
            "placeholder",
            "errorPic",
            "fallback"],
        requireAll = false)
fun loadImage(
        imageView: ImageView,
        res: Any?,
        isCircle: Boolean = false,
        isCrossFade: Boolean = false,
        isCenterCrop: Boolean = false,
        isClearMemory: Boolean = false,
        isClearDiskCache: Boolean = false,
        blurValue: Int = 0,
        imageRadius: Int = 0,
        cacheStrategy: Int = 0,
        placeholder: Drawable? = null,
        errorPic: Drawable? = null,
        fallback: Drawable? = null
) {
    ArmsUtil.obtainAppComponent().imageLoader.loadImage(imageView.context,
            ImageConfigImpl
                    .builder()
                    .res(res)
                    .isCircle(isCircle)
                    .isCrossFade(isCrossFade)
                    .isCenterCrop(isCenterCrop)
                    .isClearMemory(isClearMemory)
                    .isClearDiskCache(isClearDiskCache)
                    .blurValue(blurValue)
                    .imageRadius(imageRadius)
                    .cacheStrategy(cacheStrategy)
                    .placeholder(placeholder
                            ?: ContextCompat.getDrawable(imageView.context, R.drawable.res_ic_image_default))
                    .errorPic(errorPic
                            ?: ContextCompat.getDrawable(imageView.context, R.drawable.res_ic_image_default))
                    .fallback(fallback
                            ?: ContextCompat.getDrawable(imageView.context, R.drawable.res_ic_image_default))
                    .imageView(imageView)
                    .build())
}