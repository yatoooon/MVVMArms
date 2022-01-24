package com.common.template.mvvm.model

import com.basis.core.base.BaseModel
import com.basis.core.base.BaseRepository
import com.common.template.mvvm.model.api.RepositoryService
import javax.inject.Inject


class RepositoryModel @Inject constructor(repository: BaseRepository) : BaseModel(repository) {

    suspend fun getArticleList(page: Int, pageSize: Int) =
        getRetrofitService(RepositoryService::class.java).getArticleList(page, pageSize)

}
