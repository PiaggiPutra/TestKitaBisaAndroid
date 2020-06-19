package com.bintang.common_ui.viewholders

import android.view.View
import androidx.core.view.ViewCompat
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.bintang.common_ui.databinding.ItemMovieFavouriteBinding
import com.bintang.entity.entities.Movie
import kotlinx.android.synthetic.main.item_movie_favourite.view.*

class MovieFavouriteListViewHolder(
  view: View,
  private val delegate: Delegate
) : BaseViewHolder(view) {

  interface Delegate {
    fun onItemClick(view: View, movie: Movie)
  }

  private lateinit var movie: Movie
  private val binding by bindings<ItemMovieFavouriteBinding>(view)

  override fun bindData(data: Any) {
    if (data is Movie) {
      movie = data
      binding.apply {
        ViewCompat.setTransitionName(binding.itemMovieContainer, data.title)
        movie = data
        palette = itemPosterPalette
        executePendingBindings()
      }
    }
  }

  override fun onClick(v: View?) = delegate.onItemClick(binding.itemMovieContainer, movie)

  override fun onLongClick(v: View?) = false
}
