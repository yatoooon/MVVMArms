package com.arms.media.arouter.service;

import android.content.Context;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.arms.core.base.BaseActivity;
import com.arms.export.arouter.RouterHub;
import com.arms.export.arouter.service.IMediaService;
import com.arms.export.callback.OnCameraListener;
import com.arms.export.callback.OnCropListener;
import com.arms.export.callback.OnPhotoSelectListener;
import com.arms.export.callback.OnVideoSelectListener;
import com.arms.media.mvvm.activity.CameraActivity;
import com.arms.media.mvvm.activity.ImageCropActivity;
import com.arms.media.mvvm.activity.ImagePreviewActivity;
import com.arms.media.mvvm.activity.ImageSelectActivity;
import com.arms.media.mvvm.activity.VideoSelectActivity;

import java.io.File;
import java.util.List;

@Route(path = RouterHub.MEDIA_SERVICE, name = "media服务")
public class MediaServiceImpl implements IMediaService {

    @Override
    public void init(Context context) {

    }

    @Override
    public void startImageSelectActivity(@NonNull BaseActivity<?> activity, int maxSelect, @NonNull OnPhotoSelectListener listener) {
        ImageSelectActivity.start(activity, maxSelect, listener);
    }


    @Override
    public void startCameraActivity(@NonNull BaseActivity<?> activity, boolean video, @NonNull OnCameraListener listener) {
        CameraActivity.start(activity, video, listener);
    }

    @Override
    public void startImageCropActivity(@NonNull BaseActivity<?> activity, @NonNull File file, int cropRatioX, int cropRatioY, @NonNull OnCropListener listener) {
        ImageCropActivity.start(activity, file, cropRatioX, cropRatioY, listener);
    }

    @Override
    public void startImagePreviewActivity(@NonNull Context context, @NonNull List<String> urls, int index) {
        ImagePreviewActivity.start(context,urls,index);
    }

    @Override
    public void startVideoSelectActivity(@NonNull BaseActivity<?> activity, int maxSelect, @NonNull OnVideoSelectListener listener) {
        VideoSelectActivity.start(activity,maxSelect,listener);
    }
}