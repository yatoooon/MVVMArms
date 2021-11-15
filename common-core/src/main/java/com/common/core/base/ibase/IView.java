package com.common.core.base.ibase;

import androidx.annotation.LayoutRes;

import com.common.core.base.BaseActivity;
import com.common.core.base.BaseFragment;

/**
 * 用来规范{@link BaseActivity} 和{@link BaseFragment} 风格。
 *
 *
 */
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
