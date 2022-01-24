package com.arms.core.base.ibase;

/**
 *
 */
public interface ILoading {

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
