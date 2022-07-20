package com.common.core.appvm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * 用于创建[AppViewModel]实例
 */
class AppViewModelFactory @Inject constructor(
    private val application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass != AppViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class, must be ${AppViewModel::class.java}")
        }
        return AppViewModel(
            application
        ) as T
    }
}