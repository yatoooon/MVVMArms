package com.basis.common.http.net

import com.basis.common.livedata.StatusEvent

/**
 * @author [Jenly](mailto:jenly1314@gmail.com)
 */
class Resource<T> @JvmOverloads constructor(
    var status: Int,
    var message: String? = null,
    var data: T? = null,
    var error: Throwable? = null
) {
    val isLoading: Boolean
        get() = status == StatusEvent.Status.LOADING
    val isSuccess: Boolean
        get() = status == StatusEvent.Status.SUCCESS
    val isFailure: Boolean
        get() = status == StatusEvent.Status.FAILURE

    fun isError(): Boolean {
        return status == StatusEvent.Status.ERROR
    }

    companion object {
        fun <T> loading(): Resource<T?> {
            return Resource(StatusEvent.Status.LOADING)
        }

        fun <T> loading(data: T?): Resource<T?> {
            return Resource(StatusEvent.Status.LOADING, null, data)
        }

        fun <T> success(data: T): Resource<T> {
            return Resource(StatusEvent.Status.SUCCESS, null, data)
        }

        fun <T> failure(msg: String?): Resource<T?> {
            return Resource(StatusEvent.Status.FAILURE, msg)
        }

        fun <T> error(t: Throwable?): Resource<T?> {
            return error(t, null)
        }

        fun <T> error(t: Throwable?, msg: String?): Resource<T?> {
            return Resource(StatusEvent.Status.ERROR, msg, null, t)
        }
    }
}