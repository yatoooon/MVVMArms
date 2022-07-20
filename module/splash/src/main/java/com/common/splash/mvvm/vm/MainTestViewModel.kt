package com.common.splash.mvvm.vm

import android.app.Application
import com.common.core.base.BaseModel
import com.common.core.base.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainTestViewModel @Inject constructor(
    application: Application
) : BaseViewModel<BaseModel>(application) {

}