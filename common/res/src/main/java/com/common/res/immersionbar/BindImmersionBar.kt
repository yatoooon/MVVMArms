package com.common.res.immersionbar
//导航栏

interface BindImmersionBar {
    companion object {
        //0默认 1沉浸 2全屏
        const val DEFAULT = -0
        const val IMMERSIONBAR = 1
        const val FULLSCREEN = 2
    }

    fun getImmersionBarType(): Int
    fun isStatusBarDarkFont(): Boolean
}