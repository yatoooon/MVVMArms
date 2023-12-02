package com.common.media.export.callback;

import java.util.List;


/**
 * 图片选择监听
 */
public interface OnPhotoSelectListener {

    /**
     * 选择回调
     *
     * @param data 图片列表
     */
    void onSelected(List<String> data);

    /**
     * 取消回调
     */
    default void onCancel() {
    }
}