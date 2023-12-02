package com.common.media.export.callback;


import com.common.media.export.data.VideoBean;

import java.util.List;

/**
 * 视频选择监听
 */
public interface OnVideoSelectListener {

    /**
     * 选择回调
     *
     * @param data 视频列表
     */
    void onSelected(List<VideoBean> data);

    /**
     * 取消回调
     */
    default void onCancel() {
    }
}