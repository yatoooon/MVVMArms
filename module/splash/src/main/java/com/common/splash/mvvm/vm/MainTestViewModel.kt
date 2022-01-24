package com.common.splash.mvvm.vm

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.basis.core.base.BaseModel
import com.basis.core.base.mvvm.BaseViewModel

class MainTestViewModel @ViewModelInject constructor(
    application: Application
) : BaseViewModel<BaseModel>(application) {

}