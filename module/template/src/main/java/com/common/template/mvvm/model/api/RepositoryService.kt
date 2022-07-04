package com.common.template.mvvm.model.api

import com.common.template.mvvm.model.entity.TemplateEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryService {
    @GET("search/repositories?sort=stars&q=Android")
    suspend fun getArticleList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): TemplateEntity
}
