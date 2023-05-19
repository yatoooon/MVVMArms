package com.common.template.mvvm.model

import com.common.core.base.BaseModel
import com.common.core.base.BaseRepository
import com.common.template.mvvm.model.api.RepositoryService
import com.common.template.mvvm.model.room.database.TemplateEntityItemDataBase
import com.common.template.mvvm.model.room.table.TemplateEntityItem
import javax.inject.Inject


class RepositoryModel @Inject constructor(repository: BaseRepository) : BaseModel(repository) {

    suspend fun getArticleList(page: Int, pageSize: Int) =
        getRetrofitService(RepositoryService::class.java).getArticleList(page, pageSize)


    private fun getTemplateEntityItemDao() =
        getRoomDatabase(TemplateEntityItemDataBase::class.java).templateEntityItemDao()


    fun getArticleListFromRoom(limit: Int, offset: Int) =
        getTemplateEntityItemDao().getTemplateEntityItemList(limit, offset)

    fun insertArticleListToRoom(list: List<TemplateEntityItem>) =
        getTemplateEntityItemDao().insertTemplateEntityItemList(list)
}
