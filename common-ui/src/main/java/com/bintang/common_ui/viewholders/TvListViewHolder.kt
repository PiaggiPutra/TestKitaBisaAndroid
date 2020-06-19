package com.bintang.common_ui.viewholders

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.bintang.common_ui.databinding.ItemTvBinding
import com.bintang.entity.entities.Tv
import kotlinx.android.synthetic.main.item_movie.view.*

/** TvListViewHolder is a viewHolder class for binding a [Tv] item. */
class TvListViewHolder(
  view: View,
  private val delegate: Delegate
) : BaseViewHolder(view) {

  interface Delegate {
    fun onItemClick(tv: Tv)
  }

  private lateinit var tv: Tv
  private val binding by bindings<ItemTvBinding>(view)

  override fun bindData(data: Any) {
    if (data is Tv) {
      tv = data
      binding.apply {
        tv = data
        palette = itemView.item_poster_palette
        executePendingBindings()
      }
    }
  }

  override fun onClick(v: View?) = delegate.onItemClick(tv)

  override fun onLongClick(p0: View?) = false
}
