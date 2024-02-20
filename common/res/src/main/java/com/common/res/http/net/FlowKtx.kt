package com.common.res.http.net

import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import androidx.lifecycle.Lifecycle
import com.common.res.action.ibase.ILoading
import com.hjq.toast.Toaster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

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
    }.flowOn(Dispatchers.IO) // 确保 flow 中的操作在 IO 线程执行
}

fun <T> ILoading.launch(
    requestBlock: suspend () -> Result<T>,       // 发起请求
    listenerBuilder: (ResultListener<T>.() -> Unit)? = null,  // 是否返回实体
    withLoading: Boolean = true,                 // 是否显示loading
    isOnRepeat: Boolean = false,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    clearJobList()
    jobList.add(lifecycleScope.launch(Dispatchers.IO) {
        launchFlow(requestBlock = requestBlock,
            startCallback = {
                if (withLoading) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        showLoadingDialog(this@launch)
                    }
                }
            }, completeCallback = {
                if (withLoading) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        hideLoadingDialog(this@launch)
                    }
                }
            })
            .catch { e ->
                withContext(Dispatchers.Main) {
                    e.printStackTrace()
                    Toaster.show(e.message)
                }
            }.let { flow ->
                if (isOnRepeat) {
                    flow.flowWithLifecycle(lifecycle, minActiveState)
                } else {
                    flow
                }
            }.collect { response ->
                withContext(Dispatchers.Main) {
                    listenerBuilder?.let {
                        response.parseData(it)
                    }
                }
            }
    })
}






