package com.common.res.utils

import android.os.Bundle
import com.common.res.config.AppConfig

/**
 * 登出
 */
fun appLogout() {
    checkLogin {
        if (it) {
            AppConfig.clearUserData()
            AppManager.getAppManager().killAll()
        }
    }
}

/**
 * 登出并跳转到登录页面
 */
fun appLogoutToLogin() {
    checkLogin {
        if (it) {
            appLogout()
//            routerNavigation(RouterHub.PUBLIC_LOGINACTIVITY)
        }
    }
}

/**
 * 登出并跳转到登录界面,并自动登录
 * @param username
 * @param password
 */
fun appLogoutAutoLogin(username: String?, password: String?) {
    checkLogin {
        if (it) {
            appLogout()
            var bundle = Bundle().apply {
                putString("username", username)
                putString("password", password)
            }
//            routerNavigation(RouterHub.PUBLIC_LOGINACTIVITY, bundle)
        }
    }
}

/**
 * 检查登录
 */
fun checkLogin(block: (it: Boolean) -> Unit) {
    block(AppConfig.login)
}