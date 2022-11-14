package com.common.res.http.net

import androidx.lifecycle.*
import com.common.res.action.ibase.ILoading
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun <T> launchFlow(
    requestBlock: suspend () -> Result<T>,
    startCallback: (() -> Unit)? = null,
    completeCallback: (() -> Unit)? = null,
): Flow<Result<T>> {
    return flow {
        emit(requestBlock())
    }.onStart {
        startCallback?.invoke()
    }.onCompletion {
        completeCallback?.invoke()
    }
}

/**
 * 这个方法只是简单的一个封装Loading的普通方法，不返回任何实体类
 */
fun ILoading.launchWithLoading(requestBlock: suspend () -> Unit) {
    jobList.add(lifecycleScope.launch {
        flow {
            emit(requestBlock())
        }.onStart {
            showLoadingDialog(this@launchWithLoading)
        }.onCompletion {
            hideLoadingDialog(this@launchWithLoading)
        }.collect()
    })
}

/**
 * withLoading带不带Loading  默认是带  &&不需要监听数据变化
 */
fun <T> ILoading.launchAndCollect(
    requestBlock: suspend () -> Result<T>,
    listenerBuilder: ResultListener<T>.() -> Unit,
    withLoading: Boolean = true,
) {
    jobList.add(lifecycleScope.launch {
        launchFlow(requestBlock, {
            if (withLoading) {
                showLoadingDialog(this@launchAndCollect)
            }
        }, {
            if (withLoading) {
                hideLoadingDialog(this@launchAndCollect)
            }
        }).collect { response ->
            response.parseData(listenerBuilder)
        }
    })
}


/**
 * 需要监听数据变化
 */
fun <T> Flow<Result<T>>.collectIn(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    listenerBuilder: ResultListener<T>.() -> Unit,
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect { apiResponse: Result<T> ->
        apiResponse.parseData(listenerBuilder)
    }
}

