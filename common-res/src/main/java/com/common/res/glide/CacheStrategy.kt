package com.common.res.glide

import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

//缓存策略
interface CacheStrategy {
    @IntDef(ALL, NONE, RESOURCE, DATA, AUTOMATIC)
    @Retention(RetentionPolicy.SOURCE)
    annotation class Strategy
    companion object {
        const val ALL = 0
        const val NONE = 1
        const val RESOURCE = 2
        const val DATA = 3
        const val AUTOMATIC = 4
    }
}