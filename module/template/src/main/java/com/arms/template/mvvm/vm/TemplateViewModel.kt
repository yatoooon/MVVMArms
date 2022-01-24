package com.arms.template.mvvm.vm

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.arms.core.base.BaseModel
import com.arms.core.base.mvvm.BaseViewModel

class TemplateViewModel @ViewModelInject constructor(
    application: Application
) : BaseViewModel<BaseModel>(application) {

}