package com.common.res.http.api

import com.common.res.http.net.BaseResponse


data class LoginEntity(
    override val code: Int = 0,
    override val msg: String = "",
    val token: String = ""
):BaseResponse<LoginEntity>() {

}