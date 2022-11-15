package com.common.res.http.net

import com.common.res.livedata.StatusEvent

/**
 * @author [Jenly](mailto:jenly1314@gmail.com)
 */
class Result<T> @JvmOverloads constructor(
    var status: Int?= null,
    var message: String? = null,
    var data: T? = null,
) {

    val isSuccess: Boolean
        get() = status == StatusEvent.Status.SUCCESS
    val isFailure: Boolean
        get() = status == StatusEvent.Status.FAILURE



    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(StatusEvent.Status.SUCCESS, null, data)
        }

        fun <T> failure(msg: String?): Result<T?> {
            return Result(StatusEvent.Status.FAILURE, msg)
        }
    }
}