package com.common.template.mvvm.fragment

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.mvvm.BaseVMFragment
import com.common.export.arouter.RouterHub
import com.common.res.action.StatusAction
import com.common.res.adapter.BaseAdapter
import com.common.res.adapter.BaseAdapter.Companion.PAGE_SIZE
import com.common.res.layout.StatusLayout
import com.common.res.livedata.StatusEvent
import com.common.template.R
import com.common.template.mvvm.model.entity.Item
import com.common.template.databinding.TemplateFragmentRepositoryBinding
import com.common.template.mvvm.vm.RepositoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryFragment : BaseVMFragment<TemplateFragmentRepositoryBinding, RepositoryViewModel>(),
    StatusAction {
    companion object {
        fun newInstance(): RepositoryFragment {
            val args = Bundle()
            val fragment = RepositoryFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.template_fragment_repository
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