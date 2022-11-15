package com.common.res.http.net


fun <T> Result<T>.parseData(resultListener: ResultListener<T>.() -> Unit) {
    val listener = ResultListener<T>().also(resultListener)
    listener.onComplete()
    when {
        isSuccess -> listener.onSuccess(data)
        isFailure -> listener.onFailed(message)
    }
}

class ResultListener<T> {
    var onSuccess: (data: T?) -> Unit = {}
    var onFailed: (errorMsg: String?) -> Unit = { _ -> }
    var onComplete: () -> Unit = {}
}