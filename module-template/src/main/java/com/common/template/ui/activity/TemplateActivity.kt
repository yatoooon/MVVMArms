package com.common.template.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.common.core.base.mvvm.BaseVMActivity
import com.common.export.arouter.RouterHub
import com.common.res.adapter.BaseAdapter
import com.common.template.R
import com.common.template.data.entity.TemplateEntity
import com.common.template.databinding.TemplateActivityBinding
import com.common.template.ui.vm.TemplateViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity模板示例
 *
 */
@Route(path = RouterHub.PUBLIC_TEMPLATE_ACTIVITY)
@AndroidEntryPoint
class TemplateActivity : BaseVMActivity<TemplateActivityBinding, TemplateViewModel>() {

    private val repoAdapter = BaseAdapter<TemplateEntity>(R.layout.template_item)

    override fun getLayoutId(): Int {
        return R.layout.template_activity
    }

    override fun initObserve() {
        viewModel.articleListLiveData.observe(this, {
            repoAdapter.addData(it.data!!.datas)
        })
    }

    override fun initData() {
        viewModel.getArticleList(0)
    }

    override fun initView() {
        super.initView()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = repoAdapter
    }


}