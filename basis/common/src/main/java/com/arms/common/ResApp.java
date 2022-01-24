package com.arms.common;

import android.app.Application;

import com.arms.common.component.AppComponent;
import com.arms.common.component.AppComponent;

public abstract class ResApp extends Application {
    public abstract AppComponent getAppComponent();

    static ResApp resApp;

    @Override
    public void onCreate() {
        super.onCreate();
        resApp = this;
    }

    public static ResApp getApp() {
        return resApp;
    }
}
