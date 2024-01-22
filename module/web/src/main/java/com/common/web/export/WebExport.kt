package com.common.web.export

import com.flyjingfish.module_communication_annotation.ExposeInterface


@ExposeInterface
interface WebExport {
    companion object {
        const val SERVICE = "/service"
        const val WEB: String = "/web"
        const val PUBLIC_WEBPAGEACTIVITY: String = "$WEB/WebPageActivity"
        const val PUBLIC_WEBPAGEFRAGMENT: String = "$WEB/WebPageFrgment"
    }
}