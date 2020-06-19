package com.bintang.common_ui.viewholders

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.bintang.common_ui.databinding.ItemReviewBinding
import com.bintang.entity.Review

/** ReviewListViewHolder is a viewHolder class for binding a [Review] item. */
class ReviewListViewHolder(val view: View) : BaseViewHolder(view) {

  private lateinit var review: Review
  private val binding by bindings<ItemReviewBinding>(view)

  override fun bindData(data: Any) {
    if (data is Review) {
      review = data
      binding.apply {
        review = data
        executePendingBindings()
      }
    }
  }

  override fun onClick(v: View?) = Unit

  override fun onLongClick(v: View?) = false
}
