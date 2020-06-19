package com.bintang.common_ui.adapters

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.bintang.common_ui.R
import com.bintang.common_ui.viewholders.VideoListViewHolder
import com.bintang.entity.Video

/** TvFavouriteListAdapter is an adapter class for binding [Video] items. */
class VideoListAdapter : BaseAdapter() {

  init {
    addSection(ArrayList<Video>())
  }

  fun addVideoList(videos: List<Video>) {
    val section = sections()[0]
    section.addAll(videos)
    notifyItemRangeInserted(section.size - videos.size + 1, videos.size)
  }

  override fun layout(sectionRow: SectionRow) = R.layout.item_video

  override fun viewHolder(layout: Int, view: View) = VideoListViewHolder(view)
}
