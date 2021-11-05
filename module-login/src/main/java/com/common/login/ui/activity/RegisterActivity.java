package com.common.login.ui.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.common.core.base.BaseActivity;
import com.common.login.R;
import com.common.res.aop.Log;
import com.common.res.aop.SingleClick;
import com.common.res.manager.InputTextManager;
import com.common.res.router.RouterHub;
import com.common.res.view.CountdownView;
import com.common.res.view.SubmitButton;
import com.gyf.immersionbar.ImmersionBar;


/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 注册界面
 */
@Route(path = RouterHub.PUBLIC_LOGIN_REGISTERACTIVITY)
public final class RegisterActivity extends BaseActivity
        implements TextView.OnEditorActionListener {

    private static final String INTENT_KEY_PHONE = "phone";
    private static final String INTENT_KEY_PASSWORD = "password";

    @Log
    public static void start(BaseActivity activity, String phone, String password, OnRegisterListener listener) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        intent.putExtra(INTENT_KEY_PHONE, phone);
        intent.putExtra(INTENT_KEY_PASSWORD, password);
        activity.startActivityForResult(intent, (resultCode, data) -> {

            if (listener == null || data == null) {
                return;
            }

            if (resultCode == RESULT_OK) {
                listener.onSucceed(data.getStringExtra(INTENT_KEY_PHONE), data.getStringExtra(INTENT_KEY_PASSWORD));
            } else {
                listener.onCancel();
            }
        });
    }

    private EditText mPhoneView;
    private CountdownView mCountdownView;

    private EditText mCodeView;

    private EditText mFirstPassword;
    private EditText mSecondPassword;

    private SubmitButton mCommitView;

    @Override
    public int getLayoutId() {
        return R.layout.login_register_activity;
    }

    @Override
    public void initView() {
        mPhoneView = findViewById(R.id.et_register_phone);
        mCountdownView = findViewById(R.id.cv_register_countdown);
        mCodeView = findViewById(R.id.et_register_code);
        mFirstPassword = findViewById(R.id.et_register_password1);
        mSecondPassword = findViewById(R.id.et_register_password2);
        mCommitView = findViewById(R.id.btn_register_commit);

        setOnClickListener(mCountdownView, mCommitView);

        mSecondPassword.setOnEditorActionListener(this);

        // 给这个 View 设置沉浸式，避免状态栏遮挡
        ImmersionBar.setTitleBar(this, findViewById(R.id.tv_register_title));

        InputTextManager.with(this)
                .addView(mPhoneView)
                .addView(mCodeView)
                .addView(mFirstPassword)
                .addView(mSecondPassword)
                .setMain(mCommitView)
                .build();
    }

    @Override
    public void initData() {
        // 自动填充手机号和密码
        mPhoneView.setText(getString(INTENT_KEY_PHONE));
        mFirstPassword.setText(getString(INTENT_KEY_PASSWORD));
        mSecondPassword.setText(getString(INTENT_KEY_PASSWORD));
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == mCountdownView) {
            if (mPhoneView.getText().toString().length() != 11) {
                mPhoneView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.login_shake_anim));
                toast(R.string.res_common_phone_input_error);
                return;
            }

            if (true) {
                toast(R.string.res_common_code_send_hint);
                mCountdownView.start();
                return;
            }

        } else if (view == mCommitView) {
            if (mPhoneView.getText().toString().length() != 11) {
                mPhoneView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.login_shake_anim));
                mCommitView.showError(3000);
                toast(R.string.res_common_phone_input_error);
                return;
            }

            if (mCodeView.getText().toString().length() != getResources().getInteger(R.integer.res_sms_code_length)) {
                mCodeView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.login_shake_anim));
                mCommitView.showError(3000);
                toast(R.string.res_common_code_error_hint);
                return;
            }

            if (!mFirstPassword.getText().toString().equals(mSecondPassword.getText().toString())) {
                mFirstPassword.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.login_shake_anim));
                mSecondPassword.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.login_shake_anim));
                mCommitView.showError(3000);
                toast(R.string.res_common_password_input_unlike);
                return;
            }

            // 隐藏软键盘
            hideKeyboard(getCurrentFocus());

            if (true) {
                mCommitView.showProgress();
                postDelayed(() -> {
                    mCommitView.showSucceed();
                    postDelayed(() -> {
                        setResult(RESULT_OK, new Intent()
                                .putExtra(INTENT_KEY_PHONE, mPhoneView.getText().toString())
                                .putExtra(INTENT_KEY_PASSWORD, mFirstPassword.getText().toString()));
                        finish();
                    }, 1000);
                }, 2000);
                return;
            }

        }
    }


    /**
     * {@link TextView.OnEditorActionListener}
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE && mCommitView.isEnabled()) {
            // 模拟点击注册按钮
            onClick(mCommitView);
            return true;
        }
        return false;
    }

    /**
     * 注册监听
     */
    public interface OnRegisterListener {

        /**
         * 注册成功
         *
         * @param phone    手机号
         * @param password 密码
         */
        void onSucceed(String phone, String password);

        /**
         * 取消注册
         */
        default void onCancel() {
        }
    }
}