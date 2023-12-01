package com.common.login.export.arouter


object LoginRouterHub {

    const val SERVICE = "/service"


    const val LOGIN: String = "/login"
    const val PUBLIC_LOGIN_LOGINACTIVITY: String = "$LOGIN/LoginActivity"
    const val PUBLIC_LOGIN_REGISTERACTIVITY: String = "$LOGIN/RegisterActivity"
    const val PUBLIC_LOGIN_PASSWORDFORGETACTIVITY: String = "$LOGIN/PasswordForgetActivity"
    const val PUBLIC_LOGIN_PASSWORDRESETACTIVITY: String = "$LOGIN/PasswordResetActivity"
    const val PUBLIC_LOGIN_PHONERESETACTIVITY: String = "$LOGIN/PhoneResetActivity"

}