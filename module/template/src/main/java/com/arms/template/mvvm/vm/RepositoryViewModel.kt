package com.arms.template.mvvm.vm

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arms.core.base.mvvm.BaseViewModel
import com.arms.common.http.net.Resource
import com.arms.common.http.net.apiCall
import com.arms.template.mvvm.model.entity.TemplateEntity
import com.arms.template.mvvm.model.RepositoryModel
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