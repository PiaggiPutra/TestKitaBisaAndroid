package com.bintang.common_ui.adapters

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.bintang.common_ui.R
import com.bintang.common_ui.viewholders.ReviewListViewHolder
import com.bintang.entity.Review

/** ReviewListAdapter is an adapter class for binding [Review] items. */
class ReviewListAdapter : BaseAdapter() {

  init {
    addSection(ArrayList<Review>())
  }

  fun addReviewList(reviews: List<Review>) {
    val section = sections()[0]
    section.addAll(reviews)
    notifyItemRangeInserted(section.size - reviews.size + 1, reviews.size)
  }

  override fun layout(sectionRow: SectionRow) = R.layout.item_review

  override fun viewHolder(layout: Int, view: View) = ReviewListViewHolder(view)
}
