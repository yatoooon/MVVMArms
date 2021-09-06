package com.common.res.callback

import com.common.res.R
import com.kingja.loadsir.callback.Callback

//empty callback
class EmptyCallBack : Callback() {

    override fun onCreateView(): Int = R.layout.res_layout_empty

}