package com.basis.export.callback;

import java.io.File;

/**
 * 拍照选择监听
 */
public interface OnCameraListener {

    /**
     * 选择回调
     *
     * @param file 文件
     */
    void onSelected(File file);

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
