package com.common.res.template

import android.os.Bundle
import com.common.core.base.mvvm.BaseVMActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity模板示例
 *
 */
@AndroidEntryPoint
class TemplateActivity : BaseVMActivity<TemplateViewModel>(){

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return 0
    }

}