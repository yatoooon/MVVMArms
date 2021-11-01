package com.common.res.callback

import com.common.res.R
import com.kingja.loadsir.callback.Callback

//error callback
class ErrorCallBack : Callback() {

    override fun onCreateView(): Int = R.layout.res_layout_error

}