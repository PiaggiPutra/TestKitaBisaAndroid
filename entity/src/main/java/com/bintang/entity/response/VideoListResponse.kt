package com.bintang.entity.response

import com.bintang.entity.NetworkResponseModel
import com.bintang.entity.Video

data class VideoListResponse(
  val id: Int,
  val results: List<Video>
) : NetworkResponseModel
