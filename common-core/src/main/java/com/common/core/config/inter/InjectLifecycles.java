package com.common.core.config.inter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.common.core.base.delegate.BaseApplicationLifecycle;

import java.util.List;


//配置类的接口，需要在androidmanifest中声明它的实现类

/**
 * ================================================
 * {@link InjectLifecycles} 可以给框架配置一些参数,需要实现 {@link InjectLifecycles} 后,在 AndroidManifest 中声明该实现类
 * ================================================
 */
public interface InjectLifecycles {

    /**
     * 使用 {@link BaseApplicationLifecycle} 在 {@link Application} 的生命周期中注入一些操作
     *
     * @param context    {@link Context}
     * @param lifecycles {@link Application} 的生命周期容器, 可向框架中添加多个 {@link Application} 的生命周期类
     */
    void injectAppLifecycle(@NonNull Context context, @NonNull List<BaseApplicationLifecycle> lifecycles);

    /**
     * 使用 {@link Application.ActivityLifecycleCallbacks} 在 {@link Activity} 的生命周期中注入一些操作
     *
     * @param context    {@link Context}
     * @param lifecycles {@link Activity} 的生命周期容器, 可向框架中添加多个 {@link Activity} 的生命周期类
     */
    void injectActivityLifecycle(@NonNull Context context, @NonNull List<Application.ActivityLifecycleCallbacks> lifecycles);

    /**
     * 使用 {@link FragmentManager.FragmentLifecycleCallbacks} 在 {@link Fragment} 的生命周期中注入一些操作
     *
     * @param context    {@link Context}
     * @param lifecycles {@link Fragment} 的生命周期容器, 可向框架中添加多个 {@link Fragment} 的生命周期类
     */
    void injectFragmentLifecycle(@NonNull Context context, @NonNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycles);
}
