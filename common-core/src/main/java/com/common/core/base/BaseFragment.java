package com.common.core.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import com.common.core.base.ibase.ILoading;
import com.common.core.base.ibase.IView;
import com.common.res.action.ActivityAction;
import com.common.res.action.BundleAction;
import com.common.res.action.ClickAction;
import com.common.res.action.HandlerAction;
import com.common.res.action.KeyboardAction;
import com.common.res.action.ResourcesAction;

import java.util.List;


/**
 * MVVMFrame 框架基于Google官方的 JetPack 构建，在使用MVVMFrame时，需遵循一些规范：
 * <p>
 * 如果您继承使用了BaseFragment或其子类，你需要参照如下方式添加@AndroidEntryPoint注解
 *
 * @example Fragment
 * //-------------------------
 * @AndroidEntryPoint public class YourFragment extends BaseFragment {
 * <p>
 * }
 * //-------------------------
 */
public abstract class BaseFragment<VDB extends ViewDataBinding> extends Fragment implements IView, ILoading,
        ActivityAction, ResourcesAction, HandlerAction, ClickAction, BundleAction, KeyboardAction {
    /**
     * 请通过 {@link #getViewDataBinding()}获取，后续版本 {@link #mBinding}可能会私有化
     */
    private VDB mBinding;

    /**
     * 请通过 {@link #getRootView()} ()}获取，后续版本 {@link #mRootView}可能会私有化
     */
    private View mRootView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = createRootView(inflater, container, savedInstanceState);
        initView();
        initViewModel();
        initObserve();
        initViewClick();
        return mRootView;
    }

    public void initView() {
        if (isBinding()) {
            mBinding = DataBindingUtil.bind(getRootView());
        }
    }

    public void initViewModel() {
    }

    public void initObserve() {
    }

    public void initViewClick() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(savedInstanceState);
    }

    /**
     * 创建 {@link #mRootView}
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected View createRootView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView = null;
    }

    /**
     * 是否使用DataBinding
     *
     * @return 默认为true 表示使用。如果为false，则不会初始化 {@link #mBinding}。
     */
    @Override
    public boolean isBinding() {
        return true;
    }

    /**
     * 获取 ViewDataBinding
     *
     * @return {@link #mBinding}
     */
    public VDB getViewDataBinding() {
        return mBinding;
    }

    /**
     * 同 {@link #getViewDataBinding()}
     *
     * @return {@link #mBinding}
     */
    public VDB getBinding() {
        return mBinding;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBinding != null) {
            mBinding.unbind();
        }
        removeCallbacks();
    }


    /**
     * 获取rootView
     *
     * @return {@link #mRootView}
     */
    protected View getRootView() {
        return mRootView;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return getRootView().findViewById(id);
    }

    @Override
    public Bundle getBundle() {
        return getArguments();
    }

    @Override
    public void showLoading() {
        FragmentActivity activity = requireActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity<?>) activity).showLoading();
        }
    }

    @Override
    public void hideLoading() {
        FragmentActivity activity = requireActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity<?>) activity).hideLoading();
        }
    }

    /**
     * Fragment 按键事件派发
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            // 这个子 Fragment 必须是 BaseFragment 的子类，并且处于可见状态
            if (!(fragment instanceof BaseFragment) ||
                    fragment.getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                continue;
            }
            // 将按键事件派发给子 Fragment 进行处理
            if (((BaseFragment<?>) fragment).dispatchKeyEvent(event)) {
                // 如果子 Fragment 拦截了这个事件，那么就不交给父 Fragment 处理
                return true;
            }
        }
        switch (event.getAction()) {
            case KeyEvent.ACTION_DOWN:
                return onKeyDown(event.getKeyCode(), event);
            case KeyEvent.ACTION_UP:
                return onKeyUp(event.getKeyCode(), event);
            default:
                return false;
        }
    }

    /**
     * 按键按下事件回调
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 默认不拦截按键事件
        return false;
    }

    /**
     * 按键抬起事件回调
     */
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // 默认不拦截按键事件
        return false;
    }

}
