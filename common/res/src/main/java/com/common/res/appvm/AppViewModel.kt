package com.common.res.appvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * [Application]生命周期内的[AndroidViewModel]
 */

@HiltViewModel
class AppViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    var test:MutableLiveData<String> = MutableLiveData<String>("")

    fun testChange(){
        test.value="aaaaaaaaaaaa"
    }

}