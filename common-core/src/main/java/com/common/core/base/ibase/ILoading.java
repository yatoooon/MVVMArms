package com.common.core.base.ibase;

/**
 *
 */
public interface ILoading {

    boolean isShowLoading();
    /**
     * 显示加载
     */
    void showDialog();

    /**
     * 隐藏加载
     */
    void hideLoading();

}
