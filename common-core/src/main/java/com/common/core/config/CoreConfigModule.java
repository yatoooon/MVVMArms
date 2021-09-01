package com.common.core.config;


/**
 *
 */
public abstract class CoreConfigModule implements AppliesOptions , InjectLifecycles {

    /**
     * 是否启用解析配置
     * @return 默认返回{@code true}
     */
    public boolean isManifestParsingEnabled() {
        return true;
    }

}
