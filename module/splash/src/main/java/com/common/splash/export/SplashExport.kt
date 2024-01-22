package com.common.splash.export

import com.flyjingfish.module_communication_annotation.ExposeInterface


@ExposeInterface
interface SplashExport {
    companion object {
        const val SPLASH: String = "/splash"
        const val PUBLIC_SPLASH_SPLASHACTIVITY: String = "$SPLASH/SplashActivity"
        const val PUBLIC_SPLASH_GUIDEACTIVITY: String = "$SPLASH/GuideActivity"
    }
}