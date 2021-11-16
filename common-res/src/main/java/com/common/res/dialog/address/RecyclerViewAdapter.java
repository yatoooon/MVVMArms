package com.common.res.dialog.address;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.common.res.BR;
import com.common.res.R;
import com.common.res.adapter.BaseAdapter;

import java.util.List;

public final class RecyclerViewAdapter extends BaseAdapter<List<AddressDialog.AddressBean>> {

    @Nullable
    public OnSelectListener mListener;

    public RecyclerViewAdapter(int layoutId, int variableId) {
        super(layoutId, variableId);
    }


    @Override
    protected void convert(@NonNull BaseDataBindingHolder<?> holder, List<AddressDialog.AddressBean> item) {
        super.convert(holder, item);
        RecyclerView recyclerView = (RecyclerView) holder.itemView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        BaseAdapter<AddressDialog.AddressBean> mAdapter = new BaseAdapter<>(R.layout.res_address_dialog_item_tv, BR.item);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mListener == null) {
                return;
            }
            mListener.onSelected(holder.getBindingAdapterPosition(), position);
        });
        recyclerView.setAdapter(mAdapter);
        mAdapter.setList(getItem(holder.getBindingAdapterPosition()));
    }


    public void setOnSelectListener(@Nullable OnSelectListener listener) {
        mListener = listener;
    }

    public interface OnSelectListener {
        void onSelected(int recyclerViewPosition, int clickItemPosition);
    }
}
