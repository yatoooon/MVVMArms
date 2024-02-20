package com.common.res.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.internal.ThreadUtil.isMainThread

fun <T> LiveData<T>.send(value: T) {
    when (this) {
        is MutableLiveData<T> -> {
            if (isMainThread()) {
                this.value = value
            } else {
                this.postValue(value)
            }
        }
        else -> error("emit() only support MutableLiveData")
    }
}