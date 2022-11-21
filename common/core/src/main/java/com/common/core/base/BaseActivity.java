package com.common.core.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.LifecycleOwnerKt.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.common.res.action.ActivityAction;
import com.common.res.action.BundleAction;
import com.common.res.action.ClickAction;
import com.common.res.action.HandlerAction;
import com.common.res.action.KeyboardAction;
import com.common.res.action.TitleBarAction;
import com.common.res.action.ToastAction;
import com.common.res.action.ibase.ILoading;
import com.common.res.action.ibase.IView;
import com.common.res.dialog.BaseDialog;
import com.common.res.dialog.WaitDialog;
import com.common.res.immersionbar.BindImmersionBar;
import com.common.res.utils.ScreenUtilKt;
import com.hjq.bar.TitleBar;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;


/**
 * MVVMFrame 框架基于Google官方的 JetPack 构建，在使用MVVMFrame时，需遵循一些规范：
 * <p>
 * 如果您继承使用了BaseActivity或其子类，你需要参照如下方式添加@AndroidEntryPoint注解
 *
 * @example Activity
 * //-------------------------
 * @AndroidEntryPoint public class YourActivity extends BaseActivity {
 * <p>
 * }
 * //-------------------------
 */
public abstract class BaseActivity<VDB extends ViewDataBinding> extends AppCompatActivity implements IView, ILoading, BindImmersionBar, ActivityAction, ClickAction, HandlerAction, BundleAction, KeyboardAction, ToastAction, TitleBarAction {


    private VDB mBinding;

    /**
     * 错误结果码
     */
    public static final int RESULT_ERROR = -2;
    /**
     * Activity 回调集合
     */
    private SparseArray<OnActivityCallback> mActivityCallbacks;

    /**
     * 加载对话框
     */
    private BaseDialog mDialog;
    /**
     * 对话框数量
     */
    private int mDialogCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initViewModel();
        initView();
        initObserve();
        initData();
    }


    /**
     * 初始化ContentView，{@link #setContentView(int)} }
     */
    public void setContentView() {
        if (getLayoutId() != 0) {
            if (isBinding()) {
                mBinding = DataBindingUtil.setContentView(this, getLayoutId());
                if (mBinding != null) {
                    mBinding.setLifecycleOwner(this);
                }
            } else {
                setContentView(getLayoutId());
            }
            if (getTitleBar() != null) {
                getTitleBar().setOnTitleBarListener(this);
            }
            initSoftKeyboard();
        }
    }

    @Nullable
    @Override
    public TitleBar getTitleBar() {
        return obtainTitleBar(getContentView());
    }

    @Override
    public void onLeftClick(TitleBar titleBar) {
        onBackPressed();
    }


    /**
     * 初始化软键盘
     */
    protected void initSoftKeyboard() {
        // 点击外部隐藏软键盘，提升用户体验
        getContentView().setOnClickListener(v -> {
            // 隐藏软键，避免内存泄漏
            hideKeyboard(getCurrentFocus());
        });
    }

    /**
     * 和 setContentView 对应的方法
     */
    public ViewGroup getContentView() {
        return findViewById(Window.ID_ANDROID_CONTENT);
    }

    public void initView() {

    }

    public void initViewModel() {

    }

    public void initObserve() {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0); //finish时的默认动画需要去掉
        // 隐藏软键，避免内存泄漏
        hideKeyboard(getCurrentFocus());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBinding != null) {
            mBinding.unbind();
            mBinding = null;
        }
        removeCallbacks();
        if (isShowLoading()) {
            hideLoadingDialog(this);
        }
        mDialog = null;
        
    }

    /**
     * 如果当前的 Activity（singleTop 启动模式） 被复用时会回调
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 设置为当前的 Intent，避免 Activity 被杀死后重启 Intent 还是最原先的那个
        setIntent(intent);
    }

    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
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


    /**
     * 当前加载对话框是否在显示中
     */
    @Override
    public boolean isShowLoading() {
        return mDialog != null && mDialog.isShowing();
    }

    @Override
    public void showLoadingDialog(ILoading loading) {
        if (isFinishing() || isDestroyed()) {
            return;
        }

        mDialogCount++;
        if (mDialogCount <= 0 || isFinishing() || isDestroyed()) {
            return;
        }

        if (mDialog == null) {
            mDialog = new WaitDialog.Builder(this).setCancelable(false).create();
        }
        mDialog.setOnKeyListener((dialog, event) -> {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                for (Job job : loading.getJobList()) {
                    job.cancel(null);
                }
                return true;
            } else {
                return false;
            }
        });
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void hideLoadingDialog(ILoading loading) {
        if (isFinishing() || isDestroyed()) {
            return;
        }

        if (mDialogCount > 0) {
            mDialogCount--;
        }
        postDelayed(() -> {
            if (mDialogCount != 0 || mDialog == null || !mDialog.isShowing()) {
                return;
            }
            mDialog.setOnKeyListener((dialog, event) -> false);
            mDialog.dismiss();
        }, 300);
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            // 这个 Fragment 必须是 BaseFragment 的子类，并且处于可见状态
            if (!(fragment instanceof BaseFragment) || fragment.getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                continue;
            }
            // 将按键事件派发给 Fragment 进行处理
            if (((BaseFragment<?>) fragment).dispatchKeyEvent(event)) {
                // 如果 Fragment 拦截了这个事件，那么就不交给 Activity 处理
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        // 隐藏软键，避免内存泄漏
        hideKeyboard(getCurrentFocus());
        // 查看源码得知 startActivity 最终也会调用 startActivityForResult
        super.startActivityForResult(intent, requestCode, options);
    }

    /**
     * startActivityForResult 方法优化
     */

    public void startActivityForResult(Class<? extends Activity> clazz, OnActivityCallback callback) {
        startActivityForResult(new Intent(this, clazz), null, callback);
    }

    public void startActivityForResult(Intent intent, OnActivityCallback callback) {
        startActivityForResult(intent, null, callback);
    }

    public void startActivityForResult(Intent intent, @Nullable Bundle options, OnActivityCallback callback) {
        if (mActivityCallbacks == null) {
            mActivityCallbacks = new SparseArray<>(1);
        }
        // 请求码必须在 2 的 16 次方以内
        int requestCode = new Random().nextInt((int) Math.pow(2, 16));
        mActivityCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode, options);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        OnActivityCallback callback;
        if (mActivityCallbacks != null && (callback = mActivityCallbacks.get(requestCode)) != null) {
            callback.onActivityResult(resultCode, data);
            mActivityCallbacks.remove(requestCode);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public interface OnActivityCallback {

        /**
         * 结果回调
         *
         * @param resultCode 结果码
         * @param data       数据
         */
        void onActivityResult(int resultCode, @Nullable Intent data);
    }


    //activity 默认DEFAULT
    @Override
    public int getImmersionBarType() {
        return DEFAULT;
    }

    //activity 默认状态栏黑色字体
    @Override
    public boolean isStatusBarDarkFont() {
        return true;
    }
}
