package com.common.core.base.ibase;

import com.common.core.base.BaseApplication;

/**
 * 用于模块/组件的各个 Module 在 Application 中初始化一些配置
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface IComponentApp {
    /**
     * 用于模块/组件的初始化
     *
     * @param baseApplication
     */
    void onCreate(BaseApplication baseApplication);

    default void onTerminate() {

    }
}
