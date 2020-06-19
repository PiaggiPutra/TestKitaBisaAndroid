package com.bintang.common_ui.adapters

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.bintang.common_ui.R
import com.bintang.common_ui.viewholders.TvListViewHolder
import com.bintang.entity.entities.Tv

/** TvFavouriteListAdapter is an adapter class for binding [Tv] items. */
class TvListAdapter(
  private val delegate: TvListViewHolder.Delegate
) : BaseAdapter() {

  init {
    addSection(ArrayList<Tv>())
  }

  fun addTvList(tvs: List<Tv>) {
    val section = sections()[0]
    section.addAll(tvs)
    notifyItemRangeInserted(section.size - tvs.size + 1, tvs.size)
  }

  override fun layout(sectionRow: SectionRow) = R.layout.item_tv

  override fun viewHolder(layout: Int, view: View) = TvListViewHolder(view, delegate)
}
