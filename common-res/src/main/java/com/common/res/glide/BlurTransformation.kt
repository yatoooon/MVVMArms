package com.common.res.glide

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.common.core.util.FastBlur
import java.security.MessageDigest

//高斯模糊
class BlurTransformation(private val mRadius: Int) : BitmapTransformation() {
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return FastBlur.doBlur(toTransform, mRadius, true)
    }

    override fun equals(o: Any?): Boolean {
        return o is BlurTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    companion object {
        const val DEFAULT_RADIUS = 15
        private val ID = BlurTransformation::class.java.name
        private val ID_BYTES = ID.toByteArray(CHARSET)
    }
}