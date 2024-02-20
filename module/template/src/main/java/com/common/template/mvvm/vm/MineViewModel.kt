package com.common.template.mvvm.vm

import android.app.Application
import com.common.template.mvvm.model.MineModel
import com.common.core.base.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MineViewModel @Inject constructor(
    application: Application, mineModel: MineModel,
) : BaseViewModel<MineModel>(application, mineModel) {

}