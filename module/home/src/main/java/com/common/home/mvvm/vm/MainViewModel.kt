package com.common.home.mvvm.vm

import android.app.Application
import com.common.home.mvvm.model.MainModel
import com.common.core.base.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application, mainModel: MainModel,
) : BaseViewModel<MainModel>(application, mainModel) {

}