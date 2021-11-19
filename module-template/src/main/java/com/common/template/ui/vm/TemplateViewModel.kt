package com.common.template.ui.vm

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.common.core.base.mvvm.BaseViewModel
import com.common.res.net.Resource
import com.common.template.data.entity.TemplateEntity
import com.common.template.data.repository.TemplateRepository
import kotlinx.coroutines.launch

/**
 * ViewModel示例
 *
 */
class TemplateViewModel @ViewModelInject constructor(
    application: Application,
    private val templateRepository: TemplateRepository
) : BaseViewModel(application) {

    var articleListLiveData: MutableLiveData<Resource<TemplateEntity?>> = MutableLiveData()

    fun getArticleList(page: Int, pageSize: Int) {
        viewModelScope.launch {
            templateRepository.getArticleList(page, pageSize).let {
                articleListLiveData.postValue(it)
            }
        }
    }
}