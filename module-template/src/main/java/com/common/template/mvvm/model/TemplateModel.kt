package com.common.template.mvvm.model

import com.common.core.base.BaseModel
import com.common.core.base.BaseRepository
import com.common.res.http.net.apiCall
import com.common.template.mvvm.model.api.TemplateService
import javax.inject.Inject


class TemplateModel @Inject constructor(repository : BaseRepository) : BaseModel(repository) {

    suspend fun getArticleList(page: Int, pageSize: Int) = apiCall {
        getRetrofitService(TemplateService::class.java).getArticleList(page, pageSize)
    }

}
