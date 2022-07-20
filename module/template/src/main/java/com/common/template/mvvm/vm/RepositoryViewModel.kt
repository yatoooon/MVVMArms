package com.common.template.mvvm.vm

import android.app.Application
import com.common.core.base.mvvm.BaseViewModel
import com.common.res.http.net.Result
import com.common.res.http.net.apiCall
import com.common.template.mvvm.model.RepositoryModel
import com.common.template.mvvm.model.entity.TemplateEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel示例
 *
 */
@HiltViewModel
class RepositoryViewModel @Inject constructor(
    application: Application, repositoryModel: RepositoryModel
) : BaseViewModel<RepositoryModel>(application, repositoryModel) {

    suspend fun getArticleList(page: Int, pageSize: Int): Result<TemplateEntity?> {
        return apiCall { model.getArticleList(page, pageSize) }
    }
}