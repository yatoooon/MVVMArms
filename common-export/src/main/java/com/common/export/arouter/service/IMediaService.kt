package com.common.export.arouter.service

import com.alibaba.android.arouter.facade.template.IProvider
import com.common.core.base.BaseActivity
import com.common.export.callback.OnPhotoSelectListener

interface IMediaService : IProvider {


    fun startImageSelectActivity(
        activity: BaseActivity<*>,
        maxSelect: Int,
        listener: OnPhotoSelectListener
    )


}