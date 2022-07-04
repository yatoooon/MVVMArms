package com.common.core.base.mvvm;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

/**
 * 懒加载Fragment
 *
 *
 */
public abstract class BaseVMLazyFragment<VDB extends ViewDataBinding,VM extends BaseViewModel> extends BaseVMFragment<VDB,VM> {
    /**
     * 是否可见
     */
    private boolean isVisible;
    /**
     * 是否初次加载
     */
    private boolean isFirstLoad;
    /**
     * 是否准备好加载
     */
    private boolean isPrepared;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        isFirstLoad = true;
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        lazyLoad();
    }

    /**
     * {@link Fragment}可见
     */
    private void onVisible() {
        isVisible = true;
        lazyLoad();
    }

    /**
     * {@link Fragment}不可见
     */
    private void onInvisible() {
        isVisible = false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onInvisible();
        } else {
            onVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInvisible();
        }

    }

    /**
     * 懒加载，触发{@link #onLazyLoad()}
     */
    private void lazyLoad() {
        if (isFirstLoad && isPrepared && isVisible) {
            //保证只加载一次
            isFirstLoad = false;
            onLazyLoad();
        }
    }

    /**
     * 懒加载
     */
    public abstract void onLazyLoad();
}
