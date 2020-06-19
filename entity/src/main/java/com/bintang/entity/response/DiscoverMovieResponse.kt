package com.bintang.entity.response

import com.bintang.entity.NetworkResponseModel
import com.bintang.entity.entities.Movie

data class DiscoverMovieResponse(
  val page: Int,
  val results: List<Movie>,
  val total_results: Int,
  val total_pages: Int
) : NetworkResponseModel
