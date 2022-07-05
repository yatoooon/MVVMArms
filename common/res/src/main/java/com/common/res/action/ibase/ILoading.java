package com.common.res.action.ibase;

import androidx.lifecycle.LifecycleOwner;

/**
 *
 */
public interface ILoading extends LifecycleOwner {

    boolean isShowLoading();
    /**
     * 显示加载
     */
    void showLoadingDialog();

    /**
     * 隐藏加载
     */
    void hideLoadingDialog();

}
