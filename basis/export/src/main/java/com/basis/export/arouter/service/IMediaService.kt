package com.basis.export.arouter.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider
import com.basis.core.base.BaseActivity
import com.basis.export.callback.OnCameraListener
import com.basis.export.callback.OnCropListener
import com.basis.export.callback.OnPhotoSelectListener
import com.basis.export.callback.OnVideoSelectListener
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