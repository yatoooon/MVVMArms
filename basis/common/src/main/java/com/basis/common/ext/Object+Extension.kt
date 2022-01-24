package com.basis.common.ext
//object 扩展
fun <T : Any> T.TAG() = this::class.simpleName