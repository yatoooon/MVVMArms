package com.common.media.ui.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.common.media.BR;
import com.common.media.R;
import com.common.res.adapter.BaseAdapter;
import com.common.res.dialog.BaseDialog;
import com.common.res.dialog.BottomSheetDialog;

import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/07/27
 *    desc   : 相册专辑选取对话框
 */
public final class AlbumDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder> {

        @Nullable
        private OnListener mListener;

        private final RecyclerView mRecyclerView;

        private final BaseAdapter<AlbumInfo> mAdapter = new BaseAdapter<AlbumInfo>(R.layout.media_album_item, BR.item);

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.media_album_dialog);
            mRecyclerView = findViewById(R.id.rv_album_list);
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    List<AlbumInfo> data = mAdapter.getData();

                    for (AlbumInfo info : data) {
                        if (info.isSelect()) {
                            info.setSelect(false);
                            break;
                        }
                    }
                    mAdapter.getItem(position).setSelect(true);
                    mAdapter.notifyDataSetChanged();

                    // 延迟消失
                    postDelayed(() -> {

                        if (mListener != null) {
                            mListener.onSelected(getDialog(), position, mAdapter.getItem(position));
                        }
                        dismiss();

                    }, 300);
                }
            });
            mRecyclerView.setAdapter(mAdapter);
        }

        public Builder setData(List<AlbumInfo> data) {
            mAdapter.setNewInstance(data);
            // 滚动到选中的位置
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).isSelect()) {
                    mRecyclerView.scrollToPosition(i);
                    break;
                }
            }
            return this;
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }



        @NonNull
        @Override
        protected BaseDialog createDialog(Context context, int themeId) {
            BottomSheetDialog dialog = new BottomSheetDialog(context, themeId);
            dialog.getBottomSheetBehavior().setPeekHeight(getResources().getDisplayMetrics().heightPixels / 2);
            return dialog;
        }
    }



    /**
     * 专辑信息类
     */
    public static class AlbumInfo {

        /** 封面 */
        private String icon;
        /** 名称 */
        private String name;
        /** 备注 */
        private String remark;
        /** 选中 */
        private boolean select;

        public AlbumInfo(String icon, String name, String remark, boolean select) {
            this.icon = icon;
            this.name = name;
            this.remark = remark;
            this.select = select;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public String getIcon() {
            return icon;
        }

        public String getName() {
            return name;
        }

        public String getRemark() {
            return remark;
        }

        public boolean isSelect() {
            return select;
        }
    }

    public interface OnListener {

        /**
         * 选择条目时回调
         */
        void onSelected(BaseDialog dialog, int position, AlbumInfo bean);
    }
}