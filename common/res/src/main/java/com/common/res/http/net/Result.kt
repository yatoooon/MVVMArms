package com.common.res.http.net

import com.common.res.livedata.StatusEvent

/**
 * @author [Jenly](mailto:jenly1314@gmail.com)
 */
class Result<T> @JvmOverloads constructor(
    var status: Int?= null,
    var message: String? = null,
    var data: T? = null,
    var error: Throwable? = null
) {
    val isEmpty: Boolean
        get() = status == StatusEvent.Status.EMPTY
    val isSuccess: Boolean
        get() = status == StatusEvent.Status.SUCCESS
    val isFailure: Boolean
        get() = status == StatusEvent.Status.FAILURE
    val isError : Boolean
        get() = status == StatusEvent.Status.ERROR


    companion object {
        fun <T> empty(): Result<T?> {
            return Result(StatusEvent.Status.EMPTY)
        }

        fun <T> success(data: T): Result<T> {
            return Result(StatusEvent.Status.SUCCESS, null, data)
        }

        fun <T> failure(msg: String?): Result<T?> {
            return Result(StatusEvent.Status.FAILURE, msg)
        }

        fun <T> error(t: Throwable?): Result<T?> {
            return error(t, null)
        }

        fun <T> error(t: Throwable?, msg: String?): Result<T?> {
            return Result(StatusEvent.Status.ERROR, msg, null, t)
        }
    }
}