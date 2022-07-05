package com.common.template.mvvm.vm

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.common.core.base.mvvm.BaseViewModel
import com.common.res.http.net.Result
import com.common.res.http.net.apiCall
import com.common.template.mvvm.model.entity.TemplateEntity
import com.common.template.mvvm.model.RepositoryModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel示例
 *
 */
class RepositoryViewModel @ViewModelInject constructor(
    application: Application, repositoryModel: RepositoryModel
) : BaseViewModel<RepositoryModel>(application, repositoryModel) {

    suspend fun getArticleList(page: Int, pageSize: Int): Result<TemplateEntity?> {
        return apiCall { model.getArticleList(page, pageSize) }
    }
}