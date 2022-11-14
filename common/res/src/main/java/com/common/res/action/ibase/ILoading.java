package com.common.res.action.ibase;

import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;
import java.util.List;

import kotlinx.coroutines.Job;

/**
 *
 */
public interface ILoading extends LifecycleOwner {
    List<Job> JOB_LIST = new ArrayList<>();

    default List<Job> getJobList() {
        return JOB_LIST;
    }


    boolean isShowLoading();

    /**
     * 显示加载
     */
    void showLoadingDialog(ILoading loading);

    /**
     * 隐藏加载
     */
    void hideLoadingDialog(ILoading loading);


}
