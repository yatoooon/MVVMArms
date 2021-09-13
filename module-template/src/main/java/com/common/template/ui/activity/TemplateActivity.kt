package com.common.template.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.common.core.base.mvvm.BaseVMActivity
import com.common.res.immersionbar.BindFullScreen
import com.common.res.immersionbar.BindImmersionBar
import com.common.res.router.RouterHub
import com.common.template.R
import com.common.template.databinding.TemplateActivityBinding
import com.common.template.ui.adapter.FooterAdapter
import com.common.template.ui.adapter.RepoAdapter
import com.common.template.ui.vm.TemplateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Activity模板示例
 *
 */
@Route(path = RouterHub.PUBLIC_TEMPLATE)
@AndroidEntryPoint
class TemplateActivity : BaseVMActivity<TemplateActivityBinding, TemplateViewModel>(){

    private val repoAdapter = RepoAdapter()

    override fun getLayoutId(): Int {
        return R.layout.template_activity
    }

    override fun initObserve() {
        viewModel.isRefreshing.observe(this, Observer { boolean ->
            binding.srlRefresh.isRefreshing = boolean
        })
    }

    override fun initData(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            viewModel.pagingData.collect { pagingData ->
                repoAdapter.submitData(pagingData)
            }
        }
    }

    override fun initViewClick() {
        binding.srlRefresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        binding.srlRefresh.setOnRefreshListener {
            repoAdapter.refresh()
            viewModel.isRefreshing.value = true
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter =
            repoAdapter.withLoadStateFooter(FooterAdapter { repoAdapter.retry() })

//        repoAdapter.setOnClickListener { item, view ->
//            val action = MainFragmentDirections.actionMainFragmentToMainDetailFragment(
//                item.html_url,
//                item.name
//            )
//            Navigation.findNavController(view).navigate(action)
//        }
        repoAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    viewModel.isRefreshing.value = false
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.srlRefresh.isEnabled = true
                }
                is LoadState.Loading -> {
                    if (!binding.srlRefresh.isRefreshing) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.INVISIBLE
                        binding.srlRefresh.isEnabled = false
                    }
                }
                is LoadState.Error -> {
                    viewModel.isRefreshing.value = false
                    val state = it.refresh as LoadState.Error
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.srlRefresh.isEnabled = true
                    Toast.makeText(
                        context,
                        "Load Error: ${state.error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}