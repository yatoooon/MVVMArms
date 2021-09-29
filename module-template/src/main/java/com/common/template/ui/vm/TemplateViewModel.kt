package com.common.template.ui.vm

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.common.core.base.BaseModel
import com.common.core.base.mvvm.BaseViewModel
import com.common.res.entity.ListEntity
import com.common.res.net.BaseResponse
import com.common.template.data.entity.TemplateEntity
import com.common.template.data.repository.TemplateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * ViewModel示例
 *
 */
class TemplateViewModel @ViewModelInject constructor(
    application: Application,
    private val templateRepository: TemplateRepository
) : BaseViewModel(application) {

    val articleListLiveData: MutableLiveData<BaseResponse<ListEntity<TemplateEntity>>> = MutableLiveData()

    fun getArticleList(page:Int) {
        viewModelScope.launch {
            templateRepository.getArticleList(page).apply {
                articleListLiveData.postValue(this)
            }
        }
    }
}