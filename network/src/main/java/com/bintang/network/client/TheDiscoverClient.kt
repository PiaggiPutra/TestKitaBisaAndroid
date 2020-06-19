package com.bintang.network.client

import com.bintang.entity.response.DiscoverMovieResponse
import com.bintang.entity.response.DiscoverTvResponse
import com.bintang.network.ApiResponse
import com.bintang.network.service.TheDiscoverService
import com.bintang.network.transform

/** TheDiscoverClient is a UseCase of the [TheDiscoverService] interface. */
class TheDiscoverClient(private val service: TheDiscoverService) {

  fun fetchDiscoverMovie(
    page: Int,
    onResult: (response: ApiResponse<DiscoverMovieResponse>) -> Unit
  ) {
    this.service.fetchDiscoverMovie(page).transform(onResult)
  }

  fun fetchDiscoverTv(
    page: Int,
    onResult: (response: ApiResponse<DiscoverTvResponse>) -> Unit
  ) {
    this.service.fetchDiscoverTv(page).transform(onResult)
  }
}
