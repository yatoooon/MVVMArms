package com.common.res.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.common.res.BR;
import com.common.res.R;
import com.common.res.adapter.BaseAdapter;
import com.common.res.aop.SingleClick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/12/2
 * desc   : 菜单选择框
 */
public final class MenuDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder>
            implements OnItemClickListener,
            View.OnLayoutChangeListener, Runnable {

        @SuppressWarnings("rawtypes")
        @Nullable
        private OnListener mListener;
        private boolean mAutoDismiss = true;

        private final RecyclerView mRecyclerView;
        private final TextView mCancelView;

        private final BaseAdapter<String> mAdapter = new BaseAdapter<String>(R.layout.res_menu_item, BR.item) {
            @Override
            protected void convert(@NonNull BaseDataBindingHolder<?> holder, String item) {
                super.convert(holder, item);
                if (holder.getBindingAdapterPosition() == getItemCount() - 1) {
                    holder.findView(R.id.v_menu_line).setVisibility(View.GONE);
                } else {
                    holder.findView(R.id.v_menu_line).setVisibility(View.VISIBLE);

                }
            }
        };

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.res_menu_dialog);
            setAnimStyle(BaseDialog.ANIM_BOTTOM);

            mRecyclerView = findViewById(R.id.rv_menu_list);
            mCancelView = findViewById(R.id.tv_menu_cancel);
            setOnClickListener(mCancelView);

            mAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mAdapter);
        }

        @Override
        public Builder setGravity(int gravity) {
            switch (gravity) {
                // 如果这个是在中间显示的
                case Gravity.CENTER:
                case Gravity.CENTER_VERTICAL:
                    // 不显示取消按钮
                    setCancel("");
                    // 重新设置动画
                    setAnimStyle(BaseDialog.ANIM_SCALE);
                    break;
                default:
                    break;
            }
            return super.setGravity(gravity);
        }

        public Builder setList(int... ids) {
            List<String> data = new ArrayList<>(ids.length);
            for (int id : ids) {
                data.add(getString(id));
            }
            return setList(data);
        }

        public Builder setList(String... data) {
            return setList(Arrays.asList(data));
        }

        @SuppressWarnings("all")
        public Builder setList(List data) {
            mAdapter.setList(data);
            mRecyclerView.addOnLayoutChangeListener(this);
            return this;
        }

        public Builder setCancel(@StringRes int id) {
            return setCancel(getString(id));
        }

        public Builder setCancel(CharSequence text) {
            mCancelView.setText(text);
            return this;
        }

        public Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        @SuppressWarnings("rawtypes")
        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        @SingleClick
        @Override
        public void onClick(View view) {
            if (mAutoDismiss) {
                dismiss();
            }

            if (view == mCancelView) {
                if (mListener == null) {
                    return;
                }
                mListener.onCancel(getDialog());
            }
        }


        /**
         * {@link View.OnLayoutChangeListener}
         */
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            mRecyclerView.removeOnLayoutChangeListener(this);
            // 这里一定要加延迟，如果不加在 Android 9.0 上面会导致 setLayoutParams 无效
            post(this);
        }

        @Override
        public void run() {
            final ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
            final int maxHeight = getScreenHeight() / 4 * 3;
            if (mRecyclerView.getHeight() > maxHeight) {
                if (params.height != maxHeight) {
                    params.height = maxHeight;
                    mRecyclerView.setLayoutParams(params);
                }
                return;
            }

            if (params.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                mRecyclerView.setLayoutParams(params);
            }
        }

        /**
         * 获取屏幕的高度
         */
        private int getScreenHeight() {
            Resources resources = getResources();
            DisplayMetrics outMetrics = resources.getDisplayMetrics();
            return outMetrics.heightPixels;
        }

        @Override
        public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
            if (mAutoDismiss) {
                dismiss();
            }

            if (mListener == null) {
                return;
            }
            mListener.onSelected(getDialog(), position, mAdapter.getItem(position));
        }
    }


    public interface OnListener<T> {

        /**
         * 选择条目时回调
         */
        void onSelected(BaseDialog dialog, int position, T t);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {
        }
    }
}