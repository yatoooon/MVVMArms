package com.arms.common.dialog;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arms.common.adapter.BaseAdapter;
import com.arms.common.ext.ImageView_ExtensionKt;
import com.arms.common.http.imageloader.ImageLoader;
import com.arms.res.view.ScaleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.arms.common.BR;
import com.arms.common.R;
import com.arms.umeng.Platform;
import com.arms.umeng.UmengClient;
import com.arms.umeng.UmengShare;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.hjq.toast.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMQQMini;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;

import java.util.ArrayList;
import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/03/23
 * desc   : 分享对话框
 */
public final class ShareDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder>
            implements OnItemClickListener {

        private final RecyclerView mRecyclerView;
        private final BaseAdapter<ShareBean> mAdapter = new BaseAdapter<ShareBean>(R.layout.res_share_item, BR.item){

            @Override
            protected void convert(@NonNull BaseDataBindingHolder<?> holder, ShareBean item) {
                super.convert(holder, item);
                ScaleImageView ivShareImage = holder.findView(R.id.iv_share_image);
                AppCompatTextView tvShareText = holder.findView(R.id.tv_share_text);
                ImageView_ExtensionKt.load(ivShareImage,item.shareIcon);
                tvShareText.setText(item.shareName);
            }
        };

        private final ShareAction mShareAction;
        private final ShareBean mCopyLink;

        @Nullable
        private UmengShare.OnShareListener mListener;

        public Builder(Activity activity) {
            super(activity);

            setContentView(R.layout.res_share_dialog);

            List<ShareBean> data = new ArrayList<>();
            data.add(new ShareBean(getDrawable(R.drawable.res_share_wechat_ic), getString(R.string.res_share_platform_wechat), Platform.WECHAT));
            data.add(new ShareBean(getDrawable(R.drawable.res_share_moment_ic), getString(R.string.res_share_platform_moment), Platform.CIRCLE));
            data.add(new ShareBean(getDrawable(R.drawable.res_share_qq_ic), getString(R.string.res_share_platform_qq), Platform.QQ));
            data.add(new ShareBean(getDrawable(R.drawable.res_share_qzone_ic), getString(R.string.res_share_platform_qzone), Platform.QZONE));

            mCopyLink = new ShareBean(getDrawable(R.drawable.res_share_link_ic), getString(R.string.res_share_platform_link), null);

            mAdapter.setList(data);
            mAdapter.setOnItemClickListener(this);

            mRecyclerView = findViewById(R.id.rv_share_list);
            mRecyclerView.setLayoutManager(new GridLayoutManager(activity, data.size()));
            mRecyclerView.setAdapter(mAdapter);

            mShareAction = new ShareAction(activity);
        }

        /**
         * 分享网页链接：https://developer.umeng.com/docs/128606/detail/193883#h2-u5206u4EABu7F51u9875u94FEu63A51
         */
        public Builder setShareLink(UMWeb content) {
            mShareAction.withMedia(content);
            refreshShareOptions();
            return this;
        }

        /**
         * 分享图片：https://developer.umeng.com/docs/128606/detail/193883#h2-u5206u4EABu56FEu72473
         */
        public Builder setShareImage(UMImage content) {
            mShareAction.withMedia(content);
            refreshShareOptions();
            return this;
        }

        /**
         * 分享纯文本：https://developer.umeng.com/docs/128606/detail/193883#h2-u5206u4EABu7EAFu6587u672C5
         */
        public Builder setShareText(String content) {
            mShareAction.withText(content);
            refreshShareOptions();
            return this;
        }

        /**
         * 分享音乐：https://developer.umeng.com/docs/128606/detail/193883#h2-u5206u4EABu97F3u4E507
         */
        public Builder setShareMusic(UMusic content) {
            mShareAction.withMedia(content);
            refreshShareOptions();
            return this;
        }

        /**
         * 分享视频：https://developer.umeng.com/docs/128606/detail/193883#h2-u5206u4EABu89C6u98916
         */
        public Builder setShareVideo(UMVideo content) {
            mShareAction.withMedia(content);
            refreshShareOptions();
            return this;
        }

        /**
         * 分享 Gif 表情：https://developer.umeng.com/docs/128606/detail/193883#h2--gif-8
         */
        public Builder setShareEmoji(UMEmoji content) {
            mShareAction.withMedia(content);
            refreshShareOptions();
            return this;
        }

        /**
         * 分享微信小程序：https://developer.umeng.com/docs/128606/detail/193883#h2-u5206u4EABu5C0Fu7A0Bu5E8F2
         */
        public Builder setShareMin(UMMin content) {
            mShareAction.withMedia(content);
            refreshShareOptions();
            return this;
        }

        /**
         * 分享 QQ 小程序：https://developer.umeng.com/docs/128606/detail/193883#h2-u5206u4EABu5C0Fu7A0Bu5E8F2
         */
        public Builder setShareMin(UMQQMini content) {
            mShareAction.withMedia(content);
            refreshShareOptions();
            return this;
        }

        /**
         * 设置回调监听器
         */
        public Builder setListener(UmengShare.OnShareListener listener) {
            mListener = listener;
            return this;
        }

        /**
         * 刷新分享选项
         */
        private void refreshShareOptions() {
            switch (mShareAction.getShareContent().getShareType()) {
                case ShareContent.WEB_STYLE:
                    if (!mAdapter.getData().contains(mCopyLink)) {
                        mAdapter.addData(mCopyLink);
                        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), mAdapter.getItemCount()));
                    }
                    break;
                default:
                    if (mAdapter.getData().contains(mCopyLink)) {
                        mAdapter.addData(mCopyLink);
                        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), mAdapter.getItemCount()));
                    }
                    break;
            }
        }

        @Override
        public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
            Platform platform = mAdapter.getItem(position).sharePlatform;
            if (platform != null) {
                UmengClient.share(getActivity(), platform, mShareAction, mListener);
            } else {
                if (mShareAction.getShareContent().getShareType() == ShareContent.WEB_STYLE) {
                    // 复制到剪贴板
                    getSystemService(ClipboardManager.class).setPrimaryClip(ClipData.newPlainText("url", mShareAction.getShareContent().mMedia.toUrl()));
                    ToastUtils.show(R.string.res_share_platform_copy_hint);
                }
            }
            dismiss();
        }
    }


    public static class ShareBean {

        /**
         * 分享图标
         */
        public Drawable shareIcon;
        /**
         * 分享名称
         */
        public String shareName;
        /**
         * 分享平台
         */
        public Platform sharePlatform;

        public Drawable getShareIcon() {
            return shareIcon;
        }

        public String getShareName() {
            return shareName;
        }

        public Platform getSharePlatform() {
            return sharePlatform;
        }

        private ShareBean(Drawable icon, String name, Platform platform) {
            shareIcon = icon;
            shareName = name;
            sharePlatform = platform;
        }
    }
}