package com.common.login.export

import com.flyjingfish.module_communication_annotation.ExposeInterface


@ExposeInterface
interface LoginExport {
    companion object {
        const val LOGIN: String = "/login"
        const val PUBLIC_LOGIN_LOGINACTIVITY: String = "$LOGIN/LoginActivity"
        const val PUBLIC_LOGIN_REGISTERACTIVITY: String = "$LOGIN/RegisterActivity"
        const val PUBLIC_LOGIN_PASSWORDFORGETACTIVITY: String = "$LOGIN/PasswordForgetActivity"
        const val PUBLIC_LOGIN_PASSWORDRESETACTIVITY: String = "$LOGIN/PasswordResetActivity"
        const val PUBLIC_LOGIN_PHONERESETACTIVITY: String = "$LOGIN/PhoneResetActivity"
    }
}