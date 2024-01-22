package com.common.media.export

import android.app.Activity
import android.content.Context
import com.common.media.export.callback.OnCameraListener
import com.common.media.export.callback.OnCropListener
import com.common.media.export.callback.OnPhotoSelectListener
import com.common.media.export.callback.OnVideoSelectListener
import com.flyjingfish.module_communication_annotation.ExposeInterface
import java.io.File


@ExposeInterface
interface MediaExport {
    companion object {
        private const val MEDIA: String = "/media"

        const val PUBLIC_MEDIA_IMAGESELECTACTIVITY: String = "$MEDIA/ImageSelectActivity"
        const val PUBLIC_MEDIA_IMAGEPREVIEWACTIVITY: String = "$MEDIA/ImagePreviewActivity"
        const val PUBLIC_MEDIA_IMAGECROPACTIVITY: String = "$MEDIA/ImageCropActivity"
        const val PUBLIC_MEDIA_VIDEOSELECTACTIVITY: String = "$MEDIA/VideoSelectActivity"
        const val PUBLIC_MEDIA_VIDEOPLAYACTIVITY: String = "$MEDIA/VideoPlayActivity"
        const val PUBLIC_MEDIA_VIDEOPLAYACTIVITY_LANDSCAPE: String = "$MEDIA/VideoPlayActivity/Landscape"
        const val PUBLIC_MEDIA_VIDEOPLAYACTIVITY_PORTRAIT: String = "$MEDIA/VideoPlayActivity/Portrait"
    }

    fun startImageSelectActivity(
        activity: Activity, maxSelect: Int, listener: OnPhotoSelectListener,
    )

    fun startCameraActivity(
        activity: Activity, video: Boolean, listener: OnCameraListener,
    )

    fun startImageCropActivity(
        activity: Activity,
        file: File,
        cropRatioX: Int,
        cropRatioY: Int,
        listener: OnCropListener,
    )

    fun startImagePreviewActivity(
        context: Context, urls: List<String>, index: Int,
    )

    fun startVideoSelectActivity(
        activity: Activity, maxSelect: Int, listener: OnVideoSelectListener,
    )

}