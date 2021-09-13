package com.common.template.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.common.core.base.BaseRepository
import com.common.template.data.api.TemplateService
import com.common.template.data.entity.Item
import com.common.template.data.source.TemplatePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 10

class TemplateRepository @Inject constructor() : BaseRepository() {

    fun getPagingData(): Flow<PagingData<Item>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { TemplatePagingSource(getRetrofitService(TemplateService::class.java)) }
        ).flow
    }
}
