package com.common.template.mvvm.fragment

import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.common.core.base.mvvm.BaseVMFragment
import com.common.res.adapter.BaseAdapter
import com.common.res.adapter.BaseAdapter.Companion.PAGE_SIZE
import com.common.res.ext.inflateLayout
import com.common.template.R
import com.common.template.databinding.TemplateFragmentStatusBinding
import com.common.template.mvvm.vm.StatusViewModel
import org.jetbrains.anko.textColor

class StatusFragment : BaseVMFragment<TemplateFragmentStatusBinding, StatusViewModel>() {

    companion object {
        fun newInstance(): StatusFragment {
            val args = Bundle()
            val fragment = StatusFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val mAdapter = BaseAdapter<String>(R.layout.template_item_status)

    override fun getLayoutId(): Int {
        return R.layout.template_fragment_status
    }

    override fun initData() {
        viewModel.getData(mAdapter.page, PAGE_SIZE)
    }

    override fun initObserve() {
        viewModel.statusList.observe(this, {
            if (mAdapter.isFirstPage()) {
                mAdapter.setList(it)
                binding.srlRefresh.isRefreshing = false
            } else {
                mAdapter.addData(it)
                if (mAdapter.itemCount > 100) {
                    mAdapter.loadMoreModule.loadMoreEnd(false)
                } else {
                    mAdapter.loadMoreModule.loadMoreComplete()
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
        mAdapter.addHeaderView(
            requireContext().inflateLayout(R.layout.template_item_status)
                .apply {
                    findViewById<TextView>(R.id.tv_item).apply {
                        text = "???????????????"
                        textColor = ContextCompat.getColor(
                            requireContext(),
                            R.color.res_primary_color
                        )
                        setOnClickListener { toast("??????????????????") }
                    }
                })
        /*  statusAdapter.addFooterView(
              requireContext().inflateLayout(R.layout.template_item_status)
                  .apply {
                      findViewById<TextView>(R.id.tv_item).apply {
                          text = "???????????????"
                          textColor = ContextCompat.getColor(
                              requireContext(),
                              R.color.res_common_primary_color
                          )
                          setOnClickListener { toast("??????????????????") }
                      }
                  })*/
        mAdapter.setOnItemClickListener { adapter, view, position ->
            toast("?????????" + position + "?????????")
        }
    }


    private fun refresh() {
        postDelayed({
            mAdapter.reset()
            initData()
        }, 200)

    }

    private fun loadMore() {
        postDelayed({
            mAdapter.nextPage()
            viewModel.getData(mAdapter.page, PAGE_SIZE)
        }, 200)

    }
}