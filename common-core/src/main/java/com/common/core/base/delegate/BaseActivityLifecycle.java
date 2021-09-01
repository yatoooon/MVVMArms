package com.common.core.base.delegate;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.common.core.util.AppManager;


public class BaseActivityLifecycle implements Application.ActivityLifecycleCallbacks {


    public BaseActivityLifecycle(){

    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        AppManager.getAppManager().setCurrentActivity(activity);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        if (AppManager.getAppManager().getCurrentActivity() == activity) {
            AppManager.getAppManager().setCurrentActivity(null);
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        AppManager.getAppManager().removeActivity(activity);
    }
}
