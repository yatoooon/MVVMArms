package com.common.template.mvvm.vm

import android.app.Application
import com.common.template.mvvm.model.MessageModel
import com.common.core.base.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    application: Application, messageModel: MessageModel,
) : BaseViewModel<MessageModel>(application, messageModel) {

}