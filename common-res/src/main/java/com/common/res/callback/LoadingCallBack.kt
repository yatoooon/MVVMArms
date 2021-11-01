package com.common.res.callback

import android.content.Context
import android.view.View
import com.common.res.R
import com.kingja.loadsir.callback.Callback

//loading callback
class LoadingCallBack : Callback() {

    override fun onCreateView(): Int = R.layout.res_layout_loading

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
    
}