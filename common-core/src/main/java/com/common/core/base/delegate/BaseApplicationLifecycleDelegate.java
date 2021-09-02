package com.common.core.base.delegate;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.common.core.config.CoreConfigModule;
import com.common.core.config.ManifestParser;
import com.common.core.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 用于模块/组件的各个 Module 在 Application 中初始化一些配置
 */
public class BaseApplicationLifecycleDelegate implements BaseApplicationLifecycle {

    public Application.ActivityLifecycleCallbacks mBaseActivityLifecycle = new BaseActivityLifecycle();
    private List<BaseApplicationLifecycle> mBaseApplicationLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();
    public static Application mApplication;


    public BaseApplicationLifecycleDelegate(@NonNull Context context) {
        List<CoreConfigModule> modules = new ManifestParser(context).parse();
        for (CoreConfigModule coreConfigModule : modules) {
            coreConfigModule.injectAppLifecycle(context, mBaseApplicationLifecycles);
            coreConfigModule.injectActivityLifecycle(context, mActivityLifecycles);
        }
    }

    @Override
    public void attachBaseContext(@NonNull Context base) {
        for (BaseApplicationLifecycle lifecycle : mBaseApplicationLifecycles) {
            lifecycle.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate(@NonNull Application application) {
        mApplication = application;
        for (BaseApplicationLifecycle lifecycle : mBaseApplicationLifecycles) {
            lifecycle.onCreate(mApplication);
        }
        application.registerActivityLifecycleCallbacks(mBaseActivityLifecycle);
        for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : mActivityLifecycles) {
            application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {
        mApplication.unregisterActivityLifecycleCallbacks(mBaseActivityLifecycle);
        if (mActivityLifecycles != null && mActivityLifecycles.size() > 0) {
            for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
                mApplication.unregisterActivityLifecycleCallbacks(lifecycle);
            }
        }
        if (mBaseApplicationLifecycles != null && mBaseApplicationLifecycles.size() > 0) {
            for (BaseApplicationLifecycle lifecycle : mBaseApplicationLifecycles) {
                lifecycle.onTerminate(mApplication);
            }
        }
        this.mActivityLifecycles = null;
        this.mBaseActivityLifecycle = null;
        this.mBaseApplicationLifecycles = null;
    }


}
