package com.common.template.data.api

import com.common.template.data.entity.TemplateEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface TemplateService {
    @GET("search/repositories?sort=stars&q=Android")
    suspend fun searchRepos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): TemplateEntity
}
