package com.common.media.export.arouter

import android.app.Activity
import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider
import com.common.media.export.callback.OnCameraListener
import com.common.media.export.callback.OnCropListener
import com.common.media.export.callback.OnPhotoSelectListener
import com.common.media.export.callback.OnVideoSelectListener
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