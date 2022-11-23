package com.common.template.mvvm.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.common.core.base.mvvm.BaseViewModel
import com.common.res.http.net.Result
import com.common.res.http.net.apiCall
import com.common.res.http.net.parseData
import com.common.res.livedata.StatusEvent
import com.common.template.mvvm.model.RepositoryModel
import com.common.template.mvvm.model.entity.TemplateEntity
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


    suspend fun getArticleList(page: Int, pageSize: Int) =
        apiCall { model.getArticleList(page, pageSize) }.apply {
            parseData {
                onSuccess = {
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