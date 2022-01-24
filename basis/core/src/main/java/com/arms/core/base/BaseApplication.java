package com.arms.core.base;

import android.content.Context;

import com.arms.core.base.delegate.BaseApplicationLifecycleDelegate;
import com.arms.common.ResApp;
import com.arms.common.aop.Log;
import com.arms.common.component.AppComponent;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;


/**
 *
 */
public class BaseApplication extends ResApp {

    @Inject
    AppComponent appComponent;

    private BaseApplicationLifecycleDelegate mBaseApplicationLifecycleDelegate;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (mBaseApplicationLifecycleDelegate == null) {
            mBaseApplicationLifecycleDelegate = new BaseApplicationLifecycleDelegate(base);
        }
        mBaseApplicationLifecycleDelegate.attachBaseContext(base);
    }

    @Log("启动耗时")
    @Override
    public void onCreate() {
        super.onCreate();
        if (mBaseApplicationLifecycleDelegate != null) {
            mBaseApplicationLifecycleDelegate.onCreate(this);
        }
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mBaseApplicationLifecycleDelegate != null) {
            mBaseApplicationLifecycleDelegate.onTerminate(this);
        }
    }

    @NotNull
    @Override
    public AppComponent getAppComponent() {
        return appComponent;
    }
}
