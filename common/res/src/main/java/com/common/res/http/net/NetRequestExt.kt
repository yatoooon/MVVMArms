package com.common.res.http.net

import com.common.res.ResApp
import com.common.res.utils.checkNetworkStatus
import com.hjq.toast.ToastUtils


/**
 * 包装类
 */
suspend fun <T> apiCall(call: suspend () -> T): Result<T?> {
    return try {
        if (checkNetworkStatus(ResApp.getApp())) {
            val entity = call()
            if (entity != null) {
                Result.success(entity)
            } else {
                Result.failure("返回null")
            }
        } else {
            Result.failure("请检查网络连接")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ToastUtils.show(e.message.toString())
        Result.error(e)
    }
}

