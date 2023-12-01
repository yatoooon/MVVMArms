package com.common.media.export.arouter


object MediaRouterHub {

    const val SERVICE = "/service"




    const val MEDIA: String = "/media"
    const val MEDIA_SERVICE: String = "$MEDIA$SERVICE/IMediaService"
    const val PUBLIC_MEDIA_IMAGESELECTACTIVITY: String = "$MEDIA/ImageSelectActivity"
    const val PUBLIC_MEDIA_IMAGEPREVIEWACTIVITY: String = "$MEDIA/ImagePreviewActivity"
    const val PUBLIC_MEDIA_IMAGECROPACTIVITY: String = "$MEDIA/ImageCropActivity"
    const val PUBLIC_MEDIA_VIDEOSELECTACTIVITY: String = "$MEDIA/VideoSelectActivity"
    const val PUBLIC_MEDIA_VIDEOPLAYACTIVITY: String = "$MEDIA/VideoPlayActivity"
    const val PUBLIC_MEDIA_VIDEOPLAYACTIVITY_LANDSCAPE: String = "$MEDIA/VideoPlayActivity/Landscape"
    const val PUBLIC_MEDIA_VIDEOPLAYACTIVITY_PORTRAIT: String = "$MEDIA/VideoPlayActivity/Portrait"

}