package com.common.res.http.net

import com.common.res.ResApp
import com.common.res.utils.checkNetworkStatus
import com.hjq.toast.Toaster


/**
 * 包装类
 */
@Suppress("UNCHECKED_CAST")
suspend fun <T> apiCall(call: suspend () -> BaseResponse<T>): Result<T?> {
    try {
        if (checkNetworkStatus(ResApp.getApp())) {
            val baseResponse = call()
            if (baseResponse.code == 200) {
                return Result.success( baseResponse as T)
            } else {
                Toaster.show(baseResponse.msg)
                return Result.failure(baseResponse.msg)
            }
        } else {
            val noNet = "请检查网络连接"
            Toaster.show(noNet)
            return Result.failure(noNet)
        }
    } catch (e: kotlinx.coroutines.CancellationException) {
        e.printStackTrace()
        print("取消网络请求")
        return Result.failure(e.message)
    } catch (e: Exception) {
        e.printStackTrace()
        Toaster.show(e.message)
        return Result.failure(e.message)
    }
}

