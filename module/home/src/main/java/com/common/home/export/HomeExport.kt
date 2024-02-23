package com.common.home.export

import com.flyjingfish.module_communication_annotation.ExposeInterface


@ExposeInterface
interface HomeExport {
    companion object {
        private const val GROUP: String = "/Home"

         const val publicHomeMainActivity: String = "/Home/Activity/MainActivity"

    }
}