package com.common.res.dialog.address;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.common.res.R;
import com.common.res.adapter.BaseAdapter;
import com.hjq.shape.view.ShapeView;


/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2021/02/28
 * desc   : Tab 适配器
 */
public final class TabAdapter extends BaseAdapter<String> implements OnItemClickListener {


    /**
     * 当前选中条目位置
     */
    private int mSelectedPosition = 0;

    /**
     * 导航栏监听对象
     */
    @Nullable
    private OnTabListener mListener;


    public TabAdapter(int layoutId, int variableId) {
        super(layoutId, variableId);
        setOnItemClickListener(this);
        registerAdapterDataObserver(new TabAdapterDataObserver());
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        // 禁用 RecyclerView 条目动画
        recyclerView.setItemAnimator(null);
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public void setSelectedPosition(int position) {
        if (mSelectedPosition == position) {
            return;
        }
        notifyItemChanged(mSelectedPosition);
        mSelectedPosition = position;
        notifyItemChanged(position);
    }

    /**
     * 设置导航栏监听
     */
    public void setOnTabListener(@Nullable OnTabListener listener) {
        mListener = listener;
    }


    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        if (mSelectedPosition == position) {
            return;
        }

        if (mListener == null) {
            mSelectedPosition = position;
            notifyDataSetChanged();
            return;
        }

        if (mListener.onTabSelected(adapter.getRecyclerView(), position)) {
            mSelectedPosition = position;
            notifyDataSetChanged();
        }
    }


    @Override
    protected void convert(@NonNull BaseDataBindingHolder<?> holder, String item) {
        super.convert(holder, item);
        TextView mTitleView = (TextView) holder.findView(R.id.tv_tab_sliding_title);
        ShapeView mLineView = (ShapeView) holder.findView(R.id.v_tab_sliding_line);
        mTitleView.setText(item);
        mTitleView.setSelected(mSelectedPosition == holder.getBindingAdapterPosition());
        mLineView.setVisibility(mSelectedPosition == holder.getBindingAdapterPosition() ? View.VISIBLE : View.INVISIBLE);
    }


    /**
     * 数据改变监听器
     */
    private final class TabAdapterDataObserver extends RecyclerView.AdapterDataObserver {

        @Override
        public void onChanged() {
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (getSelectedPosition() > positionStart - itemCount) {
                setSelectedPosition(positionStart - itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        }

    }

    /**
     * Tab 监听器
     */
    public interface OnTabListener {

        /**
         * Tab 被选中了
         */
        boolean onTabSelected(RecyclerView recyclerView, int position);
    }
}