package com.common.template.mvvm.vm

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.common.core.base.mvvm.BaseViewModel
import com.common.res.http.net.Resource
import com.common.template.mvvm.model.entity.TemplateEntity
import com.common.template.mvvm.model.TemplateModel
import kotlinx.coroutines.launch

/**
 * ViewModel示例
 *
 */
class TemplateViewModel @ViewModelInject constructor(
    application: Application, templateModel: TemplateModel
) : BaseViewModel<TemplateModel>(application, templateModel) {

    var articleListLiveData: MutableLiveData<Resource<TemplateEntity?>> = MutableLiveData()

    fun getArticleList(page: Int, pageSize: Int) {
        viewModelScope.launch {
            model.getArticleList(page, pageSize).let {
                articleListLiveData.postValue(it)
            }
        }
    }
}