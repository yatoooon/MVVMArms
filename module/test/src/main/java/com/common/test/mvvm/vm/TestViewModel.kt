package com.common.test.mvvm.vm

import android.app.Application
import com.common.test.mvvm.model.TestModel
import com.common.core.base.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    application: Application, testModel: TestModel,
) : BaseViewModel<TestModel>(application, testModel) {

}