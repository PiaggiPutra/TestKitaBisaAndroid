package com.bintang.network.client

import com.bintang.entity.response.KeywordListResponse
import com.bintang.entity.response.ReviewListResponse
import com.bintang.entity.response.VideoListResponse
import com.bintang.network.ApiResponse
import com.bintang.network.service.TvService
import com.bintang.network.transform

/** TvClient is a UseCase of the [TvService] interface. */
class TvClient(private val service: TvService) {

  fun fetchKeywords(
    id: Int,
    onResult: (response: ApiResponse<KeywordListResponse>) -> Unit
  ) {
    this.service.fetchKeywords(id).transform(onResult)
  }

  fun fetchVideos(
    id: Int,
    onResult: (response: ApiResponse<VideoListResponse>) -> Unit
  ) {
    this.service.fetchVideos(id).transform(onResult)
  }

  fun fetchReviews(
    id: Int,
    onResult: (response: ApiResponse<ReviewListResponse>) -> Unit
  ) {
    this.service.fetchReviews(id).transform(onResult)
  }
}
