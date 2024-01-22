package com.common.media.export.callback;

import android.net.Uri;

import com.flyjingfish.module_communication_annotation.ExposeBean;

/**
 * 裁剪图片监听
 */
@ExposeBean
public interface OnCropListener {

    /**
     * 裁剪成功回调
     *
     * @param fileUri  文件路径
     * @param fileName 文件名称
     */
    void onSucceed(Uri fileUri, String fileName);

    /**
     * 错误回调
     *
     * @param details 错误详情
     */
    void onError(String details);

    /**
     * 取消回调
     */
    default void onCancel() {
    }
}
