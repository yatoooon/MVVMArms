package com.common.res.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chad.library.adapter4.viewholder.DataBindingHolder;
import com.common.res.BR;
import com.common.res.R;
import com.common.res.adapter.BaseAdapter;
import com.common.res.aop.SingleClick;
import com.hjq.toast.Toaster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/10/09
 * desc   : 单选或者多选对话框
 */
public final class SelectDialog {

    public static final class Builder
            extends CommonDialog.Builder<Builder>
            implements View.OnLayoutChangeListener, Runnable {

        @SuppressWarnings("rawtypes")
        @Nullable
        private OnListener mListener;

        private final RecyclerView mRecyclerView;
        private final SelectAdapter mAdapter;

        public Builder(Context context) {
            super(context);

            setCustomView(R.layout.res_select_dialog);
            mRecyclerView = findViewById(R.id.rv_select_list);
            mRecyclerView.setItemAnimator(null);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            mAdapter = new SelectAdapter(R.layout.res_select_item, BR.item);
            mRecyclerView.setAdapter(mAdapter);
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
            mAdapter.submitList(data);
            mRecyclerView.addOnLayoutChangeListener(this);
            return this;
        }

        /**
         * 设置默认选中的位置
         */
        public Builder setSelect(int... positions) {
            mAdapter.setSelect(positions);
            return this;
        }

        /**
         * 设置最大选择数量
         */
        public Builder setMaxSelect(int count) {
            mAdapter.setMaxSelect(count);
            return this;
        }

        /**
         * 设置最小选择数量
         */
        public Builder setMinSelect(int count) {
            mAdapter.setMinSelect(count);
            return this;
        }

        /**
         * 设置单选模式
         */
        public Builder setSingleSelect() {
            mAdapter.setSingleSelect();
            return this;
        }

        @SuppressWarnings("rawtypes")
        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        @SingleClick
        @SuppressWarnings("all")
        @Override
        public void onClick(View view) {
            int viewId = view.getId();
            if (viewId == R.id.tv_ui_confirm) {
                HashMap<Integer, Object> data = mAdapter.getSelectSet();
                if (data.size() >= mAdapter.getMinSelect()) {
                    autoDismiss();
                    if (mListener == null) {
                        return;
                    }
                    mListener.onSelected(getDialog(), data);
                } else {
                    Toaster.show(String.format(getString(R.string.res_select_min_hint), mAdapter.getMinSelect()));
                }
            } else if (viewId == R.id.tv_ui_cancel) {
                autoDismiss();
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
            } else {
                if (params.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    mRecyclerView.setLayoutParams(params);
                }
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
    }

    private static final class SelectAdapter extends BaseAdapter<Object> {

        /**
         * 最小选择数量
         */
        private int mMinSelect = 1;
        /**
         * 最大选择数量
         */
        private int mMaxSelect = Integer.MAX_VALUE;

        /**
         * 选择对象集合
         */
        @SuppressLint("UseSparseArrays")
        private final HashMap<Integer, Object> mSelectSet = new HashMap<>();

        public SelectAdapter(int layoutId, int variableId) {
            super(layoutId, variableId);
            setOnItemClickListener((baseQuickAdapter, view, position) -> {
                System.out.println("position:" + position);
                if (mSelectSet.containsKey(position)) {
                    // 当前必须不是单选模式才能取消选中
                    if (!isSingleSelect()) {
                        mSelectSet.remove(position);
                        notifyItemChanged(position);
                    }
                } else {
                    if (mMaxSelect == 1) {
                        mSelectSet.clear();
                        notifyDataSetChanged();
                    }

                    if (mSelectSet.size() < mMaxSelect) {
                        mSelectSet.put(position, getItem(position));
                        notifyItemChanged(position);
                    } else {
                        Toaster.show(String.format(getContext().getString(R.string.res_select_max_hint), mMaxSelect));
                    }
                }
            });
        }


        private void setSelect(int... positions) {
            for (int position : positions) {
                mSelectSet.put(position, getItem(position));
            }
            notifyDataSetChanged();
        }

        private void setMaxSelect(int count) {
            mMaxSelect = count;
        }

        private void setMinSelect(int count) {
            mMinSelect = count;
        }

        private int getMinSelect() {
            return mMinSelect;
        }

        private void setSingleSelect() {
            setMaxSelect(1);
            setMinSelect(1);
        }

        private boolean isSingleSelect() {
            return mMaxSelect == 1 && mMinSelect == 1;
        }

        private HashMap<Integer, Object> getSelectSet() {
            return mSelectSet;
        }


        @Override
        protected void onBindViewHolder(@NonNull DataBindingHolder<?> holder, int position, @Nullable Object item) {
            super.onBindViewHolder(holder, position, item);
            ((CheckBox) holder.itemView.findViewById(R.id.tv_select_checkbox)).setChecked(mSelectSet.containsKey(holder.getBindingAdapterPosition()));
            if (mMaxSelect == 1) {
                holder.itemView.findViewById(R.id.tv_select_checkbox).setClickable(false);
            } else {
                holder.itemView.findViewById(R.id.tv_select_checkbox).setEnabled(false);
            }
            ((CheckBox) holder.itemView.findViewById(R.id.tv_select_checkbox)).setChecked(mSelectSet.containsKey(holder.getBindingAdapterPosition()));
            if (mMaxSelect == 1) {
                holder.itemView.findViewById(R.id.tv_select_checkbox).setClickable(false);
            } else {
                holder.itemView.findViewById(R.id.tv_select_checkbox).setEnabled(false);
            }
        }


    }

    public interface OnListener<T> {

        /**
         * 选择回调
         *
         * @param data 选择的位置和数据
         */
        void onSelected(BaseDialog dialog, HashMap<Integer, T> data);

        /**
         * 取消回调
         */
        default void onCancel(BaseDialog dialog) {
        }
    }
}