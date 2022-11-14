package com.common.template.mvvm.fragment

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.common.core.base.mvvm.BaseVMFragment
import com.common.export.arouter.RouterHub
import com.common.res.action.StatusAction
import com.common.res.adapter.BaseAdapter
import com.common.res.adapter.BaseAdapter.Companion.PAGE_SIZE
import com.common.res.http.net.launchAndCollect
import com.common.res.layout.StatusLayout
import com.common.template.R
import com.common.template.databinding.TemplateFragmentRepositoryBinding
import com.common.template.mvvm.model.entity.TemplateEntity.Item
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
    }

    override fun initObserve() {
    }


    override fun onFragmentResume(first: Boolean) {
        if (first){
            getArticleData()
        }
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
                .withString("url", data.htmlUrl)
                .withString("title", data.name)
                .navigation()
        }
        showLoading()

    }


    private fun refresh() {
        mAdapter.reset()
        getArticleData()
    }

    private fun loadMore() {
        mAdapter.nextPage()
        getArticleData()
    }

    private fun getArticleData() {
        launchAndCollect({ viewModel.getArticleList(mAdapter.page, PAGE_SIZE) },
            {
                onSuccess = {
                    showComplete()
                    if (mAdapter.isFirstPage()) {
                        mAdapter.setList(it!!.items)
                        binding.srlRefresh.isRefreshing = false
                    } else {
                        mAdapter.addData(it!!.items)
                        if (it.items.size == 0) {
                            mAdapter.loadMoreModule.loadMoreEnd(false)
                        } else {
                            mAdapter.loadMoreModule.loadMoreComplete()
                        }
                    }
                }
                onFailed = { _, _ ->
                    binding.srlRefresh.isRefreshing = false
                    mAdapter.loadMoreModule.loadMoreFail()
                    showError(object : StatusLayout.OnRetryListener {
                        override fun onRetry(layout: StatusLayout?) {
                            getArticleData()
                        }
                    })
                }
            },false)
    }

    override fun getStatusLayout(): StatusLayout {
        return binding.hlStatusHint
    }
}