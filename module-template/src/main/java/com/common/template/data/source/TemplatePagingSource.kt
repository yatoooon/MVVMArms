package com.common.template.data.source

import com.common.template.data.api.TemplateService

class TemplatePagingSource(private val templateService: TemplateService) {

    suspend fun getArticleList(page: Int) = templateService.getArticleList(page)

}