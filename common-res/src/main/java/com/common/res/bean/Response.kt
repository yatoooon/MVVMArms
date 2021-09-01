package com.common.res.bean

import com.google.gson.annotations.SerializedName

/**
 *
 */
class Response<T> {

    @SerializedName("error_code")
    var code: Int? = 0

    var reason: String? = null

    var result: Result<T>? = null

    fun isSuccess() = code == 0
}