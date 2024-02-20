package com.common.template.mvvm.vm

import android.app.Application
import com.common.template.mvvm.model.HomeModel
import com.common.core.base.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application, homeModel: HomeModel,
) : BaseViewModel<HomeModel>(application, homeModel) {

}