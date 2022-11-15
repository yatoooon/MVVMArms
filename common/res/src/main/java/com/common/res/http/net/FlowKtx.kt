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
 * withLoading带不带Loading  默认是带  是否返回实体类&&不需要监听数据变化
 */
fun <T> ILoading.launch(
    requestBlock: suspend () -> Result<T>,       //发起请求
    listenerBuilder: (ResultListener<T>.() -> Unit)? = null,  //是否返回实体
    withLoading: Boolean = true,                 //是否显示loading
    isOnRepeat: Boolean = false,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    jobList.add(lifecycleScope.launch {
        launchFlow(requestBlock, {
            if (withLoading) {
                showLoadingDialog(this@launch)
            }
        }, {
            if (withLoading) {
                hideLoadingDialog(this@launch)
            }
        }).let {
            if (isOnRepeat) {
                it.flowWithLifecycle(lifecycle, minActiveState)
            } else {
                it
            }
        }.collect { response ->
            listenerBuilder?.let {
                response.parseData(it)
            }
        }
    })
}



