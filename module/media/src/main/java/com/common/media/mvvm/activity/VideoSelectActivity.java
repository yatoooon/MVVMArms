package com.common.media.mvvm.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.common.core.base.BaseActivity;
import com.common.export.arouter.RouterHub;
import com.common.export.callback.OnCameraListener;
import com.common.export.callback.OnVideoSelectListener;
import com.common.export.data.VideoBean;
import com.common.export.data.VideoPlayBuilder;
import com.common.media.BR;
import com.common.media.R;
import com.common.media.mvvm.dialog.AlbumDialog;
import com.common.res.action.StatusAction;
import com.common.res.adapter.BaseAdapter;
import com.common.res.adapter.DataBindingViewHolder;
import com.common.res.aop.Log;
import com.common.res.aop.Permissions;
import com.common.res.aop.SingleClick;
import com.common.res.layout.StatusLayout;
import com.common.res.manager.ThreadPoolManager;
import com.common.res.utils.CacheUtil;
import com.common.res.view.FloatActionButton;
import com.common.res.view.GridSpaceDecoration;
import com.common.res.view.PlayerView;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2020/03/01
 * desc   : 选择视频
 */
@Route(path = RouterHub.PUBLIC_MEDIA_VIDEOSELECTACTIVITY)
public final class VideoSelectActivity extends BaseActivity implements StatusAction, Runnable, OnItemClickListener, OnItemLongClickListener, OnItemChildClickListener {

    private static final String INTENT_KEY_IN_MAX_SELECT = "maxSelect";

    private static final String INTENT_KEY_OUT_VIDEO_LIST = "videoList";


    @Permissions({Permission.READ_MEDIA_IMAGES, Permission.READ_MEDIA_VIDEO, Permission.READ_MEDIA_AUDIO})
    public static void start(BaseActivity activity, int maxSelect, OnVideoSelectListener listener) {
        if (maxSelect < 1) {
            // 最少要选择一个视频
            throw new IllegalArgumentException("are you ok?");
        }
        Intent intent = new Intent(activity, VideoSelectActivity.class);
        intent.putExtra(INTENT_KEY_IN_MAX_SELECT, maxSelect);
        activity.startActivityForResult(intent, (resultCode, data) -> {

            if (listener == null) {
                return;
            }

            if (data == null) {
                listener.onCancel();
                return;
            }

            ArrayList<VideoBean> list = data.getParcelableArrayListExtra(INTENT_KEY_OUT_VIDEO_LIST);
            if (list == null || list.isEmpty()) {
                listener.onCancel();
                return;
            }

            Iterator<VideoBean> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (!new File(iterator.next().getVideoPath()).isFile()) {
                    iterator.remove();
                }
            }

            if (resultCode == RESULT_OK && !list.isEmpty()) {
                listener.onSelected(list);
                return;
            }
            listener.onCancel();
        });
    }

    private StatusLayout mStatusLayout;
    private RecyclerView mRecyclerView;
    private FloatActionButton mFloatingView;

    private BaseAdapter<VideoBean> mAdapter = new BaseAdapter<VideoBean>(R.layout.media_video_select_item, BR.item) {
        @Override
        protected void convert(@NonNull DataBindingViewHolder<?> holder, VideoBean item) {
            super.convert(holder, item);
            ((CheckBox) holder.findView(R.id.iv_video_select_check)).setChecked(mSelectVideo.contains(item));
        }
    };

    /**
     * 最大选中
     */
    private int mMaxSelect = 1;
    /**
     * 选中列表
     */
    private final ArrayList<VideoBean> mSelectVideo = new ArrayList<>();

    /**
     * 全部视频
     */
    private final ArrayList<VideoBean> mAllVideo = new ArrayList<>();
    /**
     * 视频专辑
     */
    private final HashMap<String, List<VideoBean>> mAllAlbum = new HashMap<>();

    /**
     * 专辑选择对话框
     */
    private AlbumDialog.Builder mAlbumDialog;

    @Override
    public int getLayoutId() {
        return R.layout.media_video_select_activity;
    }


    @Override
    public void initView() {
        if (getTitleBar() != null) {
            getTitleBar().setOnTitleBarListener(this);
        }
        mStatusLayout = findViewById(R.id.hl_video_select_hint);
        mRecyclerView = findViewById(R.id.rv_video_select_list);
        mFloatingView = findViewById(R.id.fab_video_select_floating);
        setOnClickListener(mFloatingView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
//        mAdapter = new VideoSelectAdapter(this, mSelectVideo);
        mAdapter.addChildClickViewIds(R.id.fl_video_select_check);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        // 禁用动画效果
        mRecyclerView.setItemAnimator(null);
        // 添加分割线
        mRecyclerView.addItemDecoration(new GridSpaceDecoration((int) getResources().getDimension(R.dimen.res_dp_5)));
        // 设置滚动监听
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        mFloatingView.hide();
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        mFloatingView.show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        // 获取最大的选择数
        mMaxSelect = getInt(INTENT_KEY_IN_MAX_SELECT, mMaxSelect);

        // 显示加载进度条
        showLoading();
        // 加载视频列表
        ThreadPoolManager.getInstance().execute(this);
    }

    @Override
    public StatusLayout getStatusLayout() {
        return mStatusLayout;
    }

    @SingleClick
    @Override
    public void onRightClick(TitleBar titleBar) {
        if (mAllVideo.isEmpty()) {
            return;
        }

        ArrayList<AlbumDialog.AlbumInfo> data = new ArrayList<>(mAllAlbum.size() + 1);

        int count = 0;
        Set<String> keys = mAllAlbum.keySet();
        for (String key : keys) {
            List<VideoBean> list = mAllAlbum.get(key);
            if (list == null || list.isEmpty()) {
                continue;
            }
            count += list.size();
            data.add(new AlbumDialog.AlbumInfo(list.get(0).getVideoPath(), key, String.format(getString(R.string.res_video_select_total), list.size()), mAdapter.getData() == list));
        }
        data.add(0, new AlbumDialog.AlbumInfo(mAllVideo.get(0).getVideoPath(), getString(R.string.res_video_select_all), String.format(getString(R.string.res_video_select_total), count), mAdapter.getData() == mAllVideo));

        if (mAlbumDialog == null) {
            mAlbumDialog = new AlbumDialog.Builder(this).setListener((dialog, position, bean) -> {

                setRightTitle(bean.getName());
                // 滚动回第一个位置
                mRecyclerView.scrollToPosition(0);
                if (position == 0) {
                    mAdapter.setList(mAllVideo);
                } else {
                    mAdapter.setList(mAllAlbum.get(bean.getName()));
                }
                // 执行列表动画
                mRecyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.res_layout_from_right));
                mRecyclerView.scheduleLayoutAnimation();
            });
        }
        mAlbumDialog.setData(data).show();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Iterator<VideoBean> iterator = mSelectVideo.iterator();
        // 遍历判断选择了的视频是否被删除了
        while (iterator.hasNext()) {
            VideoBean bean = iterator.next();

            File file = new File(bean.getVideoPath());
            if (file.isFile()) {
                continue;
            }

            iterator.remove();
            mAllVideo.remove(bean);

            File parentFile = file.getParentFile();
            if (parentFile == null) {
                continue;
            }

            List<VideoBean> data = mAllAlbum.get(parentFile.getName());
            if (data != null) {
                data.remove(bean);
            }
            mAdapter.notifyDataSetChanged();

            if (mSelectVideo.isEmpty()) {
                mFloatingView.setImageResource(R.drawable.res_videocam_ic);
            } else {
                mFloatingView.setImageResource(R.drawable.res_succeed_ic);
            }
        }
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_video_select_floating) {
            if (mSelectVideo.isEmpty()) {
//                // 点击拍照
                CameraActivity.start(this, true, new OnCameraListener() {
                    @Override
                    public void onSelected(File file) {
                        // 当前选中视频的数量必须小于最大选中数
                        if (mSelectVideo.size() < mMaxSelect) {
                            mSelectVideo.add(VideoBean.newInstance(file.getPath()));
                        }

                        // 这里需要延迟刷新，否则可能会找不到拍照的视频
                        postDelayed(() -> {
                            // 重新加载视频列表
                            ThreadPoolManager.getInstance().execute(VideoSelectActivity.this);
                        }, 1000);
                    }

                    @Override
                    public void onError(String details) {
                        toast(details);
                    }
                });
                return;
            }

            // 完成选择
            setResult(RESULT_OK, new Intent().putParcelableArrayListExtra(INTENT_KEY_OUT_VIDEO_LIST, mSelectVideo));
            finish();
        }
    }


    @Override
    public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        if (view.getId() == R.id.fl_video_select_check) {

            VideoBean bean = mAdapter.getItem(position);
            File file = new File(bean.getVideoPath());
            if (!file.isFile()) {
                mAdapter.removeAt(position);
                toast(R.string.res_video_select_error);
                return;
            }

            if (mSelectVideo.contains(bean)) {
                mSelectVideo.remove(bean);

                if (mSelectVideo.isEmpty()) {
                    mFloatingView.setImageResource(R.drawable.res_videocam_ic);
                }

                mAdapter.notifyItemChanged(position);
                return;
            }

            if (mMaxSelect == 1 && mSelectVideo.size() == 1) {

                List<VideoBean> data = mAdapter.getData();
                int index = data.indexOf(mSelectVideo.remove(0));
                if (index != -1) {
                    mAdapter.notifyItemChanged(index);
                }
                mSelectVideo.add(bean);

            } else if (mSelectVideo.size() < mMaxSelect) {

                mSelectVideo.add(bean);

                if (mSelectVideo.size() == 1) {
                    mFloatingView.setImageResource(R.drawable.res_succeed_ic);
                }
            } else {
                toast(String.format(getString(R.string.res_video_select_max_hint), mMaxSelect));
            }
            mAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        VideoBean bean = mAdapter.getItem(position);
        VideoPlayBuilder videoPlayBuilder = new VideoPlayBuilder().setVideoSource(new File(bean.getVideoPath())).setActivityOrientation(bean.getVideoWidth() > bean.getVideoHeight() ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ARouter.getInstance().build(bean.getVideoWidth() > bean.getVideoHeight() ? RouterHub.PUBLIC_MEDIA_VIDEOPLAYACTIVITY_LANDSCAPE : RouterHub.PUBLIC_MEDIA_VIDEOPLAYACTIVITY_PORTRAIT).withParcelable("parameters", videoPlayBuilder).navigation();
    }

    @Override
    public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        if (mSelectVideo.size() < mMaxSelect) {
            // 长按的时候模拟选中
            return view.findViewById(R.id.fl_video_select_check).performClick();
        }
        return false;
    }


    @Override
    public void run() {
        mAllAlbum.clear();
        mAllVideo.clear();

        final Uri contentUri = MediaStore.Files.getContentUri("external");
        final String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC";
        final String selection = "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)" + " AND " + MediaStore.MediaColumns.SIZE + ">0";

        ContentResolver contentResolver = getContentResolver();
        String[] projections = new String[]{MediaStore.Files.FileColumns._ID, MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED, MediaStore.MediaColumns.MIME_TYPE, MediaStore.MediaColumns.WIDTH, MediaStore.MediaColumns.HEIGHT, MediaStore.MediaColumns.SIZE, MediaStore.Video.Media.DURATION};

        Cursor cursor = null;
        if (XXPermissions.isGranted(this, Permission.READ_MEDIA_IMAGES, Permission.READ_MEDIA_VIDEO, Permission.READ_MEDIA_AUDIO)) {
            cursor = contentResolver.query(contentUri, projections, selection, new String[]{String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)}, sortOrder);
        }
        if (cursor != null && cursor.moveToFirst()) {

            int pathIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
            int mimeTypeIndex = cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE);
            int sizeIndex = cursor.getColumnIndex(MediaStore.MediaColumns.SIZE);
            int durationIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DURATION);
            int widthIndex = cursor.getColumnIndex(MediaStore.MediaColumns.WIDTH);
            int heightIndex = cursor.getColumnIndex(MediaStore.MediaColumns.HEIGHT);

            do {
                long duration = cursor.getLong(durationIndex);
                // 视频时长不得小于 1 秒
                if (duration < 1000) {
                    continue;
                }

                long size = cursor.getLong(sizeIndex);
                // 视频大小不得小于 10 KB
                if (size < 1024 * 10) {
                    continue;
                }

                String type = cursor.getString(mimeTypeIndex);
                String path = cursor.getString(pathIndex);
                if (TextUtils.isEmpty(path) || TextUtils.isEmpty(type)) {
                    continue;
                }

                File file = new File(path);
                if (!file.exists() || !file.isFile()) {
                    continue;
                }

                File parentFile = file.getParentFile();
                if (parentFile == null) {
                    continue;
                }

                // 获取目录名作为专辑名称
                String albumName = parentFile.getName();
                List<VideoBean> data = mAllAlbum.get(albumName);
                if (data == null) {
                    data = new ArrayList<>();
                    mAllAlbum.put(albumName, data);
                }

                int width = cursor.getInt(widthIndex);
                int height = cursor.getInt(heightIndex);

                VideoBean bean = new VideoBean(path, width, height, duration, size);
                data.add(bean);
                mAllVideo.add(bean);

            } while (cursor.moveToNext());

            cursor.close();
        }

        postDelayed(() -> {
            // 滚动回第一个位置
            mRecyclerView.scrollToPosition(0);
            // 设置新的列表数据
            mAdapter.setList(mAllVideo);

            if (mSelectVideo.isEmpty()) {
                mFloatingView.setImageResource(R.drawable.res_videocam_ic);
            } else {
                mFloatingView.setImageResource(R.drawable.res_succeed_ic);
            }

            // 执行列表动画
            mRecyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.res_layout_fall_down));
            mRecyclerView.scheduleLayoutAnimation();

            if (mAllVideo.isEmpty()) {
                // 显示空布局
                showEmpty();
                // 设置右标题
                setRightTitle(null);
            } else {
                // 显示加载完成
                showComplete();
                // 设置右标题
                setRightTitle(R.string.res_video_select_all);
            }
        }, 500);
    }


    @BindingAdapter(value = {"videoDuration"})
    public static void setVideoDuration(TextView textView, long videoDuration) {
        textView.setText(PlayerView.conversionTime((int) videoDuration));
    }

    @BindingAdapter(value = {"videoSize"})
    public static void setVideoSize(TextView textView, double videoSize) {
        textView.setText(CacheUtil.INSTANCE.getFormatSize(videoSize));
    }
}