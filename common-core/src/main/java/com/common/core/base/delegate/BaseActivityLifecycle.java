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
import com.common.res.utils.AppManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class BaseActivityLifecycle implements Application.ActivityLifecycleCallbacks {


    @Inject
    public List<CoreConfigModule> modules;

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
            //mFragmentLifecycle 为 Fragment 生命周期实现类, 用于框架内部对每个 Fragment 的必要操作, 如给每个 Fragment 配置 FragmentDelegate
            //注册框架内部已实现的 Fragment 生命周期逻辑
            ArrayList<FragmentLifecycleCallbacks> fragmentLifecycleCallbacksList = new ArrayList<>();
            if (modules != null) {
                for (CoreConfigModule module : modules) {
                    module.injectFragmentLifecycle(mApplication, fragmentLifecycleCallbacksList);
                }
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
