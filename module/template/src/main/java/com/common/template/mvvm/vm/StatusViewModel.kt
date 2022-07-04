package com.common.template.mvvm.vm

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.common.core.base.BaseModel
import com.common.core.base.mvvm.BaseViewModel

class StatusViewModel @ViewModelInject constructor(
    application: Application
) : BaseViewModel<BaseModel>(application) {

    var statusList = MutableLiveData<ArrayList<String>>()

    fun getData(page: Int, pageSize: Int) {
        val arrayListOf: ArrayList<String> = arrayListOf()
        for (index in (page - 1) * pageSize until page * pageSize) {
            arrayListOf.add("这是第" + index.toString() + "个条目")
        }
        statusList.postValue(arrayListOf)
    }
}