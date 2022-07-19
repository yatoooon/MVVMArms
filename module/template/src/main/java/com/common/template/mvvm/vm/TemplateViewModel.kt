package com.common.template.mvvm.vm

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.common.core.base.BaseModel
import com.common.core.base.mvvm.BaseViewModel

class TemplateViewModel @ViewModelInject constructor(
    application: Application
) : BaseViewModel<BaseModel>(application) {

}