package com.bintang.common_ui.viewholders

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.bintang.common_ui.databinding.ItemVideoBinding
import com.bintang.entity.Video
import kotlinx.android.synthetic.main.item_video.view.*

/** VideoListViewHolder is a viewHolder class for binding a [Video] item. */
class VideoListViewHolder(val view: View) : BaseViewHolder(view) {

  private lateinit var video: Video
  private val binding by bindings<ItemVideoBinding>(view)

  override fun bindData(data: Any) {
    if (data is Video) {
      video = data
      binding.apply {
        video = data
        palette = itemView.item_video_palette
        executePendingBindings()
      }
    }
  }

  override fun onClick(v: View?) = Unit

  override fun onLongClick(v: View?) = false
}
