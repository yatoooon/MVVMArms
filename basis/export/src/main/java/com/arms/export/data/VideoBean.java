package com.arms.export.data;

import android.media.MediaMetadataRetriever;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

/**
 * 视频 Bean 类
 */
public  class VideoBean implements Parcelable {

    private final String mVideoPath;
    private final int mVideoWidth;
    private final int mVideoHeight;
    private final long mVideoDuration;
    private final long mVideoSize;

    public static VideoBean newInstance(String videoPath) {
        int videoWidth = 0;
        int videoHeight = 0;
        long videoDuration = 0;
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(videoPath);

            String widthMetadata = retriever.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            if (widthMetadata != null && !"".equals(widthMetadata)) {
                videoWidth = Integer.parseInt(widthMetadata);
            }

            String heightMetadata = retriever.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            if (heightMetadata != null && !"".equals(heightMetadata)) {
                videoHeight = Integer.parseInt(heightMetadata);
            }

            String durationMetadata = retriever.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_DURATION);
            if (durationMetadata != null && !"".equals(durationMetadata)) {
                videoDuration = Long.parseLong(durationMetadata);
            }
        } catch (RuntimeException e) {
            // 荣耀 LLD AL20 Android 8.0 出现：java.lang.IllegalArgumentException
            // 荣耀 HLK AL00 Android 10.0 出现：java.lang.RuntimeException：setDataSource failed: status = 0x80000000
            CrashReport.postCatchedException(e);
        }

        long videoSize = new File(videoPath).length();
        return new VideoBean(videoPath, videoWidth, videoHeight, videoDuration, videoSize);
    }

    public VideoBean(String path, int width, int height, long duration, long size) {
        mVideoPath = path;
        mVideoWidth = width;
        mVideoHeight = height;
        mVideoDuration = duration;
        mVideoSize = size;
    }

    public int getVideoWidth() {
        return mVideoWidth;
    }

    public int getVideoHeight() {
        return mVideoHeight;
    }

    public String getVideoPath() {
        return mVideoPath;
    }

    public long getVideoDuration() {
        return mVideoDuration;
    }

    public long getVideoSize() {
        return mVideoSize;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof VideoBean) {
            return mVideoPath.equals(((VideoBean) obj).mVideoPath);
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return mVideoPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mVideoPath);
        dest.writeInt(mVideoWidth);
        dest.writeInt(mVideoHeight);
        dest.writeLong(mVideoDuration);
        dest.writeLong(mVideoSize);
    }

    public VideoBean(Parcel in) {
        mVideoPath = in.readString();
        mVideoWidth = in.readInt();
        mVideoHeight = in.readInt();
        mVideoDuration = in.readLong();
        mVideoSize = in.readLong();
    }

    public static final Creator<VideoBean> CREATOR = new Creator<VideoBean>() {
        @Override
        public VideoBean createFromParcel(Parcel source) {
            return new VideoBean(source);
        }

        @Override
        public VideoBean[] newArray(int size) {
            return new VideoBean[size];
        }
    };
}
