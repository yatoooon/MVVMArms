package com.common.res.state

//state bean
data class State(var code: StateType, var message: String? = null, var showDialog: Boolean? = true)