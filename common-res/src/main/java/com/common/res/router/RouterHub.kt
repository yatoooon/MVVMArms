package com.common.res.router

//路由组件路径
object RouterHub {

    //服务组件, 用于给每个组件暴露特有的服务
    const val SERVICE = "/service"

    //splash组件
    const val SPLASH: String = "/splash"

    //login组件
    const val LOGIN: String = "/login"

    //login组件
    const val TEMPLATE: String = "/template"

    //main组件
    const val MAIN: String = "/main"

    //uniapp组件
    const val UNIAPP: String = "/uniapp"

    //webframe组件
    const val WEBFRAME: String = "/webframe"

    //splash页
    const val PUBLIC_SPLASH: String = "$LOGIN/SplashActivity"

    //login页
    const val PUBLIC_LOGIN: String = "$LOGIN/loginPage"

    //login页
    const val PUBLIC_TEMPLATE: String = "$TEMPLATE/TemplateActivity"

    //main页
    const val PUBLIC_MAIN: String = "$MAIN/MainActivity"

    //web框架服务
    const val WEBFRAME_ERVICE: String = "$WEBFRAME$SERVICE/WebframeService"

}