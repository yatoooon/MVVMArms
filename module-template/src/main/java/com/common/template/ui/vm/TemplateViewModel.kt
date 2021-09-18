package com.common.template.ui.vm

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.common.core.base.BaseModel
import com.common.core.base.mvvm.BaseViewModel
import com.common.template.data.entity.Item
import com.common.template.data.repository.TemplateRepository
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel示例
 *
 */
class TemplateViewModel @ViewModelInject constructor(
    application: Application,
    templateRepository: TemplateRepository
) : BaseViewModel(application) {

    val pagingData: Flow<PagingData<Item>> =
        templateRepository.getPagingData().cachedIn(viewModelScope)
    val isRefreshing: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

}