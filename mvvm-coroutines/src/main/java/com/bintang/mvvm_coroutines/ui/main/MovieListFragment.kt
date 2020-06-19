package com.bintang.mvvm_coroutines.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.bintang.common_ui.adapters.MovieListAdapter
import com.bintang.common_ui.extensions.toast
import com.bintang.common_ui.viewholders.MovieListViewHolder
import com.bintang.entity.entities.Movie
import com.bintang.mvvm_coroutines.R
import com.bintang.mvvm_coroutines.base.DatabindingFragment
import com.bintang.mvvm_coroutines.databinding.FragmentMovieBinding
import com.bintang.mvvm_coroutines.ui.details.movie.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_movie.recyclerView
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListFragment : DatabindingFragment(), MovieListViewHolder.Delegate {

  private val viewModel: MainActivityViewModel by viewModel()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return binding<FragmentMovieBinding>(
      inflater, R.layout.fragment_movie, container).apply {
      viewModel = this@MovieListFragment.viewModel
      lifecycleOwner = this@MovieListFragment
      adapter = MovieListAdapter(this@MovieListFragment)
    }.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initializeUI()
    loadMore(page = 1)
    observeMessages()
  }

  private fun initializeUI() {
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

  private fun loadMore(page: Int) = this.viewModel.postMoviePage(page)

  override fun onItemClick(view: View, movie: Movie) =
    MovieDetailActivity.startActivityModel(requireContext(), view, movie)

  private fun observeMessages() =
    this.viewModel.toastLiveData.observe(this) { context?.toast(it) }
}
