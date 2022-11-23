package com.common.res.http.net

import java.io.Serializable


/**
 * 服务器网络数据返回模型
 */
open class BaseResponse<T> : Serializable {
    var code: Int = 200

    var msg: String = ""

    var data: T? = null

    override fun toString(): String {
        return "BaseResponse(code=$code, msg='$msg', data=$data)"
    }

}