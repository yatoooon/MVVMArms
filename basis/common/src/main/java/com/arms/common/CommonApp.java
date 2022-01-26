package com.arms.common;

import android.app.Application;

import com.arms.common.component.AppComponent;

public abstract class CommonApp extends Application {
    public abstract AppComponent getAppComponent();

    static CommonApp commonApp;

    @Override
    public void onCreate() {
        super.onCreate();
        commonApp = this;
    }

    public static CommonApp getApp() {
        return commonApp;
    }
}
