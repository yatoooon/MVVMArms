package com.common.res.config

import cn.vove7.smartkey.AConfig
import cn.vove7.smartkey.annotation.Config
import cn.vove7.smartkey.key.smartKey
import com.common.res.smartkey.MmkvSettingsImpl

@Config(implCls = MmkvSettingsImpl::class)
object AppConfig : AConfig() {
    var username: String by smartKey("")
    var password: String by smartKey("")
    var login: Boolean by smartKey(false)
    var token: String by smartKey("")
    var userInfoEntity: UserInfoEntity? by smartKey(null)
    fun clearUserData() {
        username = ""
        password = ""
        login = false
        token = ""
        userInfoEntity = null
    }
}