package com.common.res.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.common.res.R;
import com.common.res.aop.SingleClick;
import com.common.res.view.RegexEditText;
import com.hjq.toast.ToastUtils;


/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/02/27
 * desc   : 输入对话框
 */
public final class InputDialog {

    public static final class Builder
            extends CommonDialog.Builder<Builder>
            implements BaseDialog.OnShowListener,
            TextView.OnEditorActionListener {

        @Nullable
        private OnListener mListener;
        private final RegexEditText mInputView;

        public Builder(Context context) {
            super(context);
            setCustomView(R.layout.res_input_dialog);

            mInputView = findViewById(R.id.tv_input_message);
            mInputView.setOnEditorActionListener(this);

            addOnShowListener(this);


            //需求：1 不显示键盘  2点击drawable事件
            mInputView.setInputType(InputType.TYPE_NULL);
            mInputView.setOnTouchListener((view, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Drawable drawable = mInputView.getCompoundDrawables()[2];
                    if (drawable == null) {
                        return false;
                    }
                    //drawleft 是 小于 ,drawright 是 大于
                    //左右上下分别对应 0  1  2  3
                    if (event.getX() > mInputView.getWidth() - mInputView.getCompoundDrawables()[2].getBounds().width()) {
                        if (mListener != null) {
                            mListener.onRightDrawableClick();
                        }
                        return false;
                    }
                }
                return false;
            });
        }

        public Builder setHint(@StringRes int id) {
            return setHint(getString(id));
        }

        public Builder setHint(CharSequence text) {
            mInputView.setHint(text);
            return this;
        }

        public Builder setContent(@StringRes int id) {
            return setContent(getString(id));
        }

        public Builder setContent(CharSequence text) {
            mInputView.setText(text);
            Editable editable = mInputView.getText();
            if (editable == null) {
                return this;
            }
            int index = editable.length();
            if (index <= 0) {
                return this;
            }
//            mInputView.requestFocus();  //暂时不需要弹出输入法
            mInputView.setSelection(index);
            return this;
        }

        public Builder setInputRegex(String regex) {
            mInputView.setInputRegex(regex);
            return this;
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        /**
         * {@link BaseDialog.OnShowListener}
         */
        @Override
        public void onShow(BaseDialog dialog) {
//            postDelayed(() -> showKeyboard(mInputView), 500);   //暂时不需要弹出输入法
        }

        @SingleClick
        @Override
        public void onClick(View view) {
            int viewId = view.getId();
            if (viewId == R.id.tv_ui_confirm) {
                if (mListener == null) {
                    return;
                }
                Editable editable = mInputView.getText();
                if (TextUtils.isEmpty(editable != null ? editable.toString():"")){
                    ToastUtils.show("请扫描或手动输入编号");
                    return;
                }
                mListener.onConfirm(getDialog(), editable != null ? editable.toString() : "");
                autoDismiss();
            } else if (viewId == R.id.tv_ui_cancel) {
                autoDismiss();
                if (mListener == null) {
                    return;
                }
                mListener.onCancel(getDialog());
            }
        }

        /**
         * {@link TextView.OnEditorActionListener}
         */
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // 模拟点击确认按钮
                onClick(findViewById(R.id.tv_ui_confirm));
                return true;
            }
            return false;
        }
    }

    public interface OnListener {

        /**
         * 点击确定时回调
         */
        void onConfirm(BaseDialog dialog, String content);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {

        }

        /**
         * 点击右drawable时回调
         */
        default void onRightDrawableClick() {

        }
    }
}