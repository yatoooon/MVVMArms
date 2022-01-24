package com.common.template.mvvm.vm

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.basis.core.base.mvvm.BaseViewModel
import com.basis.common.http.net.Resource
import com.basis.common.http.net.apiCall
import com.common.template.mvvm.model.entity.TemplateEntity
import com.common.template.mvvm.model.RepositoryModel
import kotlinx.coroutines.launch

/**
 * ViewModel示例
 *
 */
class RepositoryViewModel @ViewModelInject constructor(
    application: Application, repositoryModel: RepositoryModel
) : BaseViewModel<RepositoryModel>(application, repositoryModel) {

    var articleListLiveData: MutableLiveData<Resource<TemplateEntity?>> = MutableLiveData()

    fun getArticleList(page: Int, pageSize: Int) {
        viewModelScope.launch {
            apiCall { model.getArticleList(page, pageSize) }.let {
                articleListLiveData.postValue(it)
            }
        }
    }
}