package com.arms.login.mvvm.activity;

import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.arms.core.base.BaseActivity;
import com.arms.export.arouter.RouterHub;
import com.arms.login.R;
import com.arms.common.aop.SingleClick;
import com.arms.common.manager.InputTextManager;
import com.arms.res.view.CountdownView;


/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/02/27
 * desc   : 忘记密码
 */
@Route(path = RouterHub.PUBLIC_LOGIN_PASSWORDFORGETACTIVITY)
public final class PasswordForgetActivity extends BaseActivity
        implements TextView.OnEditorActionListener {

    private EditText mPhoneView;
    private EditText mCodeView;
    private CountdownView mCountdownView;
    private Button mCommitView;

    @Override
    public int getLayoutId() {
        return R.layout.login_password_forget_activity;
    }

    @Override
    public void initView() {
        mPhoneView = findViewById(R.id.et_password_forget_phone);
        mCodeView = findViewById(R.id.et_password_forget_code);
        mCountdownView = findViewById(R.id.cv_password_forget_countdown);
        mCommitView = findViewById(R.id.btn_password_forget_commit);

        setOnClickListener(mCountdownView, mCommitView);

        mCodeView.setOnEditorActionListener(this);

        InputTextManager.with(this)
                .addView(mPhoneView)
                .addView(mCodeView)
                .setMain(mCommitView)
                .build();
    }

    @Override
    public void initData() {

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

            // 隐藏软键盘
            hideKeyboard(getCurrentFocus());


        } else if (view == mCommitView) {

            if (mPhoneView.getText().toString().length() != 11) {
                mPhoneView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.login_shake_anim));
                toast(R.string.res_common_phone_input_error);
                return;
            }

            if (mCodeView.getText().toString().length() != getResources().getInteger(R.integer.res_sms_code_length)) {
                mCodeView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.login_shake_anim));
                toast(R.string.res_common_code_error_hint);
                return;
            }

            if (true) {
                PasswordResetActivity.start(getActivity(), mPhoneView.getText().toString(), mCodeView.getText().toString());
                finish();
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
            // 模拟点击下一步按钮
            onClick(mCommitView);
            return true;
        }
        return false;
    }
}