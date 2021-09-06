package com.common.core.base.ibase;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

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
     *
     * @param savedInstanceState
     */
    void initData(@Nullable Bundle savedInstanceState);


    /**
     * 是否使用DataBinding
     *
     * @return
     */
    boolean isBinding();


}
