package com.common.core.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import com.common.core.R;
import com.common.res.action.ActivityAction;
import com.common.res.action.BundleAction;
import com.common.res.action.ClickAction;
import com.common.res.action.HandlerAction;
import com.common.res.action.KeyboardAction;
import com.common.res.action.ResourcesAction;
import com.common.res.action.ToastAction;
import com.common.res.action.ibase.ILoading;
import com.common.res.action.ibase.IView;

import java.util.List;

import kotlinx.coroutines.Job;


public abstract class BaseDialogFragment<VDB extends ViewDataBinding> extends DialogFragment implements IView, ILoading, ActivityAction, ResourcesAction, HandlerAction, ClickAction, BundleAction, KeyboardAction, ToastAction {


    /**
     * Activity 对象
     */
    public BaseActivity mActivity;


    private VDB mBinding;


    private View mRootView;


    /**
     * 当前是否加载过
     */
    private boolean mLoading;


    protected static final float DEFAULT_WIDTH_RATIO = 1f;
    protected static final float DEFAULT_HEIGHT_RATIO = 1f;

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 获得全局的 Activity
        if (requireActivity() instanceof BaseActivity) {
            mActivity = (BaseActivity) requireActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initDialog(getDialog());
        mRootView = inflater.inflate(getLayoutId(), container, false);
        if (isBinding()) {
            mBinding = DataBindingUtil.bind(getRootView());
            if (mBinding != null) {
                mBinding.setLifecycleOwner(this);
            }
        }
        return mRootView;
    }

    protected void initDialog(Dialog dialog) {
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            dialog.setOnShowListener(dialogInterface -> {
                fullScreenImmersive(dialog.getWindow().getDecorView());
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            });
        }
    }

    private void fullScreenImmersive(View view) {
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOptions);
    }

    protected void initWindow(Window window) {
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            window.getAttributes().windowAnimations = R.style.core_dialog_animation;
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.alpha = 1.0f;
            attributes.dimAmount = 0.0f;
            window.setAttributes(attributes);
            setWindow(window, Gravity.NO_GRAVITY, DEFAULT_WIDTH_RATIO, DEFAULT_HEIGHT_RATIO, 0, 0, 0, 0, 0, 0);
        }
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
        initViewModel();
        initView();
        initObserve();
        initData();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWindow(getDialog().getWindow());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mLoading) {
            mLoading = true;
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

    private DialogInterface.OnDismissListener mOnClickListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        this.mOnClickListener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnClickListener != null) {
            mOnClickListener.onDismiss(dialog);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBinding != null) {
            mBinding.unbind();
            mBinding = null;
        }
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

    @Override
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
    public void showLoadingDialog(ILoading loading) {
        FragmentActivity activity = requireActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity<?>) activity).showLoadingDialog(loading);
        }
    }

    @Override
    public void hideLoadingDialog(ILoading loading) {
        FragmentActivity activity = requireActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity<?>) activity).hideLoadingDialog(loading);
        }
    }

    /**
     * Fragment 按键事件派发
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            // 这个子 Fragment 必须是 BaseFragment 的子类，并且处于可见状态
            if (!(fragment instanceof BaseFragment) || fragment.getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
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

    /**
     * 设置 Window 布局相关参数
     *
     * @param window           {@link Window}
     * @param gravity          Dialog的对齐方式
     * @param widthRatio       宽度比例，根据屏幕宽度计算得来
     * @param x                x轴偏移量，需与 gravity 结合使用
     * @param y                y轴偏移量，需与 gravity 结合使用
     * @param horizontalMargin 水平方向边距
     * @param verticalMargin   垂直方向边距
     * @param horizontalWeight 水平方向权重
     * @param verticalWeight   垂直方向权重
     */
    protected void setWindow(Window window, int gravity, float widthRatio, float heightRatio, int x, int y, float horizontalMargin, float verticalMargin, float horizontalWeight, float verticalWeight) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (getWidthPixels() * widthRatio);
        lp.height = (int) (getHeightPixels() * heightRatio);
        lp.gravity = gravity;
        lp.x = x;
        lp.y = y;
        lp.horizontalMargin = horizontalMargin;
        lp.verticalMargin = verticalMargin;
        lp.horizontalWeight = horizontalWeight;
        lp.verticalWeight = verticalWeight;
        window.setAttributes(lp);
    }


    protected DisplayMetrics getDisplayMetrics() {
        return getResources().getDisplayMetrics();
    }

    protected int getWidthPixels() {
        return getDisplayMetrics().widthPixels;
    }

    protected int getHeightPixels() {
        return getDisplayMetrics().heightPixels;
    }
}
