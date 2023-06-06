package com.common.template.mvvm.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.common.core.base.mvvm.BaseViewModel
import com.common.res.adapter.BaseAdapter.Companion.PAGE_SIZE
import com.common.res.http.net.apiCall
import com.common.res.http.net.parseData
import com.common.res.livedata.StatusEvent
import com.common.res.utils.ArmsUtil
import com.common.template.mvvm.model.RepositoryModel
import com.common.template.mvvm.model.entity.TemplateEntity
import com.common.template.mvvm.model.room.table.TemplateEntityItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel示例
 *
 */
@HiltViewModel
class RepositoryViewModel @Inject constructor(
    application: Application, repositoryModel: RepositoryModel
) : BaseViewModel<RepositoryModel>(application, repositoryModel) {

    var articleList = MutableLiveData<List<TemplateEntity.Item>>()


    fun getArticleListFromRoom(page: Int, pageSize: Int) {
        ArmsUtil.obtainAppComponent().executorService.execute {
            val articleListFromRoom =
                model.getArticleListFromRoom(offset = (page - 1) * PAGE_SIZE, limit = pageSize)
            if (articleListFromRoom.isNotEmpty()) {
                val list: ArrayList<TemplateEntity.Item> = arrayListOf()
                articleListFromRoom.forEach { it ->
                    list.add(
                        TemplateEntity.Item(
                            id = it.id,
                            stargazersCount = it.stargazersCount,
                            name = it.name,
                            description = it.description
                        )
                    )
                }
                articleList.postValue(list)
                statusEvent.postValue(StatusEvent.Status.SUCCESS)
            }
        }
    }

    suspend fun getArticleList(page: Int, pageSize: Int) =
        apiCall { model.getArticleList(page, pageSize) }.apply {
            parseData {
                onSuccess = {
                    ArmsUtil.obtainAppComponent().executorService.execute {
                        it?.items?.map { it ->
                            TemplateEntityItem(it.id, it.stargazersCount, it.name, it.description)
                        }.let { it ->
                            model.insertArticleListToRoom(it!!)
                        }
                    }
                    articleList.value = it?.items
                    statusEvent.value = StatusEvent.Status.SUCCESS
                }
                onFailed = {
                    statusEvent.value = StatusEvent.Status.FAILURE
                }
                onComplete = {
                    statusEvent.value = StatusEvent.Status.COMPLETE
                }
            }
        }

}