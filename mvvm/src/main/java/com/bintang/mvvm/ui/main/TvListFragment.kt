package com.bintang.mvvm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.bintang.common_ui.adapters.TvListAdapter
import com.bintang.common_ui.extensions.toast
import com.bintang.common_ui.viewholders.TvListViewHolder
import com.bintang.entity.entities.Tv
import com.bintang.mvvm.R
import com.bintang.mvvm.base.ViewModelFragment
import com.bintang.mvvm.databinding.FragmentTvBinding
import com.bintang.mvvm.ui.details.tv.TvDetailActivity
import kotlinx.android.synthetic.main.fragment_tv.recyclerView

class TvListFragment : ViewModelFragment(), TvListViewHolder.Delegate {

  private val viewModel: MainActivityViewModel by injectActivityVIewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return binding<FragmentTvBinding>(inflater, R.layout.fragment_tv, container).apply {
      viewModel = this@TvListFragment.viewModel
      lifecycleOwner = this@TvListFragment
    }.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initializeUI()
    loadMore(page = 1)
    observeMessages()
  }

  private fun initializeUI() {
    recyclerView.adapter = TvListAdapter(this)
    RecyclerViewPaginator(
      recyclerView = recyclerView,
      isLoading = { viewModel.isLoading() },
      loadMore = { loadMore(it) },
      onLast = { false }
    ).apply {
      threshold = 4
      currentPage = 1
    }
  }

  private fun loadMore(page: Int) = this.viewModel.postTvPage(page)

  override fun onItemClick(tv: Tv) =
    TvDetailActivity.startActivityModel(requireContext(), tv.id)

  private fun observeMessages() =
    this.viewModel.toastLiveData.observe(viewLifecycleOwner) { context?.toast(it) }
}
