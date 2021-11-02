package com.common.core.base;

import android.content.Context;
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
import com.common.res.action.ToastAction;
import com.common.res.immersionbar.BindImmersionBar;

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
        ActivityAction, ResourcesAction, HandlerAction, ClickAction, BundleAction, KeyboardAction, ToastAction {


    /**
     * Activity 对象
     */
    private FragmentActivity mActivity;


    private VDB mBinding;


    private View mRootView;


    /**
     * 当前是否加载过
     */
    private boolean mLoading;


    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 获得全局的 Activity
        mActivity = requireActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        if (isBinding()) {
            mBinding = DataBindingUtil.bind(getRootView());
        }
        return mRootView;
    }

    public void initViewModel() {
    }

    public void initObserve() {
    }

    public void initView() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
        initObserve();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mLoading) {
            mLoading = true;
            initData();
            onFragmentResume(true);
            return;
        }
        if (mActivity != null && mActivity.getLifecycle().getCurrentState() == Lifecycle.State.STARTED) {
            onActivityResume();
        } else {
            onFragmentResume(false);
        }
    }

    /**
     * Fragment 可见回调
     *
     * @param first 是否首次调用
     */
    protected void onFragmentResume(boolean first) {

    }

    /**
     * Activity 可见回调
     */
    protected void onActivityResume() {

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
        mLoading = false;
        removeCallbacks();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    /**
     * 这个 Fragment 是否已经加载过了
     */
    public boolean isLoading() {
        return mLoading;
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
    public boolean isShowLoading() {
        FragmentActivity activity = requireActivity();
        if (activity instanceof BaseActivity) {
            return ((BaseActivity<?>) activity).isShowLoading();
        }
        return false;
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

    /**
     * 销毁当前 Fragment 所在的 Activity
     */
    public void finish() {
        if (mActivity == null || mActivity.isFinishing() || mActivity.isDestroyed()) {
            return;
        }
        mActivity.finish();
    }

}
