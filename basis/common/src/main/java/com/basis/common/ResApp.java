package com.basis.common;

import android.app.Application;

import com.basis.common.component.AppComponent;
import com.basis.common.component.AppComponent;

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
