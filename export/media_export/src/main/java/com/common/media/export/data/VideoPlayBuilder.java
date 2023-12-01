package com.common.media.export.data;

import android.content.pm.ActivityInfo;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * 播放参数构建
 */
public final class VideoPlayBuilder implements Parcelable {

    /**
     * 视频源
     */
    public String videoSource;
    /**
     * 视频标题
     */
    public String videoTitle;

    /**
     * 播放进度
     */
    public int playProgress;
    /**
     * 手势开关
     */
    public boolean gestureEnabled = true;
    /**
     * 循环播放
     */
    public boolean loopPlay = false;
    /**
     * 自动播放
     */
    public boolean autoPlay = true;
    /**
     * 播放完关闭
     */
    public boolean autoOver = true;

    /**
     * 播放方向
     */
    public int activityOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;

    public VideoPlayBuilder() {
    }

    protected VideoPlayBuilder(Parcel in) {
        videoSource = in.readString();
        videoTitle = in.readString();
        activityOrientation = in.readInt();

        playProgress = in.readInt();
        gestureEnabled = in.readByte() != 0;
        loopPlay = in.readByte() != 0;
        autoPlay = in.readByte() != 0;
        autoOver = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoSource);
        dest.writeString(videoTitle);
        dest.writeInt(playProgress);
        dest.writeByte((byte) (gestureEnabled ? 1 : 0));
        dest.writeByte((byte) (loopPlay ? 1 : 0));
        dest.writeByte((byte) (autoPlay ? 1 : 0));
        dest.writeByte((byte) (autoOver ? 1 : 0));
        dest.writeInt(activityOrientation);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoPlayBuilder> CREATOR = new Creator<VideoPlayBuilder>() {
        @Override
        public VideoPlayBuilder createFromParcel(Parcel in) {
            return new VideoPlayBuilder(in);
        }

        @Override
        public VideoPlayBuilder[] newArray(int size) {
            return new VideoPlayBuilder[size];
        }
    };

    public VideoPlayBuilder setVideoSource(File file) {
        videoSource = file.getPath();
        if (videoTitle == null) {
            videoTitle = file.getName();
        }
        return this;
    }

    public VideoPlayBuilder setVideoSource(String url) {
        videoSource = url;
        return this;
    }

    public String getVideoSource() {
        return videoSource;
    }

    public VideoPlayBuilder setVideoTitle(String title) {
        videoTitle = title;
        return this;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public VideoPlayBuilder setPlayProgress(int progress) {
        playProgress = progress;
        return this;
    }

    public int getPlayProgress() {
        return playProgress;
    }

    public VideoPlayBuilder setGestureEnabled(boolean enabled) {
        gestureEnabled = enabled;
        return this;
    }

    public boolean isGestureEnabled() {
        return gestureEnabled;
    }

    public VideoPlayBuilder setLoopPlay(boolean enabled) {
        loopPlay = enabled;
        return this;
    }

    public boolean isLoopPlay() {
        return loopPlay;
    }

    public VideoPlayBuilder setAutoPlay(boolean enabled) {
        autoPlay = enabled;
        return this;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public VideoPlayBuilder setAutoOver(boolean enabled) {
        autoOver = enabled;
        return this;
    }

    public boolean isAutoOver() {
        return autoOver;
    }

    public VideoPlayBuilder setActivityOrientation(int orientation) {
        activityOrientation = orientation;
        return this;
    }

//    public void start(Context context) {
//        Intent intent = new Intent();
//        switch (activityOrientation) {
//            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
//                intent.setClass(context, VideoPlayActivity.Landscape.class);
//                break;
//            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
//                intent.setClass(context, VideoPlayActivity.Portrait.class);
//                break;
//            default:
//                intent.setClass(context, VideoPlayActivity.class);
//                break;
//        }
//
//        intent.putExtra(INTENT_KEY_PARAMETERS, this);
//        if (!(context instanceof Activity)) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        context.startActivity(intent);
//    }


}