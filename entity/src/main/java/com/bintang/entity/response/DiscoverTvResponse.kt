package com.bintang.entity.response

import com.bintang.entity.NetworkResponseModel
import com.bintang.entity.entities.Tv

data class DiscoverTvResponse(
  val page: Int,
  val results: List<Tv>,
  val total_results: Int,
  val total_pages: Int
) : NetworkResponseModel
