package com.common.template.data.repository

import com.common.core.base.BaseRepository
import com.common.res.net.apiCall
import com.common.template.data.api.TemplateService
import com.common.template.data.source.TemplatePagingSource
import javax.inject.Inject


class TemplateRepository @Inject constructor() : BaseRepository() {

    suspend fun getArticleList(page: Int) = apiCall {
        TemplatePagingSource(getRetrofitService(TemplateService::class.java)).getArticleList(page)
    }

}
