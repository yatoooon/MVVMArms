package com.arms.common.dialog.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arms.common.action.AnimAction;
import com.arms.common.adapter.BaseAdapter;
import com.arms.res.view.ArrowDrawable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.arms.res.BR;
import com.arms.common.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/10/18
 *    desc   : 列表弹窗
 */
public final class ListPopup {

    public static final class Builder
            extends BasePopupWindow.Builder<Builder>
            implements OnItemClickListener {

        @SuppressWarnings("rawtypes")
        @Nullable
        private OnListener mListener;
        private boolean mAutoDismiss = true;

        private final BaseAdapter<String> mAdapter = new BaseAdapter<String>(R.layout.res_popup_item, BR.item){
            @Override
            protected void convert(@NonNull BaseDataBindingHolder<?> holder, String item) {
                super.convert(holder, item);
                holder.findView(R.id.tv_popup_text).setPaddingRelative((int) getResources().getDimension(R.dimen.res_dp_12),
                        (holder.getBindingAdapterPosition() == 0 ? (int) getResources().getDimension(R.dimen.res_dp_12) : 0),
                        (int) getResources().getDimension(R.dimen.res_dp_12),
                        (int) getResources().getDimension(R.dimen.res_dp_10));
            }
        };

        public Builder(Context context) {
            super(context);

            RecyclerView recyclerView = new RecyclerView(context);
            recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            setContentView(recyclerView);

            recyclerView.setAdapter(mAdapter);

            new ArrowDrawable.Builder(context)
                    .setArrowOrientation(Gravity.TOP)
                    .setArrowGravity(Gravity.CENTER)
                    .setShadowSize((int) getResources().getDimension(R.dimen.res_dp_10))
                    .setBackgroundColor(0xFFFFFFFF)
                    .apply(recyclerView);
        }

        @Override
        public Builder setGravity(int gravity) {
            switch (gravity) {
                // 如果这个是在中间显示的
                case Gravity.CENTER:
                case Gravity.CENTER_VERTICAL:
                    // 重新设置动画
                    setAnimStyle(AnimAction.ANIM_SCALE);
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



        @Override
        public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
            if (mAutoDismiss) {
                dismiss();
            }

            if (mListener == null) {
                return;
            }
            mListener.onSelected(getPopupWindow(), position, mAdapter.getItem(position));
        }
    }


    public interface OnListener<T> {

        /**
         * 选择条目时回调
         */
        void onSelected(BasePopupWindow popupWindow, int position, T t);
    }
}