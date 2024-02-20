package com.common.core.base.mvvm;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;

/**
 * 懒加载Fragment
 */
public abstract class BaseVMLazyFragment<VDB extends ViewDataBinding, VM extends BaseViewModel> extends BaseVMFragment<VDB, VM> {
    /**
     * 是否初次加载
     */
    private boolean isFirstLoad = true;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLazyLoad();
    }

    private void initLazyLoad() {
        getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_RESUME) {
                if (isFirstLoad) {
                    onLazyLoad();
                    isFirstLoad = false;
                }
            }
        });
    }

    /**
     * 懒加载
     */
    public abstract void onLazyLoad();
}
