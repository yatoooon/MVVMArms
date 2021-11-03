package com.common.res.router

//路由组件路径
object RouterHub {

    //服务组件, 用于给每个组件暴露特有的服务
    const val SERVICE = "/service"



    //login组件
    const val LOGIN: String = "/login"


    //main组件
    const val MAIN: String = "/main"

    //uniapp组件
    const val UNIAPP: String = "/uniapp"

    //webframe组件
    const val WEBFRAME: String = "/webframe"

   

    //login页
    const val PUBLIC_LOGIN: String = "$LOGIN/loginPage"


    //main页
    const val PUBLIC_MAIN: String = "$MAIN/MainActivity"

    //web框架服务
    const val WEBFRAME_ERVICE: String = "$WEBFRAME$SERVICE/WebframeService"




    //splash组件
    const val SPLASH: String = "/splash"

    //splash页
    const val PUBLIC_SPLASH: String = "$SPLASH/SplashActivity"

    //media组件
    const val MEDIA: String = "/media"

    //splash页
    const val PUBLIC_MEDIA_IMAGE_SELECT: String = "$MEDIA/ImageSelectActivity"

    const val HOME: String = "/home"

    //template组件
    const val TEMPLATE: String = "/template"

    const val PUBLIC_HOME_MAIN_ACTIVITY: String = "$HOME/activity/MainActivity"

    //template页
    const val PUBLIC_TEMPLATE_ACTIVITY: String = "$TEMPLATE/activity/TemplateActivity"

    const val PUBLIC_TEMPLATE_FRAGMENT_HOME: String = "$TEMPLATE/fragment/HomeFragment"
    const val PUBLIC_TEMPLATE_FRAGMENT_FOUND: String = "$TEMPLATE/fragment/FoundFragment"
    const val PUBLIC_TEMPLATE_FRAGMENT_MESSAGE: String = "$TEMPLATE/fragment/MessageFragment"
    const val PUBLIC_TEMPLATE_FRAGMENT_MINE: String = "$TEMPLATE/fragment/MineFragment"
}