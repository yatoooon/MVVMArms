package com.common.export.arouter.service

import android.app.Activity
import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider
import com.common.export.callback.OnCameraListener
import com.common.export.callback.OnCropListener
import com.common.export.callback.OnPhotoSelectListener
import com.common.export.callback.OnVideoSelectListener
import java.io.File

interface IMediaService : IProvider {


    fun startImageSelectActivity(
        activity: Activity, maxSelect: Int, listener: OnPhotoSelectListener
    )

    fun startCameraActivity(
        activity: Activity, video: Boolean, listener: OnCameraListener
    )

    fun startImageCropActivity(
        activity: Activity,
        file: File,
        cropRatioX: Int,
        cropRatioY: Int,
        listener: OnCropListener
    )

    fun startImagePreviewActivity(
        context: Context, urls: List<String>, index: Int
    )

    fun startVideoSelectActivity(
        activity: Activity, maxSelect: Int, listener: OnVideoSelectListener
    )


}