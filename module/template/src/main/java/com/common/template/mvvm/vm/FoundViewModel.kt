package com.common.template.mvvm.vm

import android.app.Application
import com.common.template.mvvm.model.FoundModel
import com.common.core.base.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoundViewModel @Inject constructor(
    application: Application, foundModel: FoundModel,
) : BaseViewModel<FoundModel>(application, foundModel) {

}