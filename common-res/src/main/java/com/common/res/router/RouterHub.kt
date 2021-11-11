package com.common.res.router

//路由组件路径
object RouterHub {

    const val MAIN: String = "/main"
    const val UNIAPP: String = "/uniapp"
    const val PUBLIC_MAIN: String = "$MAIN/MainActivity"
    const val SERVICE = "/service"


    const val LOGIN: String = "/login"
    const val PUBLIC_LOGIN_LOGINACTIVITY: String = "$LOGIN/LoginActivity"
    const val PUBLIC_LOGIN_REGISTERACTIVITY: String = "$LOGIN/RegisterActivity"
    const val PUBLIC_LOGIN_PASSWORDFORGETACTIVITY: String = "$LOGIN/PasswordForgetActivity"
    const val PUBLIC_LOGIN_PASSWORDRESETACTIVITY: String = "$LOGIN/PasswordResetActivity"
    const val PUBLIC_LOGIN_PHONERESETACTIVITY: String = "$LOGIN/PhoneResetActivity"

    const val PERSONAL: String = "/personal"
    const val PUBLIC_PERSONAL_PERSONALDATAACTIVITY: String = "$PERSONAL/PersonalDataActivity"
    const val PUBLIC_PERSONAL_SETTINGACTIVITY: String = "$PERSONAL/SettingActivity"


    const val WEB: String = "/web"
    const val WEB_SERVICE: String = "$WEB$SERVICE/WebframeService"
    const val PUBLIC_WEBPAGEACTIVITY: String = "$WEB/WebPageActivity"
    const val PUBLIC_WEBPAGEFRAGMENT: String = "$WEB/WebPageFrgment"


    const val SPLASH: String = "/splash"
    const val PUBLIC_SPLASH_SPLASHACTIVITY: String = "$SPLASH/SplashActivity"
    const val PUBLIC_SPLASH_GUIDEACTIVITY: String = "$SPLASH/GuideActivity"


    const val CRASH: String = "/crash"
    const val PUBLIC_CRASH_CRASHACTIVITY: String = "$CRASH/CrashActivity"
    const val PUBLIC_CRASH_RESTARTACTIVITY: String = "$CRASH/RestartActivity"


    const val MEDIA: String = "/media"
    const val PUBLIC_MEDIA_IMAGESELECTACTIVITY: String = "$MEDIA/ImageSelectActivity"
    const val PUBLIC_MEDIA_IMAGEPREVIEWACTIVITY: String = "$MEDIA/ImagePreviewActivity"
    const val PUBLIC_MEDIA_IMAGECROPACTIVITY: String = "$MEDIA/ImageCropActivity"
    const val PUBLIC_MEDIA_VIDEOSELECTACTIVITY: String = "$MEDIA/VideoSelectActivity"
    const val PUBLIC_MEDIA_VIDEOPLAYACTIVITY: String = "$MEDIA/VideoPlayActivity"
    const val PUBLIC_MEDIA_VIDEOPLAYACTIVITY_LANDSCAPE: String = "$MEDIA/VideoPlayActivity/Landscape"
    const val PUBLIC_MEDIA_VIDEOPLAYACTIVITY_PORTRAIT: String = "$MEDIA/VideoPlayActivity/Portrait"


    const val HOME: String = "/home"
    const val PUBLIC_HOME_MAINACTIVITY: String = "$HOME/activity/MainActivity"


    const val TEMPLATE: String = "/template"
    const val PUBLIC_TEMPLATE_ACTIVITY: String = "$TEMPLATE/activity/TemplateActivity"
    const val PUBLIC_DIALOG_ACTIVITY: String = "$TEMPLATE/activity/DialogActivity"
    const val PUBLIC_TEMPLATE_FRAGMENT_HOME: String = "$TEMPLATE/fragment/HomeFragment"
    const val PUBLIC_TEMPLATE_FRAGMENT_FOUND: String = "$TEMPLATE/fragment/FoundFragment"
    const val PUBLIC_TEMPLATE_FRAGMENT_MESSAGE: String = "$TEMPLATE/fragment/MessageFragment"
    const val PUBLIC_TEMPLATE_FRAGMENT_MINE: String = "$TEMPLATE/fragment/MineFragment"
}