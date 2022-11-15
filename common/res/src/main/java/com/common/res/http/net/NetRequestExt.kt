package com.common.res.http.net

import com.common.res.ResApp
import com.common.res.utils.AppManager
import com.common.res.utils.appLogoutToLogin
import com.common.res.utils.checkNetworkStatus
import com.hjq.toast.ToastUtils
import kotlinx.coroutines.Job


/**
 * 包装类
 */
@Suppress("UNCHECKED_CAST")
suspend fun <T> apiCall(call: suspend () -> BaseResponse<T>): Result<T?> {
    try {
        if (checkNetworkStatus(ResApp.getApp())) {
            val baseResponse = call()
            if (baseResponse.code == 200) {
                return Result.success(baseResponse as T)
            } else {
                ToastUtils.show(baseResponse.msg)
                return Result.failure(baseResponse.msg)
            }
        } else {
            val noNet = "请检查网络连接"
            ToastUtils.show(noNet)
            return Result.failure(noNet)
        }
    } catch (e: kotlinx.coroutines.CancellationException) {
        print("取消网络请求")
        return Result.failure(e.message)
    } catch (e: Exception) {
        e.printStackTrace()
        ToastUtils.show(e.message)
        return Result.failure(e.message)
    }
}

