package com.common.splash.mvvm.vm

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.common.core.base.BaseModel
import com.common.core.base.mvvm.BaseViewModel

class MainTestViewModel @ViewModelInject constructor(
    application: Application
) : BaseViewModel<BaseModel>(application) {

}