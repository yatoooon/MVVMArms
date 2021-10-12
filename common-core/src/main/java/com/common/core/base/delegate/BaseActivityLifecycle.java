package com.common.core.base.delegate;

import static com.common.core.base.delegate.BaseApplicationLifecycleDelegate.mApplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks;

import com.common.core.config.CoreConfigModule;
import com.common.core.config.ManifestParser;
import com.common.res.utils.AppManager;

import java.util.ArrayList;
import java.util.List;



public class BaseActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    public BaseActivityLifecycle() {

    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        boolean isNotAdd = false;
        if (activity.getIntent() != null) {
            isNotAdd = activity.getIntent().getBooleanExtra(AppManager.IS_NOT_ADD_ACTIVITY_LIST, false);
        }
        if (!isNotAdd) {
            AppManager.getAppManager().addActivity(activity);
        }
        registerFragmentCallbacks(activity);
    }

    private void registerFragmentCallbacks(Activity activity) {

        if (activity instanceof FragmentActivity) {
            List<CoreConfigModule> modules = new ManifestParser(activity.getApplicationContext()).parse();
            ArrayList<FragmentLifecycleCallbacks> fragmentLifecycleCallbacksList = new ArrayList<>();
            for (CoreConfigModule module : modules) {
                module.injectFragmentLifecycle(mApplication, fragmentLifecycleCallbacksList);
            }
            for (FragmentLifecycleCallbacks lifecycleCallbacks :
                    fragmentLifecycleCallbacksList) {
                ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(lifecycleCallbacks, true);
            }
        }
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
