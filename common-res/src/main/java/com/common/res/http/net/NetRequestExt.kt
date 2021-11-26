package com.common.res.http.net

import com.common.res.ResApp
import com.common.res.utils.checkNetworkStatus


/**
 * 包装类
 */
suspend fun <T> apiCall(call: suspend () -> T): Resource<T?> {
    return try {
        if (checkNetworkStatus(ResApp.getApp())) {
            val entity = call()
            if (entity != null) {
                Resource.success(entity)
            } else {
                Resource.failure("返回null")
            }
        } else {
            Resource.failure("请检查网络连接")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Resource.error(e)
    }
}

