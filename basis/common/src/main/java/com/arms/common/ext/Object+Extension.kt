package com.arms.common.ext
//object 扩展
fun <T : Any> T.TAG() = this::class.simpleName