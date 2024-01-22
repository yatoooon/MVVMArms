package com.common.media.export


import android.app.Activity
import android.content.Context
import com.common.core.base.BaseActivity
import com.common.media.export.callback.OnCameraListener
import com.common.media.export.callback.OnCropListener
import com.common.media.export.callback.OnPhotoSelectListener
import com.common.media.export.callback.OnVideoSelectListener

import com.common.media.mvvm.activity.CameraActivity
import com.common.media.mvvm.activity.ImageCropActivity
import com.common.media.mvvm.activity.ImagePreviewActivity
import com.common.media.mvvm.activity.ImageSelectActivity
import com.common.media.mvvm.activity.VideoSelectActivity
import com.flyjingfish.module_communication_annotation.ImplementClass
import java.io.File

@ImplementClass(MediaExport::class)
class MediaExportImpl : MediaExport {

    override fun startImageSelectActivity(
        activity: Activity,
        maxSelect: Int,
        listener: OnPhotoSelectListener,
    ) {
        ImageSelectActivity.start(activity as BaseActivity<*>, maxSelect, listener)
    }


    override fun startCameraActivity(
        activity: Activity,
        video: Boolean,
        listener: OnCameraListener,
    ) {
        CameraActivity.start(activity as BaseActivity<*>, video, listener)
    }

    override fun startImageCropActivity(
        activity: Activity,
        file: File,
        cropRatioX: Int,
        cropRatioY: Int,
        listener: OnCropListener,
    ) {
        ImageCropActivity.start(activity as BaseActivity<*>, file, cropRatioX, cropRatioY, listener)
    }

    override fun startImagePreviewActivity(context: Context, urls: List<String>, index: Int) {
        ImagePreviewActivity.start(context, urls, index)
    }


    override fun startVideoSelectActivity(
        activity: Activity,
        maxSelect: Int,
        listener: OnVideoSelectListener,
    ) {
        VideoSelectActivity.start(activity as BaseActivity<*>, maxSelect, listener)
    }

}