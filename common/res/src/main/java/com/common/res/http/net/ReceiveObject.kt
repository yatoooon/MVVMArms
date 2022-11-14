package com.common.res.http.net


data class ReceiveObject(
    override val code: Int = 0,
    override val msg: String = "",
    val data: Any? = null,
) : BaseResponse<ReceiveObject>() {

}