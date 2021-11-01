package com.common.res.router

//路由组件路径
object RouterHub {

    //服务组件, 用于给每个组件暴露特有的服务
    const val SERVICE = "/service"

    //splash组件
    const val SPLASH: String = "/splash"

    //login组件
    const val LOGIN: String = "/login"


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


    //main页
    const val PUBLIC_MAIN: String = "$MAIN/MainActivity"

    //web框架服务
    const val WEBFRAME_ERVICE: String = "$WEBFRAME$SERVICE/WebframeService"


    //template组件
    const val TEMPLATE: String = "/template"

    //template页
    const val PUBLIC_TEMPLATE_ACTIVITY: String = "$TEMPLATE/activity/TemplateActivity"

    const val PUBLIC_TEMPLATE_FRAGMENT_HOME: String = "$TEMPLATE/fragment/HomeFragment"
    const val PUBLIC_TEMPLATE_FRAGMENT_FOUND: String = "$TEMPLATE/fragment/FoundFragment"
    const val PUBLIC_TEMPLATE_FRAGMENT_MESSAGE: String = "$TEMPLATE/fragment/MessageFragment"
    const val PUBLIC_TEMPLATE_FRAGMENT_ME: String = "$TEMPLATE/fragment/MeFragment"
}