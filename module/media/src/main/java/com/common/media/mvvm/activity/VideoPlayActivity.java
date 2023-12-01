package com.common.media.mvvm.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.common.core.base.BaseActivity;
import com.common.media.R;
import com.common.media.export.arouter.MediaRouterHub;
import com.common.media.export.data.VideoPlayBuilder;
import com.common.res.immersionbar.BindImmersionBar;
import com.common.res.view.PlayerView;

import me.jessyan.autosize.internal.CancelAdapt;


/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2020/02/16
 * desc   : 视频播放界面
 */
@Route(path = MediaRouterHub.PUBLIC_MEDIA_VIDEOPLAYACTIVITY)
public class VideoPlayActivity extends BaseActivity
        implements PlayerView.OnPlayListener, CancelAdapt {

    public static final String INTENT_KEY_PARAMETERS = "parameters";

    private PlayerView mPlayerView;
    private VideoPlayBuilder mBuilder;

    @Override
    public int getLayoutId() {
        return R.layout.media_video_play_activity;
    }

    @Override
    public void initView() {
        mPlayerView = findViewById(R.id.pv_video_play_view);
        mPlayerView.setLifecycleOwner(this);
        mPlayerView.setOnPlayListener(this);
    }

    @Override
    public void initData() {
        mBuilder = getParcelable(INTENT_KEY_PARAMETERS);
        if (mBuilder == null) {
            throw new IllegalArgumentException("are you ok?");
        }

        mPlayerView.setVideoTitle(mBuilder.getVideoTitle());
        mPlayerView.setVideoSource(mBuilder.getVideoSource());
        mPlayerView.setGestureEnabled(mBuilder.isGestureEnabled());

        if (mBuilder.isAutoPlay()) {
            mPlayerView.start();
        }
    }

    /**
     * {@link PlayerView.OnPlayListener}
     */
    @Override
    public void onClickBack(PlayerView view) {
        onBackPressed();
    }

    @Override
    public void onPlayStart(PlayerView view) {
        int progress = mBuilder.getPlayProgress();
        if (progress > 0) {
            mPlayerView.setProgress(progress);
        }
    }

    @Override
    public void onPlayProgress(PlayerView view) {
        // 记录播放进度
        mBuilder.setPlayProgress(view.getProgress());
    }

    @Override
    public void onPlayEnd(PlayerView view) {
        if (mBuilder.isLoopPlay()) {
            mPlayerView.setProgress(0);
            mPlayerView.start();
            return;
        }

        if (mBuilder.isAutoOver()) {
            finish();
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // 保存播放进度
        outState.putParcelable(INTENT_KEY_PARAMETERS, mBuilder);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // 读取播放进度
        mBuilder = savedInstanceState.getParcelable(INTENT_KEY_PARAMETERS);
    }

    /**
     * 竖屏播放
     */
    @Route(path = MediaRouterHub.PUBLIC_MEDIA_VIDEOPLAYACTIVITY_PORTRAIT)
    public static final class Portrait extends VideoPlayActivity {
    }

    /**
     * 横屏播放
     */
    @Route(path = MediaRouterHub.PUBLIC_MEDIA_VIDEOPLAYACTIVITY_LANDSCAPE)
    public static final class Landscape extends VideoPlayActivity {
    }


    @Override
    public int getImmersionBarType() {
        return BindImmersionBar.FULLSCREEN;
    }
}