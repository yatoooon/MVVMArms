package com.common.res.http.net


fun <T> Result<T>.parseData(resultListener: ResultListener<T>.() -> Unit) {
    val listener = ResultListener<T>().also(resultListener)
    when {
        isEmpty -> listener.onDataEmpty()
        isSuccess -> listener.onSuccess(data)
        isFailure -> listener.onFailed(status, message)
        isError -> listener.onError(error)
    }
    listener.onComplete()
}

class ResultListener<T> {
    var onSuccess: (data: T?) -> Unit = {}
    var onDataEmpty: () -> Unit = {}
    var onFailed: (errorCode: Int?, errorMsg: String?) -> Unit = { _, _ -> }
    var onError: (e: Throwable?) -> Unit = {}
    var onComplete: () -> Unit = {}
}