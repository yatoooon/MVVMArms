package com.arms.template.mvvm.model

import com.arms.core.base.BaseModel
import com.arms.core.base.BaseRepository
import com.arms.template.mvvm.model.api.RepositoryService
import javax.inject.Inject


class RepositoryModel @Inject constructor(repository: BaseRepository) : BaseModel(repository) {

    suspend fun getArticleList(page: Int, pageSize: Int) =
        getRetrofitService(RepositoryService::class.java).getArticleList(page, pageSize)

}
