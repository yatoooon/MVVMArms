package com.common.res.http.net

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.common.res.action.ibase.ILoading
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
    lifecycleScope.launch {
        flow {
            emit(requestBlock())
        }.onStart {
            showLoadingDialog()
        }.onCompletion {
            hideLoadingDialog()
        }.collect()
    }
}

/**
 * 不带Loading&&不需要监听数据变化
 */
fun <T> ILoading.launchAndCollect(
    requestBlock: suspend () -> Result<T>,
    listenerBuilder: ResultListener<T>.() -> Unit
) {
    lifecycleScope.launch {
        launchFlow(requestBlock).collect { response ->
            response.parseData(listenerBuilder)
        }
    }
}

/**
 * 带Loading&&不需要监听数据变化
 */
fun <T> ILoading.launchWithLoadingAndCollect(
    requestBlock: suspend () -> Result<T>,
    listenerBuilder: ResultListener<T>.() -> Unit
) {
    lifecycleScope.launch {
        launchFlow(requestBlock, { showLoadingDialog() }, { hideLoadingDialog() }).collect { response ->
            response.parseData(listenerBuilder)
        }
    }
}


/**
 * 需要监听数据变化
 */
fun <T> Flow<Result<T>>.launchAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    listenerBuilder: ResultListener<T>.() -> Unit,
) {
    if (owner is Fragment) {
        owner.viewLifecycleOwner.lifecycleScope.launch {
            owner.viewLifecycleOwner.repeatOnLifecycle(minActiveState) {
                collect { result: Result<T> ->
                    result.parseData(listenerBuilder)
                }
            }
        }
    } else {
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(minActiveState) {
                collect { result: Result<T> ->
                    result.parseData(listenerBuilder)
                }
            }
        }
    }
}



