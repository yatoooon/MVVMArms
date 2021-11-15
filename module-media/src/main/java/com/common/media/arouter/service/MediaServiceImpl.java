package com.common.media.arouter.service;

import android.content.Context;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.common.core.base.BaseActivity;
import com.common.export.arouter.RouterHub;
import com.common.export.arouter.service.IMediaService;
import com.common.export.callback.OnPhotoSelectListener;
import com.common.media.ui.activity.ImageSelectActivity;

@Route(path = RouterHub.MEDIA_SERVICE, name = "media服务")
public class MediaServiceImpl implements IMediaService {


    @Override
    public void startImageSelectActivity(@NonNull BaseActivity<?> activity, int maxSelect, @NonNull OnPhotoSelectListener listener) {
        ImageSelectActivity.start(activity,maxSelect,listener);
    }

    @Override
    public void init(Context context) {

    }
}