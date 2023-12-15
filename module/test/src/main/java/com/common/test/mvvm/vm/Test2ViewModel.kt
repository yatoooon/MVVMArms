package com.common.test.mvvm.vm

import android.app.Application
import com.common.test.mvvm.model.Test2Model
import com.common.core.base.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Test2ViewModel @Inject constructor(
    application: Application, test2Model: Test2Model,
) : BaseViewModel<Test2Model>(application, test2Model) {

}