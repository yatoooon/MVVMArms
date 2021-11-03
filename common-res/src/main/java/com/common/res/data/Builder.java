package com.common.res.data;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Parcel;
import android.os.Parcelable;


import java.io.File;

/**
 * 播放参数构建
 */
public final class Builder implements Parcelable {

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

    public Builder() {
    }

    public Builder(Parcel in) {
        videoSource = in.readString();
        videoTitle = in.readString();
        activityOrientation = in.readInt();

        playProgress = in.readInt();
        gestureEnabled = in.readByte() != 0;
        loopPlay = in.readByte() != 0;
        autoPlay = in.readByte() != 0;
        autoOver = in.readByte() != 0;
    }

    public Builder setVideoSource(File file) {
        videoSource = file.getPath();
        if (videoTitle == null) {
            videoTitle = file.getName();
        }
        return this;
    }

    public Builder setVideoSource(String url) {
        videoSource = url;
        return this;
    }

    public String getVideoSource() {
        return videoSource;
    }

    public Builder setVideoTitle(String title) {
        videoTitle = title;
        return this;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public Builder setPlayProgress(int progress) {
        playProgress = progress;
        return this;
    }

    public int getPlayProgress() {
        return playProgress;
    }

    public Builder setGestureEnabled(boolean enabled) {
        gestureEnabled = enabled;
        return this;
    }

    public boolean isGestureEnabled() {
        return gestureEnabled;
    }

    public Builder setLoopPlay(boolean enabled) {
        loopPlay = enabled;
        return this;
    }

    public boolean isLoopPlay() {
        return loopPlay;
    }

    public Builder setAutoPlay(boolean enabled) {
        autoPlay = enabled;
        return this;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public Builder setAutoOver(boolean enabled) {
        autoOver = enabled;
        return this;
    }

    public boolean isAutoOver() {
        return autoOver;
    }

    public Builder setActivityOrientation(int orientation) {
        activityOrientation = orientation;
        return this;
    }

    public void start(Context context) {
        Intent intent = new Intent();
        switch (activityOrientation) {
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                try {
                    intent.setClass(context, Class.forName("com.common.media.ui.activity.VideoPlayActivity.Landscape"));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                try {
                    intent.setClass(context, Class.forName("com.common.media.ui.activity.VideoPlayActivity.Portrait"));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    intent.setClass(context, Class.forName("com.common.media.ui.activity.VideoPlayActivity"));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }

        intent.putExtra("parameters", this);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoSource);
        dest.writeString(videoTitle);
        dest.writeInt(activityOrientation);
        dest.writeInt(playProgress);
        dest.writeByte(gestureEnabled ? (byte) 1 : (byte) 0);
        dest.writeByte(loopPlay ? (byte) 1 : (byte) 0);
        dest.writeByte(autoPlay ? (byte) 1 : (byte) 0);
        dest.writeByte(autoOver ? (byte) 1 : (byte) 0);
    }

    public static final Creator<Builder> CREATOR = new Creator<Builder>() {
        @Override
        public Builder createFromParcel(Parcel source) {
            return new Builder(source);
        }

        @Override
        public Builder[] newArray(int size) {
            return new Builder[size];
        }
    };
}
