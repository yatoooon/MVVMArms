package com.common.res.template

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.common.core.base.BaseModel
import com.common.core.base.mvvm.DataViewModel

/**
 * ViewModel示例
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class TemplateViewModel @ViewModelInject constructor(application: Application, model: BaseModel) : DataViewModel(application, model)