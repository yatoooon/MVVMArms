package com.common.res.http.net

import java.io.Serializable


/**
 * 服务器网络数据返回模型
 */
@Suppress("unused")
abstract class BaseResponse<T> : Serializable {
    abstract val code: Int

    abstract val msg: String

    override fun toString(): String {
        return "BaseResponse(code=$code, msg='$msg')"
    }
}