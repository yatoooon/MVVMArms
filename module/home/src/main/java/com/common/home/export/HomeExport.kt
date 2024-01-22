package com.common.home.export

import com.flyjingfish.module_communication_annotation.ExposeInterface


@ExposeInterface
interface HomeExport {
    companion object {
        const val HOME: String = "/home"
        const val PUBLIC_HOME_MAINACTIVITY: String = "$HOME/activity/MainActivity"
    }
}