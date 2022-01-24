package com.arms.core.base.delegate;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * 用于模块/组件的各个 Module 在 Application 中初始化一些配置
 *
 *
 */
public interface BaseApplicationLifecycle {
    void attachBaseContext(@NonNull Context base);

    void onCreate(@NonNull Application application);

    void onTerminate(@NonNull Application application);
}
