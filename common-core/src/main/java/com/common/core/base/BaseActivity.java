package com.common.core.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import timber.log.Timber;

import com.common.core.R;
import com.common.core.base.ibase.ILoading;
import com.common.core.base.ibase.IView;


/**
 * MVVMFrame 框架基于Google官方的 JetPack 构建，在使用MVVMFrame时，需遵循一些规范：
 * <p>
 * 如果您继承使用了BaseActivity或其子类，你需要参照如下方式添加@AndroidEntryPoint注解
 *
 *
 * @example Activity
 * //-------------------------
 * @AndroidEntryPoint public class YourActivity extends BaseActivity {
 * <p>
 * }
 * //-------------------------
 */
public abstract class BaseActivity<VDB extends ViewDataBinding> extends AppCompatActivity implements IView, ILoading {

    /**
     * 请通过 {@link #getViewDataBinding()}获取，后续版本 {@link #mBinding}可能会私有化
     */
    private VDB mBinding;

    protected static final float DEFAULT_WIDTH_RATIO = 0.85f;

    private Dialog mDialog;

    private Dialog mProgressDialog;

    private String mJumpTag;
    private long mJumpTime;

    private static final long IGNORE_INTERVAL_TIME = 500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initViewModel();
        initData(savedInstanceState);
    }

    /**
     * 初始化ContentView，{@link #setContentView(int)} }
     */
    public void initView() {
        if (isBinding()) {
            mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        } else {
            setContentView(getLayoutId());
        }
    }

    public void initViewModel() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBinding != null) {
            mBinding.unbind();
        }
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
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }


    public Context getContext() {
        return this;
    }


    protected Intent newIntent(Class<?> cls) {
        return new Intent(getContext(), cls);
    }

    protected Intent newIntent(Class<?> cls, int flags) {
        Intent intent = newIntent(cls);
        intent.addFlags(flags);
        return intent;
    }

    protected void startActivity(Class<?> cls) {
        startActivity(newIntent(cls));
    }

    protected void startActivity(Class<?> cls, int flags) {
        startActivity(newIntent(cls, flags));
    }

    protected void startActivity(Class<?> cls, @Nullable ActivityOptionsCompat optionsCompat) {
        startActivity(newIntent(cls), optionsCompat);
    }

    protected void startActivity(Class<?> cls, int flags, @Nullable ActivityOptionsCompat optionsCompat) {
        startActivity(newIntent(cls, flags), optionsCompat);
    }

    protected void startActivity(Intent intent, @Nullable ActivityOptionsCompat optionsCompat) {
        if (optionsCompat != null) {
            startActivity(intent, optionsCompat.toBundle());
        } else {
            startActivity(intent);
        }
    }

    protected void startActivityFinish(Class<?> cls) {
        startActivity(cls);
        finish();
    }

    protected void startActivityFinish(Class<?> cls, int flags) {
        startActivity(cls, flags);
        finish();
    }

    protected void startActivityFinish(Class<?> cls, @Nullable ActivityOptionsCompat optionsCompat) {
        startActivity(cls, optionsCompat);
        finish();
    }

    protected void startActivityFinish(Class<?> cls, int flags, @Nullable ActivityOptionsCompat optionsCompat) {
        startActivity(newIntent(cls, flags), optionsCompat);
    }

    protected void startActivityFinish(Intent intent, @Nullable ActivityOptionsCompat optionsCompat) {
        startActivity(intent, optionsCompat);
    }

    protected void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(newIntent(cls), requestCode);
    }

    protected void startActivityForResult(Class<?> cls, int requestCode, @Nullable ActivityOptionsCompat optionsCompat) {
        Intent intent = newIntent(cls);
        if (optionsCompat != null) {
            startActivityForResult(intent, requestCode, optionsCompat.toBundle());
        } else {
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (isIgnoreJump(intent)) {
            return;
        }
        super.startActivityForResult(intent, requestCode, options);
    }

    protected boolean isIgnoreJump(Intent intent) {
        String jumpTag;
        if (intent.getComponent() != null) {
            jumpTag = intent.getComponent().getClassName();
        } else if (intent.getAction() != null) {
            jumpTag = intent.getAction();
        } else {
            return false;
        }

        if (TextUtils.isEmpty(jumpTag)) {
            return false;
        }

        if (jumpTag.equals(mJumpTag) && mJumpTime > SystemClock.elapsedRealtime() - getIgnoreIntervalTime()) {
            Timber.d("Ignore:" + jumpTag);
            return true;
        }
        mJumpTag = jumpTag;
        mJumpTime = SystemClock.elapsedRealtime();

        return false;
    }

    protected long getIgnoreIntervalTime() {
        return IGNORE_INTERVAL_TIME;
    }

    //---------------------------------------

    protected View inflate(@LayoutRes int id) {
        return inflate(id, null);
    }

    protected View inflate(@LayoutRes int id, @Nullable ViewGroup root) {
        return LayoutInflater.from(getContext()).inflate(id, root);
    }

    protected View inflate(@LayoutRes int id, @Nullable ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.from(getContext()).inflate(id, root, attachToRoot);
    }

    //---------------------------------------

    protected void showDialogFragment(DialogFragment dialogFragment) {
        String tag = dialogFragment.getTag() != null ? dialogFragment.getTag() : dialogFragment.getClass().getSimpleName();
        showDialogFragment(dialogFragment, tag);
    }

    protected void showDialogFragment(DialogFragment dialogFragment, String tag) {
        dialogFragment.show(getSupportFragmentManager(), tag);
    }

    protected void showDialogFragment(DialogFragment dialogFragment, FragmentManager fragmentManager, String tag) {
        dialogFragment.show(fragmentManager, tag);
    }

    private final View.OnClickListener mOnDialogCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismissDialog();
        }
    };

    protected Dialog getDialog() {
        return this.mDialog;
    }

    protected Dialog getProgressDialog() {
        return this.mProgressDialog;
    }

    protected View.OnClickListener getDialogCancelClick() {
        return mOnDialogCancelClick;
    }

    protected void dismissDialog() {
        dismissDialog(mDialog);
    }

    protected void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    protected void dismissPopupWindow(PopupWindow popupWindow) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    protected void dismissProgressDialog() {
        dismissDialog(mProgressDialog);
    }

    protected void showProgressDialog() {
        showProgressDialog(false);
    }

    protected void showProgressDialog(boolean isCancel) {
        showProgressDialog(R.layout.core_progress_dialog, isCancel);
    }

    protected void showProgressDialog(@LayoutRes int resId) {
        showProgressDialog(resId, false);
    }

    protected void showProgressDialog(@LayoutRes int resId, boolean isCancel) {
        showProgressDialog(inflate(resId), isCancel);
    }

    protected void showProgressDialog(View v) {
        showProgressDialog(v, false);
    }

    protected void showProgressDialog(View v, boolean isCancel) {
        dismissProgressDialog();
        mProgressDialog = BaseProgressDialog.newInstance(getContext());
        mProgressDialog.setContentView(v);
        mProgressDialog.setCanceledOnTouchOutside(isCancel);
        mProgressDialog.show();
    }

    protected void showDialog(View contentView) {
        showDialog(contentView, DEFAULT_WIDTH_RATIO);
    }

    protected void showDialog(View contentView, boolean isCancel) {
        showDialog(getContext(), contentView, R.style.core_dialog, DEFAULT_WIDTH_RATIO, isCancel);
    }

    protected void showDialog(View contentView, float widthRatio) {
        showDialog(getContext(), contentView, widthRatio);
    }

    protected void showDialog(View contentView, float widthRatio, boolean isCancel) {
        showDialog(getContext(), contentView, R.style.core_dialog, widthRatio, isCancel);
    }

    protected void showDialog(Context context, View contentView, float widthRatio) {
        showDialog(context, contentView, R.style.core_dialog, widthRatio);
    }

    protected void showDialog(Context context, View contentView, @StyleRes int resId, float widthRatio) {
        showDialog(context, contentView, resId, widthRatio, true);
    }

    protected void showDialog(Context context, View contentView, @StyleRes int resId, float widthRatio, final boolean isCancel) {
        dismissDialog();
        mDialog = new Dialog(context, resId);
        mDialog.setContentView(contentView);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (isCancel) {
                        dismissDialog();
                    }
                    return true;
                }
                return false;

            }
        });
        setDialogWindow(mDialog, widthRatio);
        mDialog.show();

    }

    protected void setDialogWindow(Dialog dialog, float widthRatio) {
        setWindow(dialog.getWindow(), widthRatio);
    }

    protected void setWindow(Window window, float widthRatio) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (getWidthPixels() * widthRatio);
        window.setAttributes(lp);
    }

    //---------------------------------------

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
