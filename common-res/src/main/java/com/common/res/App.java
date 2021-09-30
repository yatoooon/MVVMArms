package com.common.res;

import android.app.Application;

import com.common.res.component.AppComponent;

public abstract class App extends Application {
    public abstract AppComponent getAppComponent();

    static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static App getApp() {
        return app;
    }
}
