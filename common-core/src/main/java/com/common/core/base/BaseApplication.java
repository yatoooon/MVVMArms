package com.common.core.base;

import android.app.Application;
import android.content.Context;

import com.common.core.base.delegate.BaseApplicationLifecycleDelegate;
import com.common.core.di.component.AppComponent;

import javax.inject.Inject;


/**
 *
 */
public class BaseApplication extends Application {

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


    public AppComponent getAppComponent() {
        return appComponent;
    }
}
