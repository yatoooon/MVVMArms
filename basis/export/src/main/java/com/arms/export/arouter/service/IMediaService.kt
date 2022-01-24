package com.arms.export.arouter.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider
import com.arms.core.base.BaseActivity
import com.arms.export.callback.OnCameraListener
import com.arms.export.callback.OnCropListener
import com.arms.export.callback.OnPhotoSelectListener
import com.arms.export.callback.OnVideoSelectListener
import java.io.File

interface IMediaService : IProvider {


    fun startImageSelectActivity(
        activity: BaseActivity<*>, maxSelect: Int, listener: OnPhotoSelectListener
    )

    fun startCameraActivity(
        activity: BaseActivity<*>, video: Boolean, listener: OnCameraListener
    )

    fun startImageCropActivity(
        activity: BaseActivity<*>,
        file: File,
        cropRatioX: Int,
        cropRatioY: Int,
        listener: OnCropListener
    )

    fun startImagePreviewActivity(
        context: Context, urls: List<String>, index: Int
    )

    fun startVideoSelectActivity(
        activity: BaseActivity<*>, maxSelect: Int, listener: OnVideoSelectListener
    )


}