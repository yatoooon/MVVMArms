package com.common.template.ui.fragment

import android.os.Bundle
import android.widget.Adapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.common.core.base.mvvm.BaseVMFragment
import com.common.export.arouter.RouterHub
import com.common.res.action.StatusAction
import com.common.res.adapter.BaseAdapter
import com.common.res.adapter.BaseAdapter.Companion.PAGE_SIZE
import com.common.res.ext.inflateLayout
import com.common.res.layout.StatusLayout
import com.common.res.livedata.StatusEvent
import com.common.res.http.net.Resource
import com.common.res.utils.showLoadingDialog
import com.common.template.R
import com.common.template.data.entity.Item
import com.common.template.databinding.TemplateFragmentBinding
import com.common.template.ui.vm.TemplateViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.textColor

@AndroidEntryPoint
class TemplateFragment : BaseVMFragment<TemplateFragmentBinding, TemplateViewModel>(),
    StatusAction {
    companion object {
        fun newInstance(): TemplateFragment {
            val args = Bundle()
            val fragment = TemplateFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.template_fragment
    }

    private val mAdapter: BaseAdapter<Item> = BaseAdapter<Item>(R.layout.template_item)

    override fun initData() {
        showLoading()
        viewModel.getArticleList(mAdapter.page, PAGE_SIZE)
    }

    override fun initObserve() {
        viewModel.articleListLiveData.observe(this, {
            when (it.status) {
                StatusEvent.Status.LOADING -> {
                    showLoading()
                }
                StatusEvent.Status.SUCCESS -> {
                    showComplete()
                    if (mAdapter.isFirstPage()) {
                        mAdapter.setList(it.data!!.items)
                        binding.srlRefresh.isRefreshing = false
                    } else {
                        mAdapter.addData(it.data!!.items)
                        if (it.data?.items?.size == 0) {
                            mAdapter.loadMoreModule.loadMoreEnd(false)
                        } else {
                            mAdapter.loadMoreModule.loadMoreComplete()
                        }
                    }
                }
                StatusEvent.Status.ERROR, StatusEvent.Status.FAILURE -> {
                    binding.srlRefresh.isRefreshing = false
                    mAdapter.loadMoreModule.loadMoreComplete()
                    showError(object : StatusLayout.OnRetryListener {
                        override fun onRetry(layout: StatusLayout?) {
                            initData()
                        }
                    })
                }
            }
        })
    }

    override fun initView() {
        binding.srlRefresh.setOnRefreshListener { refresh() }
        mAdapter.loadMoreModule.setOnLoadMoreListener { loadMore() }
        mAdapter.loadMoreModule.isAutoLoadMore = true
        binding.recyclerView.adapter = mAdapter
        binding.srlRefresh.setColorSchemeResources(R.color.res_primary_color)
        /*  statusAdapter.addFooterView(
              requireContext().inflateLayout(R.layout.template_item_status)
                  .apply {
                      findViewById<TextView>(R.id.tv_item).apply {
                          text = "我是尾布局"
                          textColor = ContextCompat.getColor(
                              requireContext(),
                              R.color.res_common_primary_color
                          )
                          setOnClickListener { toast("点击了尾布局") }
                      }
                  })*/
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val data: Item = mAdapter.data.get(position)
            ARouter.getInstance().build(RouterHub.PUBLIC_WEBPAGEACTIVITY)
                .withString("url", data.html_url)
                .withString("title", data.name)
                .navigation()
        }

    }


    private fun refresh() {
        mAdapter.reset()
        viewModel.getArticleList(mAdapter.page, PAGE_SIZE)
    }

    private fun loadMore() {
        mAdapter.nextPage()
        viewModel.getArticleList(mAdapter.page, PAGE_SIZE)
    }

    override fun getStatusLayout(): StatusLayout {
        return binding.hlStatusHint
    }
}