package com.common.template.mvvm.vm

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.common.core.base.BaseModel
import com.common.core.base.mvvm.BaseViewModel

class TemplateViewModel @ViewModelInject constructor(
    application: Application
) : BaseViewModel<BaseModel>(application) {
    var testactivity = MutableLiveData<String>()
    var testfragment = MutableLiveData<String>()
    var test = MutableLiveData<String>()
    fun getdata() {
        testactivity.postValue("aaaaaaaaaaaaaaaaaaa")
        testfragment.postValue("bbbbbbbbbbbbbbbbbbb")
        test.postValue("ccccccccccccccccccc")
    }
}