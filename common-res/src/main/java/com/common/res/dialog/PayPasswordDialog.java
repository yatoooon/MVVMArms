package com.common.res.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.common.res.BR;
import com.common.res.R;
import com.common.res.adapter.BaseAdapter;
import com.common.res.aop.SingleClick;
import com.common.res.view.PasswordView;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/12/2
 * desc   : 支付密码对话框
 */
public final class PayPasswordDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder>
            implements OnItemClickListener {

        /**
         * 输入键盘文本
         */
        private static final String[] KEYBOARD_TEXT = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", ""};

        @Nullable
        private OnListener mListener;
        private boolean mAutoDismiss = true;
        private final LinkedList<String> mRecordList = new LinkedList<>();

        private final TextView mTitleView;
        private final ImageView mCloseView;

        private final TextView mSubTitleView;
        private final TextView mMoneyView;

        private final PasswordView mPasswordView;
        private final RecyclerView mRecyclerView;
        private final BaseAdapter<String> mAdapter = new BaseAdapter<String>(R.layout.res_pay_password_item, BR.item) {
            @Override
            protected void convert(@NonNull BaseDataBindingHolder<?> holder, String item) {
                super.convert(holder, item);
                if (holder.getBindingAdapterPosition() == 9) {
                    holder.findView(R.id.tv_pay_key).setVisibility(View.GONE);
                    holder.findView(R.id.v_empty).setVisibility(View.VISIBLE);
                    holder.findView(R.id.iv_delete).setVisibility(View.GONE);
                } else if (holder.getBindingAdapterPosition() == 11) {
                    holder.findView(R.id.tv_pay_key).setVisibility(View.GONE);
                    holder.findView(R.id.v_empty).setVisibility(View.GONE);
                    holder.findView(R.id.iv_delete).setVisibility(View.VISIBLE);
                } else {
                    holder.findView(R.id.tv_pay_key).setVisibility(View.VISIBLE);
                    holder.findView(R.id.v_empty).setVisibility(View.GONE);
                    holder.findView(R.id.iv_delete).setVisibility(View.GONE);
                }
            }
        };

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.res_pay_password_dialog);
            setCancelable(false);

            mTitleView = findViewById(R.id.tv_pay_title);
            mCloseView = findViewById(R.id.iv_pay_close);
            mSubTitleView = findViewById(R.id.tv_pay_sub_title);
            mMoneyView = findViewById(R.id.tv_pay_money);
            mPasswordView = findViewById(R.id.pw_pay_view);
            mRecyclerView = findViewById(R.id.rv_pay_list);
            setOnClickListener(mCloseView);

            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            mAdapter.setNewInstance(Arrays.asList(KEYBOARD_TEXT));
            mAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mAdapter);
        }

        public Builder setTitle(@StringRes int id) {
            return setTitle(getString(id));
        }

        public Builder setTitle(CharSequence title) {
            mTitleView.setText(title);
            return this;
        }

        public Builder setSubTitle(@StringRes int id) {
            return setSubTitle(getString(id));
        }

        public Builder setSubTitle(CharSequence subTitle) {
            mSubTitleView.setText(subTitle);
            return this;
        }

        public Builder setMoney(@StringRes int id) {
            return setSubTitle(getString(id));
        }

        public Builder setMoney(CharSequence money) {
            mMoneyView.setText(money);
            return this;
        }

        public Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }


        @SingleClick
        @Override
        public void onClick(View view) {
            if (view == mCloseView) {
                if (mAutoDismiss) {
                    dismiss();
                }

                if (mListener == null) {
                    return;
                }
                mListener.onCancel(getDialog());
            }
        }

        @Override
        public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
            switch (position) {
                case 11:
                    // 点击回退按钮删除
                    if (mRecordList.size() != 0) {
                        mRecordList.removeLast();
                    }
                    break;
                case 9:
                    // 点击空白的地方不做任何操作
                    break;
                default:
                    // 判断密码是否已经输入完毕
                    if (mRecordList.size() < PasswordView.PASSWORD_COUNT) {
                        // 点击数字，显示在密码行
                        mRecordList.add(KEYBOARD_TEXT[position]);
                    }

                    // 判断密码是否已经输入完毕
                    if (mRecordList.size() == PasswordView.PASSWORD_COUNT) {
                        postDelayed(() -> {
                            if (mAutoDismiss) {
                                dismiss();
                            }
                            // 获取输入的支付密码
                            StringBuilder password = new StringBuilder();
                            for (String s : mRecordList) {
                                password.append(s);
                            }
                            if (mListener == null) {
                                return;
                            }
                            mListener.onCompleted(getDialog(), password.toString());
                        }, 300);
                    }
                    break;
            }
            mPasswordView.setPassWordLength(mRecordList.size());
        }
    }


    public interface OnListener {

        /**
         * 输入完成时回调
         *
         * @param password 输入的密码
         */
        void onCompleted(BaseDialog dialog, String password);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {
        }
    }
}