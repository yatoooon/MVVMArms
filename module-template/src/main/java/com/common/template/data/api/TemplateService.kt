package com.common.template.data.api

import com.common.res.entity.ListEntity
import com.common.res.net.BaseResponse
import com.common.template.data.entity.TemplateEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TemplateService {
    @GET("article/list/{page}/json")
    suspend fun getArticleList(
        @Path("page") page: Int
    ): BaseResponse<ListEntity<TemplateEntity>>
}
