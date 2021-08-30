package com.common.res.config

import android.content.Context
import com.common.core.config.FrameConfigModule
import com.common.core.di.module.ConfigModule

/**
 * 自定义全局配置
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class AppConfigModule : FrameConfigModule() {

    override fun applyOptions(context: Context, builder: ConfigModule.Builder) {
        builder.baseUrl(Constants.BASE_URL)
    }
}