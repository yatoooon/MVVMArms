package com.common.res.action.ibase;

import androidx.annotation.LayoutRes;

public interface IView {

    /**
     * 根布局id
     *
     * @return
     */
    @LayoutRes
    int getLayoutId();

    /**
     * 初始化数据
     */
    void initData();


    /**
     * 是否使用DataBinding
     *
     * @return
     */
    boolean isBinding();


}
