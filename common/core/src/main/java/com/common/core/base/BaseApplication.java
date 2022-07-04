package com.common.core.base;

import android.content.Context;

import com.common.core.base.delegate.BaseApplicationLifecycleDelegate;
import com.common.res.ResApp;
import com.common.res.aop.Log;
import com.common.res.component.AppComponent;

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
